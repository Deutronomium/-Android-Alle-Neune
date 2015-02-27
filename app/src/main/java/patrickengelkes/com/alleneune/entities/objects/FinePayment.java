package patrickengelkes.com.alleneune.entities.objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by patrickengelkes on 26/02/15.
 */
public class FinePayment {
    public static String ROOT = "fine_payment";
    //attribute strings for api calls
    public static String ID = "id";
    public static String PARTICIPATION_ID = "participation_id";
    public static String USER_ID = "user_id";
    public static String EVENT_ID = "event_id";
    public static String FINE_ID = "fine_id";

    //urls
    public static String GENERIC_URL = "/fine_payments";
    public static String GET_BY_USER_AND_EVENT = GENERIC_URL + "/get_by_user_and_event";

    //attributes
    private int id;
    private int participationID;
    private int fineID;

    public FinePayment(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt(ID);
        this.participationID = jsonObject.getInt(PARTICIPATION_ID);
        this.fineID = jsonObject.getInt(FINE_ID);
    }
}
