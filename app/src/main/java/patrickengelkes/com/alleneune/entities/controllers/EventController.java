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

    public List<Event> getEventsByClub() {
        List<Event> returnList = new ArrayList<Event>();

        try {
            HttpResponse response = new ApiCallTask().execute(event.getAll()).get();
            if (response != null) {
                getEventsByClubAnswer = new JsonBuilder().execute(response).get();
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

    private List<Event> getEventsFromResponse(JSONObject response) {
        List<Event> eventList = new ArrayList<Event>();
        try {
            JSONArray eventsArray = (JSONArray) response.get("events");
            for (int i = 0; i < eventsArray.length(); i++) {
                JSONObject event = (JSONObject) eventsArray.get(i);
                String eventName = event.getString("name");
                String eventDate = event.getString("date");
                Event newEvent = new Event(eventName, eventDate, this.event.getClubID());
                eventList.add(newEvent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eventList;
    }

}
