package patrickengelkes.com.alleneune.entities.controllers;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.AbstractValidityEntity;

/**
 * Created by patrickengelkes on 16/01/15.
 */
public class AbstractValidityEntityController {
    public static final String TAG = AbstractValidityEntityController.class.getSimpleName();

    private AbstractValidityEntity abstractValidityEntity;
    private JSONObject validateAnswer;

    public AbstractValidityEntityController(AbstractValidityEntity abstractValidityEntity) {
        this.abstractValidityEntity = abstractValidityEntity;
    }

    public boolean checkForValidity() {
        try {
            HttpResponse response = new ApiCallTask().execute(this.abstractValidityEntity.checkValidity()).get();
            if (response != null) {
                validateAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 200) {
                    return true;
                } else {
                    Log.e(TAG, "Invalid entity");
                    Log.e(TAG, validateAnswer.toString());
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

    public JSONObject getValidateAnswer() {
        return this.validateAnswer;
    }
}
