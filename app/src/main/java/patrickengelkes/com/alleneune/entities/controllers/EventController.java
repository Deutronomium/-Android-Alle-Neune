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
    private static String genericUrl = "/events";
    @Inject
    UserController userController;
    private JSONObject getEventsByClubAnswer;
    private JSONObject createAnswer;

    @Inject
    public EventController(Context context) {
        final RoboInjector injector = RoboGuice.getInjector(context);

        injector.injectMembersWithoutViews(this);
    }

    public static HttpPostEntity getParticipantsPostEntity(int eventID) throws JSONException, UnsupportedEncodingException {
        JSONObject leaf = new JSONObject();
        leaf.put("event_id", eventID);
        JSONObject root = new JSONObject();
        root.put("event", leaf);

        String url = genericUrl + "/get_participants";

        return new HttpPostEntity(url, root.toString());
    }

    public String eventJSON(String eventName, int clubID, String eventDate) throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put("name", eventName);
        leaf.put("club_id", clubID);
        leaf.put("date", eventDate);
        JSONObject root = new JSONObject();
        root.put("event", leaf);
        return root.toString();
    }

    public ApiCall createEvent(String eventName, int clubID, String eventDate) {
        try {
            HttpResponse response = new ApiCallTask().
                    execute(createEventPostEntity(eventName, clubID, eventDate)).get();
            if (response != null) {
                createAnswer = new JsonBuilder().execute(response).get();
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
        return new HttpPostEntity(genericUrl, eventJSON(eventName, clubID, eventDate));
    }

    public List<Event> getEventsByClub(int clubID) {
        List<Event> returnList = new ArrayList<Event>();

        try {
            HttpResponse response = new ApiCallTask().execute(getEventsByClubPostEntity(clubID)).get();
            if (response != null) {
                getEventsByClubAnswer = new JsonBuilder().execute(response).get();
                returnList = getEventsFromResponse(getEventsByClubAnswer, clubID);
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
        leaf.put("club_id", clubID);
        JSONObject root = new JSONObject();
        root.put("event", leaf);

        String url = genericUrl + "/get_events_by_club";

        return new HttpPostEntity(url, root.toString());
    }

    public List<User> getEventParticipants(int eventID) {
        List<User> returnList = new ArrayList<User>();

        try {
            HttpResponse response =
                    new ApiCallTask().execute(getParticipantsPostEntity(eventID)).get();
            if (response != null) {
                JSONObject participantsByEvent = new JsonBuilder().execute(response).get();
                JSONArray users = (JSONArray) participantsByEvent.get("events");
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

    private List<Event> getEventsFromResponse(JSONObject response, int clubID) {
        List<Event> eventList = new ArrayList<Event>();
        try {
            JSONArray eventsArray = (JSONArray) response.get("events");
            for (int i = 0; i < eventsArray.length(); i++) {
                JSONObject event = (JSONObject) eventsArray.get(i);
                String eventID = event.getString("id");
                String eventName = event.getString("name");
                String eventDate = event.getString("date");
                Event newEvent = new Event(Integer.valueOf(eventID), eventName, eventDate, clubID);
                eventList.add(newEvent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eventList;
    }

    //<editor-fold desc="Getter">
    public JSONObject getGetEventsByClubAnswer() {
        return getEventsByClubAnswer;
    }

    public JSONObject getCreateAnswer() {
        return createAnswer;
    }
    //</editor-fold>
}
