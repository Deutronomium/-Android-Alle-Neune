package patrickengelkes.com.alleneune;

/**
 * Created by patrickengelkes on 28/10/14.
 */
public class SessionParams {
    private String email;
    private String password;

    public SessionParams(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
