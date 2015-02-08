import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Random;

import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.activities.PhoneNumberActivity;
import patrickengelkes.com.alleneune.activities.UserHomeActivity;
import patrickengelkes.com.alleneune.entities.objects.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by patrickengelkes on 08/02/15.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class PhoneNumberActivityTest {
    PhoneNumberActivity phoneNumberActivity;

    @Before
    public void setup() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        String nameAndEmail = TestHelper.getRandomName();
        User user = new User(nameAndEmail, nameAndEmail, "test", "test");
        Intent userIntent = new Intent();
        userIntent.putExtra("user", user);
        phoneNumberActivity = Robolectric.buildActivity(PhoneNumberActivity.class).withIntent(userIntent)
                .create().get();

    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(phoneNumberActivity);
    }

    @Test
    public void checkComponentsNotNull() {
        EditText phoneNumberEditText = (EditText) phoneNumberActivity.findViewById(R.id.phone_number_edit_text);
        Button signUpButton = (Button) phoneNumberActivity.findViewById(R.id.sign_up_button);

        assertNotNull(phoneNumberEditText);
        assertNotNull(signUpButton);
        assertNotNull(phoneNumberActivity.getIntent().getParcelableExtra("user"));
    }

    @Test
    public void signUpWithValidData() {
        setSignUpDate(String.valueOf(new Random().nextInt()));

        Intent intent = Robolectric.shadowOf(phoneNumberActivity).peekNextStartedActivity();
        assertEquals(UserHomeActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void signUpWithInvalidData() {
        setSignUpDate("11111");

        TestHelper.checkAlertDialog(phoneNumberActivity.getString(R.string.phone_number_exists_warning));
    }

    private void setSignUpDate(String phoneNumber) {
        EditText phoneNumberEditText = (EditText) phoneNumberActivity.findViewById(R.id.phone_number_edit_text);
        Button signUpButton = (Button) phoneNumberActivity.findViewById(R.id.sign_up_button);

        phoneNumberEditText.setText(phoneNumber);
        signUpButton.performClick();
    }
}
