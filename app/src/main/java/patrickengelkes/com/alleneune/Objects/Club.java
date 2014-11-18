package patrickengelkes.com.alleneune.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by patrickengelkes on 18/11/14.
 */
public class Club implements Parcelable, AbstractEntity {

    private String clubName;

    private HashMap<String, Object> objectParameters;

    private String objectString;

    public Club(String clubName) {
        this.clubName = clubName;
    }

    @Override
    public HashMap<String, Object> getObjectParameters() {
        HashMap<String, Object> objectParameters = new HashMap<String, Object>();
        objectParameters.put("name", this.clubName);

        return objectParameters;
    }

    @Override
    public String getJsonString() throws JSONException {
        JSONObject parameters = new JSONObject();
        for (Map.Entry<String, Object> cursor : this.objectParameters.entrySet()) {
            parameters.put(cursor.getKey(), cursor.getValue());
        }
        JSONObject root = new JSONObject();
        root.put(this.objectString, parameters);
        return root.toString();

    }

    @Override
    public String getObjectString() {
        return this.objectString;
    }

    @Override
    public void prepareEntity() {
        this.objectParameters = getObjectParameters();

        this.objectString = "club";
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
