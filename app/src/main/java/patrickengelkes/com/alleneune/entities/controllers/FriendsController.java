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

import javax.inject.Inject;

import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;

/**
 * Created by patrickengelkes on 04/12/14.
 */
public class FriendsController {
    public static final String TAG = FriendsController.class.getSimpleName();
    private String genericUrl = "/friends";


    private JSONObject getFriendsAnswer;
    private JSONObject removeFriendFromClubAnswer;

    @Inject
    public FriendsController() {}

    public boolean getRegisteredFriends(List<String> phoneNumberList) throws JSONException {
        try {
            HttpResponse response = new ApiCallTask().
                    execute(getRegisteredFriendsPostEntity(phoneNumberList)).get();
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

    public HttpPostEntity getRegisteredFriendsPostEntity(List<String> phoneNumbers) throws JSONException, UnsupportedEncodingException {
        JSONArray phoneNumberArray = getJSONArrayFromList(phoneNumbers);
        JSONObject leaf = new JSONObject();
        leaf.put("phone_numbers", phoneNumberArray);
        JSONObject root = new JSONObject();
        root.put("friends", leaf);

        String url = genericUrl + "/getRegisteredFriends";

        return new HttpPostEntity(url, root.toString());
    }

    public boolean removeFriendFromClub(String clubName, String userName) throws JSONException {
        try {
            HttpResponse response = new ApiCallTask().
                    execute(removeFriendFromClubPostEntity(clubName, userName)).get();
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

    public HttpPostEntity removeFriendFromClubPostEntity(String clubName, String userName) throws JSONException, UnsupportedEncodingException {
        JSONObject leaf = new JSONObject();
        leaf.put("club_name", clubName);
        leaf.put("user_name", userName);
        JSONObject root = new JSONObject();
        root.put("friends", leaf);

        String url = genericUrl + "/removeFriendFromClub";

        return new HttpPostEntity(url, root.toString());
    }

    private JSONArray getJSONArrayFromList(List<String> list) {
        JSONArray returnArray = new JSONArray();
        for (String string : list) {
            returnArray.put(string);
        }

        return returnArray;
    }


    public JSONObject getGetFriendsAnswer() {
        return this.getFriendsAnswer;
    }

    public JSONObject getRemoveFriendFromClubAnswer() {
        return this.removeFriendFromClubAnswer;
    }



}
