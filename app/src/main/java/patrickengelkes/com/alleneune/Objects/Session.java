package patrickengelkes.com.alleneune.Objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrickengelkes on 31/10/14.
 */
public class Session implements AbstractEntity {

    private String email;
    private String password;

    private HashMap<String, Object> objectParameters;

    private String objectString;

    public Session(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public HashMap<String, Object> getObjectParameters() {
        HashMap<String, Object> objectParameters = new HashMap<String, Object>();
        objectParameters.put("email", this.email);
        objectParameters.put("password", this.password);

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

    @Override
    public void prepareEntity() {
        this.objectParameters = getObjectParameters();

        this.objectString = "session";
    }
}
