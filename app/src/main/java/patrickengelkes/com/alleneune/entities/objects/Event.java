package patrickengelkes.com.alleneune.entities.objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by patrickengelkes on 09/01/15.
 */
public class Event implements Parcelable{
    //parcelable
    public static final String PARCELABLE = "event";

    //attributes
    private int id;
    private String name;
    private String date;
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
    public static String GET_BY_CLUB = GENERIC_URL + "/get_by_club";

    public Event(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt(ID);
        this.name = jsonObject.getString(NAME);
        this.date = jsonObject.getString(DATE);
        this.clubID = jsonObject.getInt(CLUB_ID);
    }

    public Event(int clubID) {
        this.clubID = clubID;
    }

    //<editor-fold desc="Getter & Setter">
    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public int getClubID() {
        return this.clubID;
    }

    public int getId() {
        return this.id;
    }
    //</editor-fold>

    //<editor-fold desc="Parcelable">
    protected Event(Parcel in) {
        id = in.readInt();
        name = in.readString();
        date = in.readString();
        clubID = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(date);
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
