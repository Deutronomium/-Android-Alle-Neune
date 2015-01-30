package patrickengelkes.com.alleneune.entities.objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;

/**
 * Created by patrickengelkes on 09/01/15.
 */
public class Event implements AbstractEntity, Parcelable{
    public static final String PARCELABLE = "event";

    private static String genericUrl = "/events";
    private int eventID;
    private String eventName;
    private String eventDate;
    private int clubID;

    public Event(String eventName, String eventDate, int clubID) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.clubID = clubID;
    }

    public Event(int eventID, String eventName, String eventDate, int clubID) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.clubID = clubID;
    }

    public Event(int clubID) {
        this.clubID = clubID;
    }

    public String genericJSON() throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put("name", this.eventName);
        leaf.put("club_id", this.clubID);
        leaf.put("date", this.eventDate);

        JSONObject root = new JSONObject();
        root.put("event", leaf);

        return root.toString();
    }

    @Override
    public HttpPostEntity create() throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(genericUrl, genericJSON());
    }

    public HttpPostEntity getAll() throws JSONException, UnsupportedEncodingException {
        JSONObject leaf = new JSONObject();
        leaf.put("club_id", this.clubID);
        JSONObject root = new JSONObject();
        root.put("event", leaf);

        String url = genericUrl + "/get_events_by_club";

        return new HttpPostEntity(url, root.toString());
    }

    public static HttpPostEntity getParticipants(int eventID) throws JSONException, UnsupportedEncodingException {
        JSONObject leaf = new JSONObject();
        leaf.put("event_id", eventID);
        JSONObject root = new JSONObject();
        root.put("event", leaf);

        String url = genericUrl + "/get_participants";

        return new HttpPostEntity(url, root.toString());
    }

    public String getEventName() {
        return this.eventName;
    }

    public String getEventDate() {
        return this.eventDate;
    }

    public int getClubID() {
        return this.clubID;
    }

    public int getEventID() {
        return this.eventID;
    }

    public static List<Event> getEventsFromResponse(JSONObject response, int clubID) {
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

    //<editor-fold desc="Parcelable">
    protected Event(Parcel in) {
        genericUrl = in.readString();
        eventID = in.readInt();
        eventName = in.readString();
        eventDate = in.readString();
        clubID = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(genericUrl);
        dest.writeInt(eventID);
        dest.writeString(eventName);
        dest.writeString(eventDate);
        dest.writeInt(clubID);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
    //</editor-fold>
}
