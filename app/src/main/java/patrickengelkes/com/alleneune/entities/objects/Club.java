package patrickengelkes.com.alleneune.entities.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by patrickengelkes on 18/11/14.
 */
public class Club implements Parcelable {
    //attributes
    private String name;
    private int id;

    //attribute strings for api calls
    public static String ROOT = "club";
    public static String NAME = "name";
    public static String ID = "id";
    public static String MEMBERS = "members";

    //urls
    public static String GENERIC_URL = "/clubs";
    public static String VALIDITY = GENERIC_URL + "/validity";
    public static String GET_MEMBERS_BY_CLUB = GENERIC_URL + "/get_members_by_club";
    public static String ADD_MEMBERS = GENERIC_URL + "/add_members";

    public Club() {
    }

    public Club(String name) {
        this.name = name;
    }

    public Club(String name, int id) {
        this.name = name;
        this.id = id;
    }

    //<editor-fold desc="Getter & Setter">
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    //</editor-fold>

    //<editor-fold desc="Parcelable">
    protected Club(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeInt(this.id);
    }

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
