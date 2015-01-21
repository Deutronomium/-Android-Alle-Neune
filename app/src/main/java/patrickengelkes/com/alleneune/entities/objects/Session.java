package patrickengelkes.com.alleneune.entities.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;

/**
 * Created by patrickengelkes on 31/10/14.
 */
public class Session implements AbstractEntity{

    private String genericUrl = "/sessions";

    private String email;
    private String password;

    public Session(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private String genericJSON() throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put("email", this.email);
        leaf.put("password", this.password);
        JSONObject root = new JSONObject();
        root.put("session", leaf);

        return root.toString();
    }

    @Override
    public HttpPostEntity create() throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(genericUrl, genericJSON());
    }
}
