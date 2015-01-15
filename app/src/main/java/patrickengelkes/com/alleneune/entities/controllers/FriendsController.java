package patrickengelkes.com.alleneune.entities.controllers;

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

import patrickengelkes.com.alleneune.api_calls.MyHttpPost;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.User;

/**
 * Created by patrickengelkes on 04/12/14.
 */
public class FriendsController {

    public static final String TAG = FriendsController.class.getSimpleName();

    public static String objectString = "friends";

    private JSONObject getFriendsAbstractAnswer;
    private JSONObject removeFriendFromClubAbstractAnswer;

    public boolean getRegisteredFriends(List<String> phoneNumberList) throws JSONException {
        JSONArray phoneNumberArray = getJSONArrayFromList(phoneNumberList);
        JSONObject leaf = new JSONObject();
        leaf.put("phone_numbers", phoneNumberArray);
        JSONObject root = new JSONObject();
        root.put(this.objectString, leaf);

        try {
            HttpResponse response = new GetRegisteredFriendsTask().execute(root.toString()).get();
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

    public boolean addFriendsToClub(String clubName, List<String> phoneNumberList) throws JSONException {
        JSONArray phoneNumberArray = getJSONArrayFromList(phoneNumberList);
        JSONObject leaf = new JSONObject();
        leaf.put("club", clubName);
        leaf.put("members", phoneNumberArray);
        JSONObject root = new JSONObject();
        root.put("add_members", leaf);

        try {
            HttpResponse response = new AddFriendsToClubTask().execute(root.toString()).get();
            if (response != null) {
                this.removeFriendFromClubAbstractAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 200) {
                    return true;
                } else {
                    Log.e(TAG, "Could not add friends to club!");
                    Log.e(TAG, removeFriendFromClubAbstractAnswer.toString());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean removeFriendFromClub(String clubName, String userName) throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put("club_name", clubName);
        leaf.put("user_name", userName);
        JSONObject root = new JSONObject();
        root.put("friends", leaf);

        try {
            HttpResponse response = new RemoveFriendFromClubTask().execute(root.toString()).get();
            if (response != null) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    return true;
                } else {
                    Log.e(TAG, "Could not remove friend from club!");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    private class GetRegisteredFriendsTask extends AsyncTask<String, Integer, HttpResponse> {

        @Override
        protected HttpResponse doInBackground(String... strings) {
            try {
                String json = strings[0];
                StringEntity stringEntity = new StringEntity(json);

                MyHttpPost myHttpPost = new MyHttpPost(AbstractEntityController.host + "/" +
                        FriendsController.objectString + "/getRegisteredFriends", stringEntity);
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

    private class AddFriendsToClubTask extends AsyncTask<String, Integer, HttpResponse> {

        @Override
        protected HttpResponse doInBackground(String... strings) {
            try {
                String json = strings[0];
                StringEntity stringEntity = new StringEntity(json);

                MyHttpPost myHttpPost = new MyHttpPost(AbstractEntityController.host + "/" +
                    "clubs/add_members", stringEntity);
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

    private class RemoveFriendFromClubTask extends AsyncTask<String, Integer, HttpResponse> {

        @Override
        protected HttpResponse doInBackground(String... strings) {
            try {
                String json = strings[0];
                StringEntity stringEntity = new StringEntity(json);

                MyHttpPost myHttpPost = new MyHttpPost(AbstractEntityController.host + "/" +
                    "friends/removeFriendFromClub", stringEntity);
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

    public List<User> getUserListFromJSONResponse(JSONArray friends) {
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < friends.length(); i++) {
            try {
                JSONObject friend = (JSONObject)friends.get(i);
                String userName = friend.getString("userName");
                String firstName = friend.getString("firstName");
                String lastName = friend.getString("lastName");
                String phoneNumber = friend.getString("phone_number");

                User user = new User();
                user.setUserName(userName);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPhoneNumber(phoneNumber);

                userList.add(user);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return userList;
    }

    public JSONArray getJSONArrayFromList(List<String> list) {
        JSONArray returnArray = new JSONArray();
        for (String string : list) {
            returnArray.put(string);
        }

        return returnArray;
    }

    public JSONObject getGetFriendsAbstractAnswer() {
        return this.getFriendsAbstractAnswer;
    }
}
