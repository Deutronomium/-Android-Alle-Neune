package patrickengelkes.com.alleneune.entities.controllers;

import android.util.Log;

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
import patrickengelkes.com.alleneune.entities.objects.Club;
import patrickengelkes.com.alleneune.entities.objects.User;
import patrickengelkes.com.alleneune.enums.ApiCall;
import patrickengelkes.com.alleneune.enums.UserValidation;

/**
 * Created by patrickengelkes on 05/12/14.
 */
public class UserController {
    public static final String TAG = UserController.class.getSimpleName();

    private String genericUrl = "/users";

    @Inject
    public UserController() {
    }

    private String genericJSON(String userName, String email, String password,
                               String passwordConfirmation, String phoneNumber) throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put("userName", userName);
        leaf.put("email", email);
        leaf.put("password", password);
        leaf.put("password_confirmation", passwordConfirmation);
        if (!phoneNumber.isEmpty()) {
            leaf.put("phone_number", phoneNumber);
        }
        JSONObject root = new JSONObject();
        root.put("user", leaf);

        return root.toString();
    }

    public ApiCall createUser(String userName, String email, String password, String passwordConfirmation,
                              String phoneNumber) {
        try {
            HttpResponse response = new ApiCallTask().execute(create(userName, email, password,
                    passwordConfirmation, phoneNumber)).get();
            if (response != null) {
                JSONObject jsonResponse = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 201) {
                    return ApiCall.CREATED;
                } else {
                    return ApiCall.UNPROCESSABLE_ENTITY;
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



    public HttpPostEntity create(String userName, String email, String password,
                                 String passwordConfirmation, String phoneNumber)
            throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(this.genericUrl, genericJSON(userName, email, password,
                passwordConfirmation, phoneNumber));
    }

    public UserValidation checkValidity(String userName, String email, String password, String passwordConfirmation,
                                        String phoneNumber) {
        try {
            HttpResponse response = new ApiCallTask().execute(getValidityPostEntity(userName, email,
                    password, passwordConfirmation, phoneNumber)).get();
            if (response != null) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    return UserValidation.SUCCESS;
                } else if (response.getStatusLine().getStatusCode() == 450) {
                    return UserValidation.USER_AND_EMAIL;
                } else if (response.getStatusLine().getStatusCode() == 451) {
                    return UserValidation.USER;
                } else if (response.getStatusLine().getStatusCode() == 452) {
                    return UserValidation.EMAIL;
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

        return UserValidation.ERROR;
    }

    public HttpPostEntity getValidityPostEntity(String userName, String email, String password, String passwordConfirmation,
                                        String phoneNumber)
            throws JSONException, UnsupportedEncodingException {
        String url = this.genericUrl + "/validity";

        return new HttpPostEntity(url, genericJSON(userName, email, password, passwordConfirmation, phoneNumber));
    }

    public Club getClubByUserName(String userName) throws JSONException {
        try {
            HttpResponse response = new ApiCallTask().execute(getUserClubByNamePostEntity(userName)).get();
            JSONObject jsonResponse = new JsonBuilder().execute(response).get();
            if (jsonResponse != null) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    Log.e(TAG, "User already joined a club.");
                    JSONObject jsonClub = (JSONObject) jsonResponse.get("club");
                    return new Club(jsonClub.getString("name"), Integer.valueOf(jsonClub.getString("id")));
                } else if (response.getStatusLine().getStatusCode() == 450) {
                    Log.e(TAG, "User did not join a club yet.");
                    return null;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public HttpPostEntity getUserClubByNamePostEntity(String userName) throws JSONException, UnsupportedEncodingException {
        JSONObject leaf = new JSONObject();
        leaf.put("userName", userName);
        JSONObject root = new JSONObject();
        root.put("user", leaf);

        String url = genericUrl + "/user_club";

        return new HttpPostEntity(url, root.toString());
    }
}
