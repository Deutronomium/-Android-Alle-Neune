package patrickengelkes.com.alleneune.entities.controllers;

import com.google.inject.Inject;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.CurrentUser;
import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.Session;
import patrickengelkes.com.alleneune.entities.objects.User;
import patrickengelkes.com.alleneune.enums.ApiCall;

/**
 * Created by patrickengelkes on 24/01/15.
 */
public class SessionController {
    public static final String TAG = SessionController.class.getSimpleName();

    @Inject
    CurrentUser currentUser;

    @Inject
    public SessionController() {
    }

    public ApiCall logIn(String userName, String password) {
        try {
            HttpResponse response = new ApiCallTask().execute(logInPostEntity(userName, password)).get();
            if (response != null) {
                JSONObject jsonResponse = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 200) {
                    //User.setCurrentUser(jsonResponse);
                    setCurrentUser(jsonResponse);
                    return ApiCall.SUCCESS;
                } else {
                    return ApiCall.ACCESS_DENIED;
                }
            } else {
                return ApiCall.BAD_REQUEST;
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

    private String genericJSON(String email, String password) throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put(Session.EMAIL, email);
        leaf.put(Session.PASSWORD, password);
        JSONObject root = new JSONObject();
        root.put(Session.ROOT, leaf);

        return root.toString();
    }

    public HttpPostEntity logInPostEntity(String email, String password) throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(Session.GENERIC_URL, genericJSON(email, password));
    }

    public void setCurrentUser(JSONObject response) throws JSONException {
        JSONObject userJson = (JSONObject) response.get(User.ROOT);

        String userName = (String) userJson.get(User.USER_NAME);
        String firstName = (!userJson.isNull(User.FIRST_NAME)) ? (String) userJson.get(User.FIRST_NAME) : null;
        String lastName = (!userJson.isNull(User.LAST_NAME)) ? (String) userJson.get(User.LAST_NAME) : null;
        String phoneNumber = (String) userJson.get(User.PHONE_NUMBER);
        String email = (String) userJson.get(User.EMAIL);


        currentUser.setUserName(userName);
        if (firstName != null) {
            currentUser.setFirstName(firstName);
        }
        if (lastName != null) {
            currentUser.setLastName(lastName);
        }
        currentUser.setPhoneNumber(phoneNumber);
        currentUser.setEmail(email);
    }

}
