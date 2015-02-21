package patrickengelkes.com.alleneune.entities.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;

/**
 * Created by patrickengelkes on 31/10/14.
 */
public class Session{
    //attributes
    private String email;
    private String password;

    //attributes for rails api calls
    public static String ROOT = "session";
    public static String EMAIL = "email";
    public static String PASSWORD = "password";

    //urls
    public static String GENERIC_URL = "/sessions";
}
