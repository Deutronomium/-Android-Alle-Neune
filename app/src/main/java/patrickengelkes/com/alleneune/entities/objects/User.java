package patrickengelkes.com.alleneune.entities.objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import patrickengelkes.com.alleneune.api_calls.HttpPostEntity;

/**
 * Created by patrickengelkes on 31/10/14.
 */
public class User implements Parcelable, AbstractValidityEntity {
    private static User mInstance = null;

    private String genericUrl = "/users";

    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String city;
    private String password;
    private String passwordConfirmation;
    private String phoneNumber;

    public User() {}

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

    public static User getInstance() {
        if (mInstance == null) {
            mInstance = new User();
        }

        return mInstance;
    }

    private String genericJSON() throws JSONException {
        JSONObject leaf = new JSONObject();
        leaf.put("userName", this.userName);
        leaf.put("email", this.email);
        leaf.put("password", this.password);
        leaf.put("password_confirmation", this.passwordConfirmation);
        if (!this.phoneNumber.isEmpty()) {
            leaf.put("phone_number", this.phoneNumber);
        }
        JSONObject root = new JSONObject();
        root.put("user", leaf);

        return root.toString();
    }

    @Override
    public HttpPostEntity create() throws JSONException, UnsupportedEncodingException {
        return new HttpPostEntity(this.genericUrl, genericJSON());
    }

    @Override
    public HttpPostEntity checkValidity() throws JSONException, UnsupportedEncodingException {
        String url = this.genericUrl + "/validity";

        return new HttpPostEntity(url, genericJSON());
    }

    public HttpPostEntity getUserClub() throws JSONException, UnsupportedEncodingException {
        JSONObject leaf = new JSONObject();
        leaf.put("userName", this.userName);
        JSONObject root = new JSONObject();
        root.put("user", leaf);

        String url = genericUrl + "/user_club";

        return new HttpPostEntity(url, root.toString());
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
    //</editor-fold>

    //<editor-fold desc="Parcelable">
    protected User(Parcel in) {
        userName = in.readString();
        email = in.readString();
        password = in.readString();
        passwordConfirmation = in.readString();
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
    //</editor-fold>

}
