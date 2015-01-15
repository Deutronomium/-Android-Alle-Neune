package patrickengelkes.com.alleneune.entities.controllers;

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
import patrickengelkes.com.alleneune.entities.objects.Club;

/**
 * Created by patrickengelkes on 12/12/14.
 */
public class ClubController {

    public static final String TAG = ClubController.class.getSimpleName();

    private Club club;

    public ClubController(Club club) {
        this.club = club;
    }

    public List<String> getUsersByClub(){
        List<String> returnList = new ArrayList<String>();

        try {
            HttpResponse response = new ApiCallTask().execute(club.getUsersByClub()).get();
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

    private List<String> getUsersFromJsonResponse(JSONObject jsonResponse) throws JSONException {
        List<String> returnList = new ArrayList<String>();
        JSONArray usersArray = jsonResponse.getJSONArray("clubs");
        for (int i = 0; i < usersArray.length(); i++) {
            returnList.add((String) ((JSONObject) usersArray.get(i)).get("userName"));
        }

        return returnList;
    }
}
