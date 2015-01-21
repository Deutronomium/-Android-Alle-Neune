package patrickengelkes.com.alleneune.entities.objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;

/**
 * Created by patrickengelkes on 21/01/15.
 */
public class Friend {
    private String genericUrl = "/friends";

    public HttpPostEntity getRegisteredFriends(List<String> phoneNumbers) throws JSONException, UnsupportedEncodingException {
        JSONArray phoneNumberArray = getJSONArrayFromList(phoneNumbers);
        JSONObject leaf = new JSONObject();
        leaf.put("phone_numbers", phoneNumberArray);
        JSONObject root = new JSONObject();
        root.put("friends", leaf);

        String url = genericUrl + "/getRegisteredFriends";

        return new HttpPostEntity(url, root.toString());
    }

    public HttpPostEntity removeFriendFromClub(String clubName, String userName) throws JSONException, UnsupportedEncodingException {
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
}
