package patrickengelkes.com.alleneune.entities.objects;

import java.text.DecimalFormat;

/**
 * Created by patrickengelkes on 18/02/15.
 */
public class Fine {
    //attributes
    private int id;
    private String name;
    private double amount;
    private int clubID;

    //attribute strings for api calls
    public static String ROOT = "fine";
    public static String ID = "id";
    public static String NAME = "name";
    public static String AMOUNT = "amount";
    public static String CLUB_ID = "club_id";

    //urls
    public static String GENERIC_URL = "/fines";
    public static String GET_BY_CLUB = GENERIC_URL + "/get_by_club";


    public Fine(String name, double amount, int clubID) {
        this.name = name;
        this.amount = amount;
        this.clubID = clubID;
    }

    public Fine(int id, String name, double amount, int clubID) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.clubID = clubID;
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

    public String getShowAmount() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(this.amount);
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }
}
