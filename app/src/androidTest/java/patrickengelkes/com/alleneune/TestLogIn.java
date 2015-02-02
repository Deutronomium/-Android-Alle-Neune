package patrickengelkes.com.alleneune;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;

import patrickengelkes.com.alleneune.activities.ClubHomeActivity;
import patrickengelkes.com.alleneune.activities.MainActivity;
import patrickengelkes.com.alleneune.activities.UserHomeActivity;

/**
 * Created by patrickengelkes on 30/01/15.
 */
public class TestLogIn extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public TestLogIn() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testLogInUserWithClub() {
        enterLogInData("Test", "test123");

        assertTrue(solo.waitForActivity(ClubHomeActivity.class));
    }

    public void testLogInUserWithoutClub() {
        enterLogInData("Test", "test123");

        assertTrue(solo.waitForActivity(UserHomeActivity.class));
    }

    public void testLogInWithInvalidData() {
        enterLogInData("WrongUserName", "Wrong Password");

        assertTrue(solo.waitForDialogToOpen());
        assertTrue(solo.searchText(getActivity().getString(R.string.wrong_user_credentials_warning)));

        solo.clickOnView(solo.getView(android.R.id.button1));
    }

    private void enterLogInData(String email, String password) {
        EditText emailEditText = (EditText) solo.getView(R.id.sign_up_name_edit_text);
        EditText passwordEditText = (EditText) solo.getView(R.id.sign_up_password_edit_text);
        Button logInButton = (Button) solo.getView(R.id.logInButton);

        solo.enterText(emailEditText, email);
        solo.enterText(passwordEditText, password);

        solo.clickOnView(logInButton);
    }
}
