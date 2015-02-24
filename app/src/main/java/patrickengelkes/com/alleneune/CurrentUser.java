package patrickengelkes.com.alleneune;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by patrickengelkes on 04/02/15.
 */
@Singleton
public class CurrentUser {
    //attribute strings for api calls
    public static String ID = "id";
    public static String USER_NAME = "user_name";
    public static String FIRST_NAME = "first_name";
    public static String LAST_NAME = "last_name";
    public static String PHONE_NUMBER = "phone_number";
    public static String EMAIL = "email";
    public static String STREET = "street";
    public static String CITY = "city";
    public static String PASSWORD = "password";
    public static String PASSWORD_CONFIRMATION = "password_confirmation";
    //attributes
    private int id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String city;
    private String street;
    private String password;
    private String passwordConfirmation;
    private String phoneNumber;

    @Inject
    public CurrentUser() {}

    public void set(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt(ID);
        this.userName = (jsonObject.isNull(USER_NAME)) ? null : jsonObject.getString(USER_NAME);
        this.firstName = (jsonObject.isNull(FIRST_NAME)) ? null : jsonObject.getString(FIRST_NAME);
        this.lastName = (jsonObject.isNull(LAST_NAME)) ? null : jsonObject.getString(LAST_NAME);
        this.phoneNumber = (jsonObject.isNull(PHONE_NUMBER)) ? null : jsonObject.getString(PHONE_NUMBER);
        this.email = (jsonObject.isNull(EMAIL)) ? null : jsonObject.getString(EMAIL);
        this.city = (jsonObject.isNull(CITY)) ? null : jsonObject.getString(CITY);
        this.street = (jsonObject.isNull(STREET)) ? null : jsonObject.getString(STREET);
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
