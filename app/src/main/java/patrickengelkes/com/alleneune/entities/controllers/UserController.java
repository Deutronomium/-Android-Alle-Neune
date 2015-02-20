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
import patrickengelkes.com.alleneune.CurrentUser;
import patrickengelkes.com.alleneune.api_calls.ApiCallTask;
import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;
import patrickengelkes.com.alleneune.api_calls.JsonBuilder;
import patrickengelkes.com.alleneune.entities.objects.User;
import patrickengelkes.com.alleneune.enums.ApiCall;
import patrickengelkes.com.alleneune.enums.UserClub;
import patrickengelkes.com.alleneune.enums.UserValidation;

/**
 * Created by patrickengelkes on 05/12/14.
 */
public class UserController {
    public static final String TAG = UserController.class.getSimpleName();
    @Inject
    CurrentClub currentClub;
    @Inject
    CurrentUser currentUser;

    @Inject
    public UserController() {
    }

    private String userJSON(String userName, String email, String password,
                            String passwordConfirmation, String phoneNumber) throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put(User.USER_NAME, userName);
        leaf.put(User.EMAIL, email);
        leaf.put(User.PASSWORD, password);
        leaf.put(User.PASSWORD_CONFIRMATION, passwordConfirmation);
        if (!phoneNumber.isEmpty()) {
            leaf.put(User.PHONE_NUMBER, phoneNumber);
        }
        JSONObject root = new JSONObject();
        root.put(User.ROOT, leaf);

        return root.toString();
    }

    public ApiCall createUser(String userName, String email, String password, String passwordConfirmation,
                              String phoneNumber) {
        try {
            HttpResponse response = new ApiCallTask().execute(create(userName, email, password,
                    passwordConfirmation, phoneNumber)).get();
            currentUser.setUserName(userName);
            currentUser.setEmail(email);
            if (response != null) {
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
        return new HttpPostEntity(User.GENERIC_URL, userJSON(userName, email, password,
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

        return new HttpPostEntity(User.VALIDITY, userJSON(userName, email, password, passwordConfirmation, phoneNumber));
    }

    public UserClub getClubByUserName(String userName) throws JSONException {
        try {
            HttpResponse response = new ApiCallTask().execute(getUserClubByNamePostEntity(userName)).get();
            JSONObject jsonResponse = new JsonBuilder().execute(response).get();
            if (jsonResponse != null) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    Log.e(TAG, "User already joined a club.");
                    JSONObject jsonClub = (JSONObject) jsonResponse.get("club");
                    currentClub.setClubName(jsonClub.getString("name"));
                    currentClub.setClubID(Integer.valueOf(jsonClub.getString("id")));
                    return UserClub.USER_HAS_CLUB;
                } else if (response.getStatusLine().getStatusCode() == 450) {
                    Log.e(TAG, "User did not join a club yet.");
                    return UserClub.USER_HAS_NO_CLUB;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return UserClub.USER_HAS_NO_CLUB;
    }

    public HttpPostEntity getUserClubByNamePostEntity(String userName) throws JSONException, UnsupportedEncodingException {
        JSONObject leaf = new JSONObject();
        leaf.put(User.USER_NAME, userName);
        JSONObject root = new JSONObject();
        root.put(User.ROOT, leaf);

        return new HttpPostEntity(User.GET_USER_CLUB_BY_NAME, root.toString());
    }

    public List<User> getUserListFromJSONResponse(JSONArray friends) {
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < friends.length(); i++) {
            try {
                JSONObject friend = (JSONObject)friends.get(i);
                String userName = friend.getString(User.USER_NAME);
                String firstName = friend.getString(User.FIRST_NAME);
                String lastName = friend.getString(User.LAST_NAME);
                String phoneNumber = friend.getString(User.PHONE_NUMBER);

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
}
