package patrickengelkes.com.alleneune.Objects;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.JsonBuilder;
import patrickengelkes.com.alleneune.controllers.AbstractEntityController;
import patrickengelkes.com.alleneune.controllers.MyHttpPost;

/**
 * Created by patrickengelkes on 27/11/14.
 */
public class Friends implements AbstractEntity{
    public static final String TAG = Friends.class.getSimpleName();

    private JSONArray phoneNumbers = new JSONArray();
    private HashMap<String, Object> objectParameters;
    private String objectString;
    private JSONObject getFriendsAbstractAnswer;

    public Friends(String[] phoneNumbers) {
        for (String string : phoneNumbers) {
            this.phoneNumbers.put(string);
        }
        prepareEntity();
    }

    public JSONObject getGetFriendsAbstractAnswer() {
        return this.getFriendsAbstractAnswer;
    }

    public boolean getRegisteredFriends() {
        try {
            HttpResponse response = new GetRegisteredFriendsTask().execute(this).get();
            if (response != null) {
                this.getFriendsAbstractAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 200) {
                    return true;
                } else {
                    Log.e(TAG, "Could not retrieve friends!");
                    Log.e(TAG, getFriendsAbstractAnswer.toString());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public HashMap<String, Object> getObjectParameters() {
        HashMap<String, Object> objectParameters = new HashMap<String, Object>();
        objectParameters.put("phone_numbers", this.phoneNumbers);

        return objectParameters;
    }

    @Override
    public String getJsonString() throws JSONException {
        JSONObject parameters = new JSONObject();
        for (Map.Entry<String, Object> cursor : this.objectParameters.entrySet()) {
            parameters.put(cursor.getKey(), cursor.getValue());
        }
        JSONObject root = new JSONObject();
        root.put(this.objectString, parameters);

        return root.toString();
    }

    @Override
    public String getObjectString() {
        return this.objectString;
    }

    @Override
    public void prepareEntity() {
        this.objectParameters = getObjectParameters();

        this.objectString = "friends";
    }

    private class GetRegisteredFriendsTask extends AsyncTask<Friends, Integer, HttpResponse> {

        @Override
        protected HttpResponse doInBackground(Friends... friendses) {
            try {
                Friends friends = friendses[0];
                String json = friends.getJsonString();
                StringEntity stringEntity = new StringEntity(json);

                MyHttpPost myHttpPost = new MyHttpPost(AbstractEntityController.host + "/" +
                        friends.getObjectString() + "/registeredFriends", stringEntity);
                return new DefaultHttpClient().execute(myHttpPost);
            } catch (JSONException e) {
                e.printStackTrace();
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

    public List<User> getUserListFromJSONResponse(JSONArray friends) {
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < friends.length(); i++) {
            try {
                JSONObject friend = (JSONObject)friends.get(i);
                String userName = friend.getString("userName");
                String firstName = friend.getString("firstName");
                String lastName = friend.getString("lastName");
                String email = friend.getString("email");
                String city = friend.getString("city");
                User user = new User(userName, firstName, lastName, email, city);
                userList.add(user);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return userList;
    }
}
