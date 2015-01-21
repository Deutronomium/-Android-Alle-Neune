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

    private AbstractEntity abstractEntity;
    private JSONObject createAnswer;

    public AbstractEntityController(AbstractEntity abstractEntity) {
        this.abstractEntity = abstractEntity;
    }

    public boolean createAbstractEntity() {
        try {
            HttpResponse response = new ApiCallTask().execute(this.abstractEntity.create()).get();
            if (response != null) {
                createAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 201) {
                    return true;
                } else {
                    Log.e(TAG, "Creating entity failed");
                    Log.e(TAG, createAnswer.toString());
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

    public JSONObject getCreateAnswer() {
        return this.createAnswer;
    }
}
