package patrickengelkes.com.alleneune.enums;

/**
 * Created by patrickengelkes on 02/02/15.
 */
public enum UserValidation {
    SUCCESS("No validation errors!"),
    USER_AND_EMAIL("Username and Email address are already in use.!"),
    USER("Username is already in use!"),
    EMAIL("Email is already in use!"),
    ERROR("Something went wrong on our end. We are sorry for this!");

    private String displayText;

    private UserValidation(String displayText) {
        this.displayText = displayText;
    }

    public String getDisplayText() {
        return this.displayText;
    }
}
