package patrickengelkes.com.alleneune.entities.controllers;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.MyHttpPost;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.Club;
import patrickengelkes.com.alleneune.entities.objects.User;

/**
 * Created by patrickengelkes on 05/12/14.
 */
public class UserController {

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
                    JSONObject jsonClub = (JSONObject) jsonResponse.get("club");
                    return new Club(jsonClub.getString("name"), Integer.valueOf(jsonClub.getString("id")));
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
