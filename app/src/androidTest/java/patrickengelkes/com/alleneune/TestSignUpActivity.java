package patrickengelkes.com.alleneune;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;

import patrickengelkes.com.alleneune.activities.MainActivity;
import patrickengelkes.com.alleneune.activities.PhoneNumberActivity;
import patrickengelkes.com.alleneune.activities.SignUpActivity;

/**
 * Created by patrickengelkes on 30/01/15.
 */
public class TestSignUpActivity extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public TestSignUpActivity() {
        super(MainActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testValidSignUp() {
        NameGenerator nameGenerator = new NameGenerator();
        String name = nameGenerator.getName();
        String email = nameGenerator.getName();

        signUp(name, email);

        assertTrue(solo.waitForActivity(PhoneNumberActivity.class));
    }

    public void testSignUpWithUsedUserNameAndEmail() {
        signUp("Test1", "test1");

        errors(getActivity().getString(R.string.user_and_email_used_warning));
    }

    public void testSignUpWithUsedUserName() {
        String email = new NameGenerator().getName();

        signUp("Test1", email);

        errors(getActivity().getString(R.string.user_used_warning));
    }

    public void testSignUpWithUsedEmail() {
        String userName = new NameGenerator().getName();

        signUp(userName, "test1");

        errors(getActivity().getString(R.string.email_used_warning));
    }


    private void errors(String errorMessage) {
        assertTrue(solo.waitForDialogToOpen());
        assertTrue(solo.searchText(errorMessage));

        solo.clickOnView(solo.getView(android.R.id.button1));
    }


    private void signUp(String name, String email) {
        //go to sign up page
        Button signUpButton = (Button) solo.getView(R.id.signUpButton);
        solo.clickOnView(signUpButton);
        assertTrue(solo.waitForActivity(SignUpActivity.class));


        //enter signUp info
        EditText userNameEditText = (EditText) solo.getView(R.id.sign_up_name_edit_text);
        EditText passwordEditText = (EditText) solo.getView(R.id.sign_up_password_edit_text);
        EditText passwordConfirmationEditText = (EditText) solo.getView(R.id.password_confirmation_edit_text);
        EditText emailEditText = (EditText) solo.getView(R.id.email_edit_text);
        Button continueButton = (Button) solo.getView(R.id.sign_up_button);

        solo.enterText(userNameEditText, name);
        solo.enterText(passwordEditText, "test");
        solo.enterText(passwordConfirmationEditText, "test");
        solo.enterText(emailEditText, email);

        solo.clickOnView(continueButton);
    }
}
