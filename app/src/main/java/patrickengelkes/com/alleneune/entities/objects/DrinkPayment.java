package patrickengelkes.com.alleneune.entities.objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by patrickengelkes on 25/02/15.
 */
public class DrinkPayment {
    public static String ROOT = "drink_payment";
    //attribute string for api calls
    public static String ID = "id";
    public static String PARTICIPATION_ID = "participation_id";
    public static String USER_ID = "user_id";
    public static String EVENT_ID = "event_id";
    public static String DRINK_ID = "drink_id";
    //urls
    public static String GENERIC_URL = "/drink_payments";
    //attributes
    private int id;
    private int participationID;
    private int drinkID;

    public DrinkPayment(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt(ID);
        this.participationID = jsonObject.getInt(PARTICIPATION_ID);
        this.drinkID = jsonObject.getInt(DRINK_ID);
    }
}
