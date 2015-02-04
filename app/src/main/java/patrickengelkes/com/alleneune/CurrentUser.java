package patrickengelkes.com.alleneune;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by patrickengelkes on 04/02/15.
 */
@Singleton
public class CurrentUser {

    private String userName;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    @Inject
    public CurrentUser() {}

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
