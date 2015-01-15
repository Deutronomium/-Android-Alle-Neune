package patrickengelkes.com.alleneune.entities.controllers;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.Session;

/**
 * Created by patrickengelkes on 15/01/15.
 */
public class SessionController {
    public static final String TAG = SessionController.class.getSimpleName();

    private Session session;
    private JSONObject createAnswer;

    public SessionController(Session session) {
        this.session = session;
    }

    public boolean createSession() {
        try {
            HttpResponse response = new ApiCallTask().execute(this.session.create()).get();
            if (response != null) {
                createAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 201) {
                    return true;
                } else {
                    Log.e(TAG, "Login failed");
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
