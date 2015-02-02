package patrickengelkes.com.alleneune.entities.controllers;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
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

    private User user;

    public UserController(User user) {
        this.user = user;
    }

    public ApiCall createUser() {
        try {
            HttpResponse response = new ApiCallTask().execute(this.user.create()).get();
            if (response != null) {
                JSONObject jsonResponse = new JsonBuilder().execute(response).get();
                if (response.getStatusLine().getStatusCode() == 201) {
                    User.setUserSingleton(jsonResponse);
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

    public UserValidation checkValidity() {
        try {
            HttpResponse response = new ApiCallTask().execute(this.user.checkValidity()).get();
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

    public Club getClubByUser() throws JSONException {
        try {
            HttpResponse response = new ApiCallTask().execute(user.getUserClub()).get();
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
}
