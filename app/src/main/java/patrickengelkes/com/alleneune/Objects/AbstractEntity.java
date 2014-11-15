package patrickengelkes.com.alleneune.Objects;

import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by patrickengelkes on 31/10/14.
 */
public interface AbstractEntity {

    public HashMap<String, Object> getObjectParameters();

    public String getJsonString() throws JSONException;

    public String getObjectString();

    public void prepareEntity();
}
