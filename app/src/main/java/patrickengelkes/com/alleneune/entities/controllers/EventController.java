package patrickengelkes.com.alleneune.entities.controllers;

import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.CurrentClub;
import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.Event;
import patrickengelkes.com.alleneune.entities.objects.User;
import patrickengelkes.com.alleneune.enums.ApiCall;
import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;

/**
 * Created by patrickengelkes on 23/01/15.
 */
public class EventController {
    public static final String TAG = EventController.class.getSimpleName();
    @Inject
    UserController userController;
    @Inject
    CurrentClub currentClub;

    @Inject
    public EventController(Context context) {
        final RoboInjector injector = RoboGuice.getInjector(context);

        injector.injectMembersWithoutViews(this);
    }

    public static HttpPostEntity getParticipantsPostEntity(int eventID) throws JSONException, UnsupportedEncodingException {
        JSONObject leaf = new JSONObject();
        leaf.put(Event.EVENT_ID, eventID);
        JSONObject root = new JSONObject();
        root.put(Event.ROOT, leaf);

        return new HttpPostEntity(Event.GET_PARTICIPANTS, root.toString());
    }

    public String eventJSON(String eventName, int clubID, String eventDate) throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put(Event.NAME, eventName);
        leaf.put(Event.CLUB_ID, clubID);
        leaf.put(Event.DATE, eventDate);
        JSONObject root = new JSONObject();
        root.put(Event.ROOT, leaf);
        return root.toString();
    }

    public ApiCall createEvent(String eventName, int clubID, String eventDate) {
        try {
            HttpResponse response = new ApiCallTask().
                    execute(createEventPostEntity(eventName, clubID, eventDate)).get();
            if (response != null) {
                JSONObject createAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 201) {
                    return ApiCall.CREATED;
                } else {
                    Log.e(TAG, "Creating entity failed");
                    Log.e(TAG, createAnswer.toString());
                    return ApiCall.BAD_REQUEST;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return ApiCall.BAD_REQUEST;
    }

    private HttpPostEntity createEventPostEntity(String eventName, int clubID, String eventDate)
            throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(Event.GENERIC_URL, eventJSON(eventName, clubID, eventDate));
    }

    public List<Event> getEventsByClub() {
        List<Event> returnList = new ArrayList<Event>();

        try {
            HttpResponse response = new ApiCallTask().execute(getEventsByClubPostEntity(currentClub.getId())).get();
            if (response != null) {
                JSONObject getEventsByClubAnswer = new JsonBuilder().execute(response).get();
                returnList = getEventsFromResponse(getEventsByClubAnswer);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return returnList;
    }

    public HttpPostEntity getEventsByClubPostEntity(int clubID) throws JSONException, UnsupportedEncodingException {
        JSONObject leaf = new JSONObject();
        leaf.put(Event.CLUB_ID, clubID);
        JSONObject root = new JSONObject();
        root.put(Event.ROOT, leaf);

        return new HttpPostEntity(Event.GET_BY_CLUB, root.toString());
    }

    public List<User> getEventParticipants(int eventID) {
        List<User> returnList = new ArrayList<User>();

        try {
            HttpResponse response =
                    new ApiCallTask().execute(getParticipantsPostEntity(eventID)).get();
            if (response != null) {
                JSONObject participantsByEvent = new JsonBuilder().execute(response).get();
                JSONArray users = (JSONArray) participantsByEvent.get(Event.ROOT + "s");
                returnList = userController.getUserListFromJSONResponse(users);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return returnList;
    }

    private List<Event> getEventsFromResponse(JSONObject response) {
        List<Event> eventList = new ArrayList<Event>();
        try {
            JSONArray eventsArray = (JSONArray) response.get(Event.ROOT + "s");
            for (int i = 0; i < eventsArray.length(); i++) {
                Event newEvent = new Event((JSONObject) eventsArray.get(i));
                eventList.add(newEvent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eventList;
    }
}
