package patrickengelkes.com.alleneune.entities.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by patrickengelkes on 09/01/15.
 */
public class Event {

    private String eventName;
    private String eventDate;
    private String clubID;

    public Event(String eventName, String eventDate, int clubID) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.clubID = String.valueOf(clubID);
    }

    public JSONObject createEventJsonObject(){
        JSONObject leaf = new JSONObject();
        try {
            leaf.put("name", this.eventName);
            leaf.put("club_id", this.clubID);
            leaf.put("date", this.eventDate);

            JSONObject root = new JSONObject();
            root.put("event", leaf);

            return root;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
