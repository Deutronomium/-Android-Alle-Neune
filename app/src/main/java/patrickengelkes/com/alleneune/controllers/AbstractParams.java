package patrickengelkes.com.alleneune.controllers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import patrickengelkes.com.alleneune.Objects.AbstractEntity;

/**
 * Created by patrickengelkes on 31/10/14.
 */
public class AbstractParams {
    private AbstractEntity abstractEntity;
    private HashMap<String, Object> objectParameters;

    public AbstractParams(AbstractEntity abstractEntity) {
        this.abstractEntity = abstractEntity;
    }

    public String getJsonString() throws JSONException {
        JSONObject objectParameters = new JSONObject();
        for (Map.Entry<String, Object> cursor : this.abstractEntity.getObjectParameters().entrySet()) {
            objectParameters.put(cursor.getKey(), cursor.getValue());
        }

        JSONObject objectRoot = new JSONObject();
        objectRoot.put(this.abstractEntity.getObjectString(), objectParameters);

        return objectRoot.toString();
    }




}
