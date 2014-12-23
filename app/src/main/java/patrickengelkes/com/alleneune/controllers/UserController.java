package patrickengelkes.com.alleneune.controllers;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.JsonBuilder;
import patrickengelkes.com.alleneune.Objects.Club;

/**
 * Created by patrickengelkes on 05/12/14.
 */
public class UserController {

    public Club getClubByUser(String userName) throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put("userName", userName);
        JSONObject root = new JSONObject();
        root.put("user", leaf);

        try {
            HttpResponse response = new GetClubByUserTask().execute(root.toString()).get();
            JSONObject jsonResponse = new JsonBuilder().execute(response).get();
            if (jsonResponse != null) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    JSONObject jsonClub = (JSONObject) jsonResponse.get("club");
                    Club club = new Club(jsonClub.getString("name"));
                    return club;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    private class GetClubByUserTask extends AsyncTask<String, Integer, HttpResponse> {

        @Override
        protected HttpResponse doInBackground(String... strings) {
            try {
                String json = strings[0];
                StringEntity stringEntity = new StringEntity(json);

                MyHttpPost myHttpPost = new MyHttpPost(AbstractEntityController.host
                        + "/users/user_club", stringEntity);
                return new DefaultHttpClient().execute(myHttpPost);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
