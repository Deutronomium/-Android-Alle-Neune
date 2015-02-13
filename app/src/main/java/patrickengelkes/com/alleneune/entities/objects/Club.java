package patrickengelkes.com.alleneune.entities.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by patrickengelkes on 18/11/14.
 */
public class Club implements Parcelable {
    public static final String ID = "club_id";
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
    private String clubName;
    private int clubID;

    public Club() {
    }

    public Club(String clubName) {
        this.clubName = clubName;
    }

    public Club(String clubName, int clubID) {
        this.clubName = clubName;
        this.clubID = clubID;
    }

    //<editor-fold desc="Parcelable">
    protected Club(Parcel in) {
        this.clubName = in.readString();
        this.clubID = in.readInt();
    }

    //<editor-fold desc="Getter & Setter">
    public String getClubName() {
        return this.clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    //</editor-fold>

    public int getClubID() {
        return this.clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
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
    //</editor-fold>
}
