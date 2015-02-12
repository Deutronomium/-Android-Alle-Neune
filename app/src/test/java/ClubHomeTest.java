import android.content.Intent;
import android.view.MenuItem;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.tester.android.view.TestMenuItem;

import patrickengelkes.com.alleneune.CurrentClub;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.activities.ClubHomeActivity;
import patrickengelkes.com.alleneune.activities.EditFriendsActivity;
import patrickengelkes.com.alleneune.activities.MainActivity;
import patrickengelkes.com.alleneune.activities.ShowEventActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by patrickengelkes on 06/02/15.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class ClubHomeTest {
    @Mock
    CurrentClub currentClub;

    private ClubHomeActivity clubHomeActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(currentClub.getClubID()).thenReturn(1);

        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        TestGuiceModule module = new TestGuiceModule();
        module.addBinding(CurrentClub.class, currentClub);
        TestGuiceModule.setUp(this, module);

        clubHomeActivity = Robolectric.buildActivity(ClubHomeActivity.class).create().get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(clubHomeActivity);
    }

    @Test
    public void checkComponentsNotNull() {
        ListView eventListView = clubHomeActivity.getListView();

        assertNotNull(eventListView);
    }

    @Test
    public void logOut() {
        MenuItem logOutItem = new TestMenuItem(R.id.logout_action);

        clubHomeActivity.onOptionsItemSelected(logOutItem);

        Intent intent = Robolectric.shadowOf(clubHomeActivity).peekNextStartedActivity();
        assertEquals(MainActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void editFriends() {
        MenuItem editFriendsItem = new TestMenuItem(R.id.edit_friends_action);

        clubHomeActivity.onOptionsItemSelected(editFriendsItem);

        Intent intent = Robolectric.shadowOf(clubHomeActivity).peekNextStartedActivity();
        assertEquals(EditFriendsActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void showEvent() {
        ListView eventListView = clubHomeActivity.getListView();

        Robolectric.shadowOf(eventListView).performItemClick(0);
        Intent intent = Robolectric.shadowOf(clubHomeActivity).peekNextStartedActivity();
        assertEquals(ShowEventActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

}
