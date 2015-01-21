package patrickengelkes.com.alleneune.entities.controllers;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.Friend;
import patrickengelkes.com.alleneune.entities.objects.User;

/**
 * Created by patrickengelkes on 04/12/14.
 */
public class FriendsController {

    public static final String TAG = FriendsController.class.getSimpleName();

    private JSONObject getFriendsAnswer;
    private JSONObject removeFriendFromClubAnswer;
    private Friend friend;

    public FriendsController() {
        this.friend = new Friend();
    }

    public boolean getRegisteredFriends(List<String> phoneNumberList) throws JSONException {
        try {
            HttpResponse response = new ApiCallTask().
                    execute(friend.getRegisteredFriends(phoneNumberList)).get();
            if (response != null) {
                this.getFriendsAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 200) {
                    return true;
                } else {
                    Log.e(TAG, "Could not retrieve friends");
                    Log.e(TAG, this.getFriendsAnswer.toString());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean removeFriendFromClub(String clubName, String userName) throws JSONException {
        try {
            HttpResponse response = new ApiCallTask().
                    execute(friend.removeFriendFromClub(clubName, userName)).get();
            if (response != null) {
                this.removeFriendFromClubAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 200) {
                    return true;
                } else {
                    Log.e(TAG, "Could not remove friend from club");
                    Log.e(TAG, removeFriendFromClubAnswer.toString());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return false;
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

    public JSONObject getGetFriendsAnswer() {
        return this.getFriendsAnswer;
    }

    public JSONObject getRemoveFriendFromClubAnswer() {
        return this.removeFriendFromClubAnswer;
    }

}
