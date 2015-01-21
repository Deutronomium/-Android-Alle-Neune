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
    private String clubID;

    public Event(String eventName, String eventDate, int clubID) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.clubID = String.valueOf(clubID);
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
}
