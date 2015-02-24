package patrickengelkes.com.alleneune.entities.objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import patrickengelkes.com.alleneune.CurrentUser;

/**
 * Created by patrickengelkes on 31/10/14.
 */
public class User implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    //attribute strings for api calls
    public static String ROOT = "user";
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
    //urls
    public static String GENERIC_URL = "/users";
    public static String VALIDITY = GENERIC_URL + "/validity";
    public static String GET_USER_CLUB_BY_NAME = GENERIC_URL + "/user_club";
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

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String email, String password, String passwordConfirmation) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.phoneNumber = "";
    }

    public User(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt(ID);
        this.userName = (jsonObject.isNull(USER_NAME)) ? null : jsonObject.getString(USER_NAME);
        this.firstName = (jsonObject.isNull(FIRST_NAME)) ? null : jsonObject.getString(FIRST_NAME);
        this.lastName = (jsonObject.isNull(LAST_NAME)) ? null : jsonObject.getString(LAST_NAME);
        this.phoneNumber = (jsonObject.isNull(PHONE_NUMBER)) ? null : jsonObject.getString(PHONE_NUMBER);
        this.email = (jsonObject.isNull(EMAIL)) ? null : jsonObject.getString(EMAIL);
        this.city = (jsonObject.isNull(CITY)) ? null : jsonObject.getString(CITY);
        this.street = (jsonObject.isNull(STREET)) ? null : jsonObject.getString(STREET);
    }

    public User(CurrentUser currentUser) {
        this.userName = currentUser.getUserName();
        this.email = currentUser.getEmail();
        this.phoneNumber = currentUser.getPhoneNumber();
    }

    //<editor-fold desc="Parcelable">
    protected User(Parcel in) {
        userName = in.readString();
        email = in.readString();
        password = in.readString();
        passwordConfirmation = in.readString();
    }

    @Override
    public String toString() {
        if (this.firstName != null && this.lastName != null) {
            return this.firstName + " " + this.lastName;
        } else if (this.firstName != null) {
            return this.firstName;
        } else {
            return this.userName;
        }
    }

    //<editor-fold desc="Getter & Setter">
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //</editor-fold>

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(passwordConfirmation);
    }
    //</editor-fold>

}
