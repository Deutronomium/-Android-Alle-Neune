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
import patrickengelkes.com.alleneune.activities.MainActivity;
import patrickengelkes.com.alleneune.activities.UserHomeActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Robolectric.shadowOf;


/**
 * Created by patrickengelkes on 06/02/15.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    private MainActivity mainActivity;

    @Before
    public void setup() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(mainActivity);
    }

    @Test
    public void logInWithValidData() throws Exception {
        enterData("Test", "test123");

        Intent intent = shadowOf(mainActivity).peekNextStartedActivity();
        assertEquals(UserHomeActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void logInWithInvalidUserNameAndEmail() throws Exception {
        enterData("asd", "test");

        TestHelper.checkAlertDialog(mainActivity.getString(R.string.wrong_user_credentials_warning));
    }


    private void enterData(String email, String password) {
        Button logInButton = (Button) mainActivity.findViewById(R.id.log_in_button);
        EditText mailEditText = (EditText) mainActivity.findViewById(R.id.sign_up_name_edit_text);
        EditText passwordEditText = (EditText) mainActivity.findViewById(R.id.sign_up_password_edit_text);
        mailEditText.setText(email);
        passwordEditText.setText(password);
        logInButton.performClick();
    }
}
