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
public class Event implements Parcelable{
    //parcelable
    public static final String PARCELABLE = "event";

    //attributes
    private int eventID;
    private String eventName;
    private String eventDate;
    private int clubID;

    //attribute strings for api calls
    public static String ROOT = "event";
    public static String ID = "id";
    public static String NAME = "name";
    public static String CLUB_ID = "club_id";
    public static String DATE = "date";
    public static String EVENT_ID = "event_id";

    //urls
    public static String GENERIC_URL = "/events";
    public static String GET_PARTICIPANTS = GENERIC_URL + "/get_participants";
    public static String GET_BY_CLUB = GENERIC_URL + "/get_events_by_club";

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

    //<editor-fold desc="Getter & Setter">
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
    //</editor-fold>



    //<editor-fold desc="Parcelable">
    protected Event(Parcel in) {
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
