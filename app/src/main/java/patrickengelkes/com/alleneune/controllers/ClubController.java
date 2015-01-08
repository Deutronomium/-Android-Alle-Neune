package patrickengelkes.com.alleneune.controllers;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.JsonBuilder;

/**
 * Created by patrickengelkes on 12/12/14.
 */
public class ClubController {

    public static final String TAG = ClubController.class.getSimpleName();

    public List<String> getUsersByClub(String clubName) throws JSONException {
        List<String> returnList = new ArrayList<String>();

        JSONObject leaf = new JSONObject();
        leaf.put("name", clubName);
        JSONObject root = new JSONObject();
        root.put("club", leaf);

        try {
            HttpResponse response = new GetUsersByClubTask().execute(root.toString()).get();
            JSONObject jsonResponse = new JsonBuilder().execute(response).get();
            returnList = getUsersFromJsonRespone(jsonResponse);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return returnList;
    }

    public boolean deleteUserByName(String userName) {
        //JSONObject leaf = new JSONObject()
        return false;
    }

    private List<String> getUsersFromJsonRespone(JSONObject jsonResponse) throws JSONException {
        List<String> returnList = new ArrayList<String>();
        JSONArray usersArray = jsonResponse.getJSONArray("clubs");
        for (int i = 0; i < usersArray.length(); i++) {
            returnList.add((String) ((JSONObject) usersArray.get(i)).get("userName"));
        }

        return returnList;
    }

    public class GetUsersByClubTask extends AsyncTask<String, Integer, HttpResponse> {

        @Override
        protected HttpResponse doInBackground(String... strings) {
            try {
                String json = strings[0];
                StringEntity stringEntity = new StringEntity(json);

                MyHttpPost myHttpPost = new MyHttpPost(AbstractEntityController.host
                        + "/clubs/get_members_by_club", stringEntity);

                return new DefaultHttpClient().execute(myHttpPost);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public class DeleteUserByName extends AsyncTask<String, Integer, HttpResponse> {

        @Override
        protected HttpResponse doInBackground(String... strings) {
            try {
                String json = strings[0];
                StringEntity stringEntity = new StringEntity(json);

                MyHttpPost myHttpPost = new MyHttpPost(AbstractEntityController.host
                        + "/clubs/delete_by_name", stringEntity);

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
