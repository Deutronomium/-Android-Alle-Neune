package patrickengelkes.com.alleneune.entities.controllers;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.AbstractEntity;

/**
 * Created by patrickengelkes on 31/10/14.
 */
public class AbstractEntityController {
    public static final String TAG = AbstractEntityController.class.getSimpleName();
    public static final String host = "http://192.168.56.1:3000";

    private AbstractEntity abstractEntity;
    private JSONObject createAbstractAnswer;
    private JSONObject validateAbstractAnswer;

    public AbstractEntityController(AbstractEntity abstractEntity) {
        this.abstractEntity = abstractEntity;
    }

    public boolean createAbstractEntity() {
        try {
            HttpResponse response = new ApiCallTask().execute(this.abstractEntity.create()).get();
            if (response != null) {
                createAbstractAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 201) {
                    return true;
                } else {
                    Log.e(TAG, "Creating entity failed");
                    Log.e(TAG, createAbstractAnswer.toString());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean checkForValidity() {
        try {
            HttpResponse response = new ApiCallTask().execute(this.abstractEntity.checkValidity()).get();
            if (response != null) {
                validateAbstractAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 200) {
                    return true;
                } else {
                    Log.e(TAG, "Invalid entity");
                    Log.e(TAG, validateAbstractAnswer.toString());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return false;
    }

    public JSONObject getCreateAbstractAnswer() {
        return this.createAbstractAnswer;
    }

    public JSONObject getValidateAbstractAnswer() { return this.validateAbstractAnswer; }
}
