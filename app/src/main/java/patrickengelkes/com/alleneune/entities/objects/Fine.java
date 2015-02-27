package patrickengelkes.com.alleneune.entities.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by patrickengelkes on 18/02/15.
 */
public class Fine {
    //attribute strings for api calls
    public static String ROOT = "fine";
    public static String ROOTS = "fines";
    public static String ID = "id";
    public static String NAME = "name";
    public static String AMOUNT = "amount";
    public static String CLUB_ID = "club_id";
    //urls
    public static String GENERIC_URL = "/fines";
    public static String GET_BY_CLUB = GENERIC_URL + "/get_by_club";
    public static String UPDATE = GENERIC_URL + "/";
    //attributes
    private int id;
    private String name;
    private double amount;
    private int clubID;


    public Fine(String name, double amount, int clubID) {
        this.name = name;
        this.amount = amount;
        this.clubID = clubID;
    }

    public Fine(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt(ID);
        this.name = jsonObject.getString(NAME);
        this.amount = jsonObject.getDouble(AMOUNT);
        this.clubID = jsonObject.getInt(CLUB_ID);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getShowAmount() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(this.amount);
    }

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }
}
