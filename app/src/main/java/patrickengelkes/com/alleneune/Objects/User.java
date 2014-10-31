package patrickengelkes.com.alleneune.Objects;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import patrickengelkes.com.alleneune.Objects.AbstractEntity;

/**
 * Created by patrickengelkes on 31/10/14.
 */
public class User implements AbstractEntity {

    private String userName;
    private String email;
    private String password;
    private String passwordConfirmation;

    private HashMap<String, Object> objectParameters;

    private String objectString;


    public User(String userName, String email, String password, String passwordConfirmation) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;

        this.objectParameters = getObjectParameters();

        this.objectString = "user";
    }

    @Override
    public HashMap<String, Object> getObjectParameters() {
        HashMap<String, Object> objectParameters = new HashMap<String, Object>();
        objectParameters.put("userName", this.userName);
        objectParameters.put("email", this.email);
        objectParameters.put("password", this.password);
        objectParameters.put("password_confirmation", this.passwordConfirmation);
        return objectParameters;
    }

    @Override
    public String getJsonString() throws JSONException {
        JSONObject parameters = new JSONObject();
        for (Map.Entry<String, Object> cursor : this.objectParameters.entrySet()) {
            parameters.put(cursor.getKey(), cursor.getValue());
        }
        JSONObject root = new JSONObject();
        root.put(this.objectString, parameters);
        return root.toString();
    }

    @Override
    public String getObjectString() {
        return this.objectString;
    }
}
