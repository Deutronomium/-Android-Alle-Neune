import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import patrickengelkes.com.alleneune.CurrentClub;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.activities.AddFriendsActivity;
import patrickengelkes.com.alleneune.activities.ClubHomeActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by patrickengelkes on 08/02/15.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class AddFriendsTest {
    @Mock
    CurrentClub currentClub;

    private AddFriendsActivity addFriendsActivity;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);

        TestGuiceModule module = new TestGuiceModule();
        module.addBinding(CurrentClub.class, currentClub);
        TestGuiceModule.setUp(this, module);
        addFriendsActivity = Robolectric.buildActivity(AddFriendsActivity.class).create().get();
    }

    @Test
    public void checkActivityNotNull() {
        assertNotNull(addFriendsActivity);
    }

    @Test
    public void checkComponentsNotNull() {
        ListView friendsListView = addFriendsActivity.getListView();
        Button addFriendsToClubButton = (Button) addFriendsActivity.findViewById(R.id.add_friends_to_club_button);

        assertNotNull(friendsListView);
        assertNotNull(addFriendsToClubButton);
    }

    @Test
    public void addingFriendsToClub() {
        when(currentClub.getId()).thenReturn(1);
        Button addFriendsToClubButton = (Button) addFriendsActivity.findViewById(R.id.add_friends_to_club_button);

        addFriendsToClubButton.performClick();

        Intent intent = Robolectric.shadowOf(addFriendsActivity).peekNextStartedActivity();
        assertEquals(ClubHomeActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void addFriendsToWrongClub() {
        when(currentClub.getId()).thenReturn(100);
        Button addFriendsToClubButton = (Button) addFriendsActivity.findViewById(R.id.add_friends_to_club_button);

        addFriendsToClubButton.performClick();

        TestHelper.checkAlertDialog(addFriendsActivity.getString(R.string.club_does_not_exist_warning));
    }
}
