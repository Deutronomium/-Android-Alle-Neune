package patrickengelkes.com.alleneune;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;

import java.util.Random;

import patrickengelkes.com.alleneune.activities.PhoneNumberActivity;
import patrickengelkes.com.alleneune.activities.UserHomeActivity;
import patrickengelkes.com.alleneune.entities.objects.User;

/**
 * Created by patrickengelkes on 03/02/15.
 */
public class TestPhoneNumberActivity extends ActivityInstrumentationTestCase2<PhoneNumberActivity>{
    private Solo solo;

    public TestPhoneNumberActivity() {
        super(PhoneNumberActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        NameGenerator nameGenerator = new NameGenerator();
        String userName = nameGenerator.getName();
        String email = nameGenerator.getName();
        User user = new User(userName, email, "test", "test");
        Intent intent = new Intent();
        intent.putExtra("user", user);
        setActivityIntent(intent);
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testAddingUserWithValidPhoneNumber() {
        EditText phoneNumberEditText = (EditText) solo.getView(R.id.phone_number_edit_text);
        Button signUpButton = (Button) solo.getView(R.id.sign_up_button);
        solo.enterText(phoneNumberEditText, String.valueOf(new Random().nextInt()));

        solo.clickOnView(signUpButton);

        assertTrue(solo.waitForActivity(UserHomeActivity.class));
    }

    public void testAddingUserWithInvalidPhoneNumber() {
        EditText phoneNumberEditText = (EditText) solo.getView(R.id.phone_number_edit_text);
        Button signUpButton = (Button) solo.getView(R.id.sign_up_button);
        solo.enterText(phoneNumberEditText, "11111");

        solo.clickOnView(signUpButton);

        assertTrue(solo.waitForDialogToOpen());
        assertTrue(solo.searchText(getActivity().getString(R.string.phone_number_exists_warning)));
    }
}
