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

/**
 * Created by patrickengelkes on 24/01/15.
 */
public class SessionController {
    public static final String TAG = SessionController.class.getSimpleName();

    private Session session;

    public SessionController(Session session) {
        this.session = session;
    }

    public void logIn() {
        try {
            HttpResponse response = new ApiCallTask().execute(this.session.logIn()).get();
            if (response != null) {
                JSONObject jsonResponse = new JsonBuilder().execute(response).get();
                setUserSingleton(jsonResponse);
            } else {
                //TODO: Error handling
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
    }

    private User setUserSingleton(JSONObject response) throws JSONException {
        JSONObject userJson = (JSONObject)response.get("user");

        User user = User.getInstance();
        user.setUserName((String) userJson.get("userName"));
        user.setFirstName((String) userJson.get("firstName"));
        user.setLastName((String) userJson.get("lastName"));
        user.setPhoneNumber((String) userJson.get("phoneNumber"));

        return user;
    }

}
