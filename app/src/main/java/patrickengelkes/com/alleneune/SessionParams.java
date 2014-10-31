package patrickengelkes.com.alleneune;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by patrickengelkes on 28/10/14.
 */
public class SessionParams {
    private String email;
    private String password;

    public SessionParams(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getJsonString() throws JSONException {
        JSONObject session = new JSONObject();
        session.put("email", this.email);
        session.put("password", this.password);
        return session.toString();
    }
}
