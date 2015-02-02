package patrickengelkes.com.alleneune.entities.controllers;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.Session;
import patrickengelkes.com.alleneune.entities.objects.User;
import patrickengelkes.com.alleneune.enums.ApiCall;

/**
 * Created by patrickengelkes on 24/01/15.
 */
public class SessionController {
    public static final String TAG = SessionController.class.getSimpleName();

    private Session session;

    public SessionController(Session session) {
        this.session = session;
    }

    public ApiCall logIn() {
        try {
            HttpResponse response = new ApiCallTask().execute(this.session.logIn()).get();
            if (response != null) {
                JSONObject jsonResponse = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 200) {
                    User.setUserSingleton(jsonResponse);
                    return ApiCall.SUCCESS;
                } else {
                    return ApiCall.ACCESS_DENIED;
                }
            } else {
                return ApiCall.BAD_REQUEST;
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

        return ApiCall.BAD_REQUEST;
    }

}
