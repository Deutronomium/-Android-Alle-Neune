package patrickengelkes.com.alleneune.entities.objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;

/**
 * Created by patrickengelkes on 18/11/14.
 */
public class Club implements Parcelable, AbstractEntity {

    private String genericUrl = "/clubs";
    private String clubName;

    public Club(String clubName) {
        this.clubName = clubName;
    }

    private String genericJSON() throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put("name", clubName);
        JSONObject root = new JSONObject();
        root.put("club", leaf);

        return root.toString();
    }

    public HttpPostEntity create() throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(genericUrl, genericJSON());
    }

    public HttpPostEntity checkValidity() throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(genericUrl + "/validity", genericJSON());
    }

    public HttpPostEntity getUsersByClub() throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(genericUrl + "/get_members_by_club", genericJSON());
    }

    public String getClubName() {
        return this.clubName;
    }

    //Parcelable
    protected Club(Parcel in) {
        this.clubName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.clubName);
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
