import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.activities.PhoneNumberActivity;
import patrickengelkes.com.alleneune.activities.SignUpActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by patrickengelkes on 06/02/15.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class SignUpActivityTest {
    SignUpActivity signUpActivity;

    @Before
    public void setup() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        signUpActivity = Robolectric.buildActivity(SignUpActivity.class).create().get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(signUpActivity);
    }

    @Test
    public void checkComponentsNotNull() throws Exception {
        Button continueButton = (Button) signUpActivity.findViewById(R.id.sign_up_button);
        EditText userNameEditText = (EditText) signUpActivity.findViewById(R.id.sign_up_name_edit_text);
        EditText passwordEditText = (EditText) signUpActivity.findViewById(R.id.sign_up_password_edit_text);
        EditText confirmationPasswordEditText = (EditText)
                signUpActivity.findViewById(R.id.password_confirmation_edit_text);
        EditText emailEditText = (EditText) signUpActivity.findViewById(R.id.sign_up_email_edit_text);

        assertNotNull(continueButton);
        assertNotNull(userNameEditText);
        assertNotNull(passwordEditText);
        assertNotNull(confirmationPasswordEditText);
        assertNotNull(emailEditText);
    }

    @Test
    public void signUpSuccessful() {
        String name = TestHelper.getRandomName();

        signUpProcess(name, name);

        Intent intent = Robolectric.shadowOf(signUpActivity).peekNextStartedActivity();
        assertEquals(PhoneNumberActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void signUpWithUsedUserNameAndEmail() {
        signUpProcess("Deutro", "test");

        TestHelper.checkAlertDialog(signUpActivity.getString(R.string.user_and_email_used_warning));
    }

    @Test
    public void signUpWithUsedUserName() {
        String email = TestHelper.getRandomName();

        signUpProcess("Deutro", email);
        TestHelper.checkAlertDialog(signUpActivity.getString(R.string.user_used_warning));

    }

    @Test
    public void signUpWithUsedEmail() {
        String userName = TestHelper.getRandomName();

        signUpProcess(userName, "test");
        TestHelper.checkAlertDialog(signUpActivity.getString(R.string.email_used_warning));
    }

    private void signUpProcess(String userName, String email) {
        Button continueButton = (Button) signUpActivity.findViewById(R.id.sign_up_button);
        EditText userNameEditText = (EditText) signUpActivity.findViewById(R.id.sign_up_name_edit_text);
        EditText passwordEditText = (EditText) signUpActivity.findViewById(R.id.sign_up_password_edit_text);
        EditText confirmationPasswordEditText = (EditText)
                signUpActivity.findViewById(R.id.password_confirmation_edit_text);
        EditText emailEditText = (EditText) signUpActivity.findViewById(R.id.sign_up_email_edit_text);


        userNameEditText.setText(userName);
        passwordEditText.setText("test");
        confirmationPasswordEditText.setText("test");
        emailEditText.setText(email);

        continueButton.performClick();
    }
}
