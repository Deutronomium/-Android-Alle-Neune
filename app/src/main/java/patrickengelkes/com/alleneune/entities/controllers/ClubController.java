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

import patrickengelkes.com.alleneune.CurrentClub;
import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.Club;
import patrickengelkes.com.alleneune.entities.objects.User;
import patrickengelkes.com.alleneune.enums.ApiCall;

/**
 * Created by patrickengelkes on 12/12/14.
 */
public class ClubController {
    public static final String TAG = ClubController.class.getSimpleName();
    @Inject
    CurrentClub currentClub;

    @Inject
    public ClubController() {

    }

    private String clubJSON(String clubName) throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put(Club.NAME, clubName);
        JSONObject root = new JSONObject();
        root.put(Club.ROOT, leaf);

        return root.toString();
    }

    public ApiCall createClub(String clubName) {
        try {
            HttpResponse response = new ApiCallTask().execute(createClubPostEntity(clubName)).get();
            if (response != null) {
                JSONObject createAnswer = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 201 && createAnswer != null) {
                    JSONObject clubJson = (JSONObject) createAnswer.get(Club.ROOT);
                    currentClub.setId((Integer) clubJson.get(Club.ID));
                    currentClub.setName((String) clubJson.get(Club.NAME));
                    return ApiCall.CREATED;
                } else if (response.getStatusLine().getStatusCode() == 422 && createAnswer != null) {
                    Log.e(TAG, "Creating entity failed");
                    Log.e(TAG, createAnswer.toString());
                    return ApiCall.UNPROCESSABLE_ENTITY;
                } else {
                    return ApiCall.BAD_REQUEST;
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
        return ApiCall.BAD_REQUEST;
    }

    private HttpPostEntity createClubPostEntity(String clubName) throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(Club.GENERIC_URL, clubJSON(clubName));
    }

    public boolean checkValidity(String clubName) {
        try {
            HttpResponse response = new ApiCallTask().execute(checkValidityPostEntity(clubName)).get();
            if (response != null) {
                JSONObject validateAnswer = new JsonBuilder().execute(response).get();
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
        return new HttpPostEntity(Club.VALIDITY, clubJSON(clubName));
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
        return new HttpPostEntity(Club.GET_MEMBERS_BY_CLUB, clubJSON(clubName));
    }

    public ApiCall addFriendsToClub(List<String> phoneNumberList, int clubID) {
        try {
            HttpResponse response = new ApiCallTask().
                    execute(addFriendsToClubPostEntity(phoneNumberList, clubID)).get();
            if (response != null) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    return ApiCall.SUCCESS;
                } else if (response.getStatusLine().getStatusCode() == 422) {
                    return ApiCall.UNPROCESSABLE_ENTITY;
                } else {
                    return ApiCall.BAD_REQUEST;
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

        return ApiCall.BAD_REQUEST;
    }

    private HttpPostEntity addFriendsToClubPostEntity(List<String> phoneNumberList, int clubID) throws JSONException, UnsupportedEncodingException {
        JSONArray phoneNumberArray = getJSONArrayFromList(phoneNumberList);
        JSONObject leaf = new JSONObject();
        leaf.put(Club.ID, clubID);
        leaf.put(Club.MEMBERS, phoneNumberArray);
        JSONObject root = new JSONObject();
        root.put(Club.ROOT, leaf);

        return new HttpPostEntity(Club.ADD_MEMBERS, root.toString());
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
        JSONArray usersArray = jsonResponse.getJSONArray(Club.ROOT + "s");
        for (int i = 0; i < usersArray.length(); i++) {
            returnList.add((String) ((JSONObject) usersArray.get(i)).get(User.USER_NAME));
        }

        return returnList;
    }
}
