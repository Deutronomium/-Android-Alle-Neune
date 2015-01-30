package patrickengelkes.com.alleneune.entities.controllers;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.Event;
import patrickengelkes.com.alleneune.entities.objects.User;

/**
 * Created by patrickengelkes on 23/01/15.
 */
public class EventController {
    public static final String TAG = EventController.class.getSimpleName();

    private Event event;
    private JSONObject getEventsByClubAnswer;

    public EventController(Event event) {
        this.event = event;
    }

    public EventController() {}

    public List<Event> getEventsByClub() {
        List<Event> returnList = new ArrayList<Event>();

        try {
            HttpResponse response = new ApiCallTask().execute(event.getAll()).get();
            if (response != null) {
                getEventsByClubAnswer = new JsonBuilder().execute(response).get();
                returnList = Event.getEventsFromResponse(getEventsByClubAnswer, this.event.getClubID());
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

    public List<User> getEventParticipants(int eventID) {
        List<User> returnList = new ArrayList<User>();

        try {
            HttpResponse response =
                    new ApiCallTask().execute(Event.getParticipants(eventID)).get();
            if (response != null) {
                JSONObject participantsByEvent = new JsonBuilder().execute(response).get();
                JSONArray users = (JSONArray) participantsByEvent.get("events");
                returnList = User.getUserListFromJSONResponse(users);
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
}
