package patrickengelkes.com.alleneune.entities.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;

/**
 * Created by patrickengelkes on 09/01/15.
 */
public class Event implements AbstractEntity{

    private String genericUrl = "/events";
    private String eventName;
    private String eventDate;
    private int clubID;

    public Event(String eventName, String eventDate, int clubID) {
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

    public String getEventName() {
        return this.eventName;
    }

    public String getEventDate() {
        return this.eventDate;
    }

    public int getClubID() {
        return this.clubID;
    }
}
