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
public class Club implements Parcelable {
    private static Club mInstance = null;

    private String genericUrl = "/clubs";
    private String clubName;
    private int clubID;

    public Club() {}

    public Club(String clubName) {
        this.clubName = clubName;
    }

    public Club(String clubName, int clubID) {
        this.clubName = clubName;
        this.clubID = clubID;
    }

    public static Club getInstance(){
        if(mInstance == null)
        {
            mInstance = new Club();
        }
        return mInstance;
    }

    //<editor-fold desc="Getter & Setter">
    public String getClubName() {
        return this.clubName;
    }

    public int getClubID() {
        return this.clubID;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }
    //</editor-fold>

    //<editor-fold desc="Parcelable">
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
    //</editor-fold>
}
