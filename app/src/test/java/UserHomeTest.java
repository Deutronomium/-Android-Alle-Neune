import android.content.Intent;
import android.view.MenuItem;
import android.widget.Button;

import com.google.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.tester.android.view.TestMenuItem;

import patrickengelkes.com.alleneune.CurrentUser;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.activities.ClubHomeActivity;
import patrickengelkes.com.alleneune.activities.CreateClubActivity;
import patrickengelkes.com.alleneune.activities.MainActivity;
import patrickengelkes.com.alleneune.activities.UserHomeActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by patrickengelkes on 06/02/15.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class UserHomeTest {
    @Mock
    CurrentUser currentUser;

    private UserHomeActivity userHomeActivity;


    private void setup(String userName) {
        MockitoAnnotations.initMocks(this);
        when(currentUser.getUserName()).thenReturn(userName);

        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);

        TestGuiceModule module = new TestGuiceModule();
        module.addBinding(CurrentUser.class, currentUser);
        TestGuiceModule.setUp(this, module);
        userHomeActivity = Robolectric.buildActivity(UserHomeActivity.class).create().get();
    }



    @Test
    public void checkActivityNotNull() throws Exception {
        setup("Deutro");
        assertNotNull(userHomeActivity);
    }

    @Test
    public void signUpUserWithoutClub() throws Exception {
        setup("Test5");

        Button createClubButton = (Button) userHomeActivity.findViewById(R.id.create_club_button);
        Button joinClubButton = (Button) userHomeActivity.findViewById(R.id.join_club_button);

        assertNotNull(createClubButton);
        assertNotNull(joinClubButton);
    }

    @Test
    public void signUpUserWithClub() throws Exception {
        setup("Deutro");

        Intent intent = Robolectric.shadowOf(userHomeActivity).peekNextStartedActivity();
        assertEquals(ClubHomeActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void createClubButton() throws Exception {
        setup("Test5");

        Button createClubButton = (Button) userHomeActivity.findViewById(R.id.create_club_button);
        createClubButton.performClick();

        Intent intent = Robolectric.shadowOf(userHomeActivity).peekNextStartedActivity();
        assertEquals(CreateClubActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void logOut() throws Exception {
        setup("Test5");

        MenuItem logoutItem = new TestMenuItem(R.id.logout_action);

        userHomeActivity.onOptionsItemSelected(logoutItem);
        Intent intent = Robolectric.shadowOf(userHomeActivity).peekNextStartedActivity();
        assertEquals(MainActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }
}
