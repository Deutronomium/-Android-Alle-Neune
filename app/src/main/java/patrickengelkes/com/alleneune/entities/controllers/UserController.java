package patrickengelkes.com.alleneune.entities.controllers;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.Club;
import patrickengelkes.com.alleneune.entities.objects.User;

/**
 * Created by patrickengelkes on 05/12/14.
 */
public class UserController {
    public static final String TAG = UserController.class.getSimpleName();

    private User user;

    public UserController(User user) {
        this.user = user;
    }

    public Club getClubByUser() throws JSONException {
        try {
            HttpResponse response = new ApiCallTask().execute(user.getUserClub()).get();
            JSONObject jsonResponse = new JsonBuilder().execute(response).get();
            if (jsonResponse != null) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    Log.e(TAG, "User already joined a club.");
                    JSONObject jsonClub = (JSONObject) jsonResponse.get("club");
                    return new Club(jsonClub.getString("name"), Integer.valueOf(jsonClub.getString("id")));
                } else if (response.getStatusLine().getStatusCode() == 450) {
                    Log.e(TAG, "User did not join a club yet.");
                    return null;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
