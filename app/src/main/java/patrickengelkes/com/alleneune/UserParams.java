package patrickengelkes.com.alleneune;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by patrickengelkes on 29/10/14.
 */
public class UserParams {

    private String userName;
    private String password;
    private String passwordConfirmation;
    private String email;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private int clubID;

    public UserParams(String userName, String password, String passwordConfirmation, String email,
                      String firstName, String lastName, String street, String city,
                      int clubID) {
        this.userName = userName;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.clubID = clubID;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getJsonString() throws JSONException {
        JSONObject userParameters = new JSONObject();
        userParameters.put("userName", this.userName);
        userParameters.put("password", this.password);
        userParameters.put("password_confirmation", this.passwordConfirmation);
        userParameters.put("email", this.email);
        userParameters.put("firstName", this.firstName);
        userParameters.put("lastName", this.lastName);
        userParameters.put("street", this.street);
        userParameters.put("city", this.city);
        userParameters.put("club_id", this.clubID);

        JSONObject userRoot = new JSONObject();
        userRoot.put("user", userParameters);
        return userRoot.toString();
    }
}
