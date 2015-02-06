package patrickengelkes.com.alleneune.entities.controllers;

import android.util.Log;

import com.google.inject.Inject;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;

/**
 * Created by patrickengelkes on 12/12/14.
 */
public class ClubController {
    public static final String TAG = ClubController.class.getSimpleName();
    private String genericUrl = "/clubs";

    private JSONObject addFriendsAnswer;
    private JSONObject validateAnswer;
    private JSONObject createAnswer;

    @Inject
    public ClubController() {

    }

    private String clubJSON(String clubName) throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put("name", clubName);
        JSONObject root = new JSONObject();
        root.put("club", leaf);

        return root.toString();
    }

    public boolean createClub(String clubName) {
        try {
            HttpResponse response = new ApiCallTask().execute(createClubPostEntity(clubName)).get();
            if (response != null) {
                createAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 201) {
                    return true;
                } else {
                    Log.e(TAG, "Creating entity failed");
                    Log.e(TAG, createAnswer.toString());
                }
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
        return false;
    }

    private HttpPostEntity createClubPostEntity(String clubName) throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(genericUrl, clubJSON(clubName));
    }

    public boolean checkValidity(String clubName) {
        try {
            HttpResponse response = new ApiCallTask().execute(checkValidityPostEntity(clubName)).get();
            if (response != null) {
                validateAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 200) {
                    return true;
                } else {
                    Log.e(TAG, "Invalid entity");
                    Log.e(TAG, validateAnswer.toString());
                }
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
        return false;
    }

    private HttpPostEntity checkValidityPostEntity(String clubName) throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(genericUrl + "/validity", clubJSON(clubName));
    }

    public List<String> getUserByClub(String clubName){
        List<String> returnList = new ArrayList<String>();

        try {
            HttpResponse response = new ApiCallTask().execute(getUsersByClubPostEntity(clubName)).get();
            JSONObject jsonResponse = new JsonBuilder().execute(response).get();
            returnList = getUsersFromJsonResponse(jsonResponse);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return returnList;
    }

    public HttpPostEntity getUsersByClubPostEntity(String clubName) throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(genericUrl + "/get_members_by_club", clubJSON(clubName));
    }

    public boolean addFriendsToClub(List<String> phoneNumberList, String clubName) {
        try {
            HttpResponse response = new ApiCallTask().
                    execute(addFriendsToClubPostEntity(phoneNumberList, clubName)).get();
            if (response != null) {
                this.addFriendsAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 200) {
                    return true;
                } else {
                    Log.e(TAG, "Could not add friends to club");
                    Log.e(TAG, this.addFriendsAnswer.toString());
                }
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

        return false;
    }

    private HttpPostEntity addFriendsToClubPostEntity(List<String> phoneNumberList, String clubName) throws JSONException, UnsupportedEncodingException {
        JSONArray phoneNumberArray = getJSONArrayFromList(phoneNumberList);
        JSONObject leaf = new JSONObject();
        leaf.put("name", clubName);
        leaf.put("members", phoneNumberArray);
        JSONObject root = new JSONObject();
        root.put("club", leaf);

        String url = genericUrl + "/add_members";

        return new HttpPostEntity(url, root.toString());
    }

    private JSONArray getJSONArrayFromList(List<String> list) {
        JSONArray returnArray = new JSONArray();
        for (String string : list) {
            returnArray.put(string);
        }

        return returnArray;
    }

    private List<String> getUsersFromJsonResponse(JSONObject jsonResponse) throws JSONException {
        List<String> returnList = new ArrayList<String>();
        JSONArray usersArray = jsonResponse.getJSONArray("clubs");
        for (int i = 0; i < usersArray.length(); i++) {
            returnList.add((String) ((JSONObject) usersArray.get(i)).get("userName"));
        }

        return returnList;
    }


    //<editor-fold desc="Getter">
    public JSONObject getAddFriendsAnswer() {
        return addFriendsAnswer;
    }

    public JSONObject getValidateAnswer() {
        return validateAnswer;
    }

    public JSONObject getCreateAnswer() {
        return createAnswer;
    }
    //</editor-fold>
}
