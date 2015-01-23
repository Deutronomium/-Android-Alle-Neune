package patrickengelkes.com.alleneune.entities.objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;

/**
 * Created by patrickengelkes on 18/11/14.
 */
public class Club implements Parcelable, AbstractValidityEntity {

    private String genericUrl = "/clubs";
    private String clubName;
    private int clubID;

    public Club(String clubName) {
        this.clubName = clubName;
    }

    public Club(String clubName, int clubID) {
        this.clubName = clubName;
        this.clubID = clubID;
    }

    private String genericJSON() throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put("name", clubName);
        JSONObject root = new JSONObject();
        root.put("club", leaf);

        return root.toString();
    }

    @Override
    public HttpPostEntity create() throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(genericUrl, genericJSON());
    }

    @Override
    public HttpPostEntity checkValidity() throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(genericUrl + "/validity", genericJSON());
    }

    public HttpPostEntity addFriends(List<String> phoneNumberList) throws JSONException, UnsupportedEncodingException {
        JSONArray phoneNumberArray = getJSONArrayFromList(phoneNumberList);
        JSONObject leaf = new JSONObject();
        leaf.put("name", this.clubName);
        leaf.put("members", phoneNumberArray);
        JSONObject root = new JSONObject();
        root.put("club", leaf);

        String url = genericUrl + "/add_members";

        return new HttpPostEntity(url, root.toString());
    }

    private JSONArray getJSONArrayFromList(List<String> list) {
        JSONArray returnArray = new JSONArray();
        for (String string : list) {
            returnArray.put(string);
        }

        return returnArray;
    }

    public HttpPostEntity getUsersByClub() throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(genericUrl + "/get_members_by_club", genericJSON());
    }

    public String getClubName() {
        return this.clubName;
    }

    public int getClubID() {
        return this.clubID;
    }

    //Parcelable
    protected Club(Parcel in) {
        this.clubName = in.readString();
        this.clubID = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.clubName);
        parcel.writeInt(this.clubID);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Club> CREATOR = new Creator<Club>() {

        @Override
        public Club createFromParcel(Parcel parcel) {
            return new Club(parcel);
        }

        @Override
        public Club[] newArray(int size) {
            return new Club[size];
        }
    };
}
