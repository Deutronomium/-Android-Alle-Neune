package patrickengelkes.com.alleneune.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by patrickengelkes on 31/10/14.
 */
public class User implements AbstractEntity, Parcelable {

    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String city;
    private String password;
    private String passwordConfirmation;
    private String phoneNumber;


    private HashMap<String, Object> objectParameters;

    private String objectString;

    public User(String userName, String email, String password, String passwordConfirmation) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.phoneNumber = "";
    }

    /*
    Always call before working with the API
     */
    @Override
    public void prepareEntity() {
        this.objectParameters = getObjectParameters();

        this.objectString = "user";
    }

    @Override
    public HashMap<String, Object> getObjectParameters() {
        HashMap<String, Object> objectParameters = new HashMap<String, Object>();
        objectParameters.put("userName", this.userName);
        objectParameters.put("email", this.email);
        objectParameters.put("password", this.password);
        objectParameters.put("password_confirmation", this.passwordConfirmation);
        if (!this.phoneNumber.isEmpty()) {
            objectParameters.put("phone_number", this.phoneNumber);
        }
        return objectParameters;
    }

    @Override
    public String getJsonString() throws JSONException {
        JSONObject parameters = new JSONObject();
        for (Map.Entry<String, Object> cursor : this.objectParameters.entrySet()) {
            parameters.put(cursor.getKey(), cursor.getValue());
        }
        JSONObject root = new JSONObject();
        root.put(this.objectString, parameters);
        return root.toString();
    }

    @Override
    public String getObjectString() {
        return this.objectString;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }




    //Parcelable
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

    public User(String userName, String firstName, String lastName, String email, String city) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
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

    public String getUserName() {
        return this.userName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

}
