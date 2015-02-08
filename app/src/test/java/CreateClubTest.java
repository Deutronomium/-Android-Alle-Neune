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
import patrickengelkes.com.alleneune.activities.AddFriendsActivity;
import patrickengelkes.com.alleneune.activities.CreateClubActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by patrickengelkes on 08/02/15.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class CreateClubTest {
    CreateClubActivity createClubActivity;

    @Before
    public void setup() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        createClubActivity = Robolectric.buildActivity(CreateClubActivity.class).create().get();
    }

    @Test
    public void checkActivityNotNull() {
        assertNotNull(createClubActivity);
    }

    @Test
    public void checkComponentsNotNull() {
        EditText clubNameEditText = (EditText) createClubActivity.findViewById(R.id.club_name_edit_text);
        Button addFriendsButton = (Button) createClubActivity.findViewById(R.id.add_friends_button);

        assertNotNull(clubNameEditText);
        assertNotNull(addFriendsButton);
    }

    @Test
    public void createClubWithValidData() {
        String randomName = "";
        while (randomName.length() < 3) {
            randomName = TestHelper.getRandomName();
        }
        setCreateClubData(TestHelper.getRandomName());

        Intent intent = Robolectric.shadowOf(createClubActivity).peekNextStartedActivity();
        assertEquals(AddFriendsActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void createClubWithInvalidData() {
        setCreateClubData("TestClub");

        TestHelper.checkAlertDialog(createClubActivity.getString(R.string.club_name_exist_warning));
    }

    private void setCreateClubData(String clubName) {
        EditText clubNameEditText = (EditText) createClubActivity.findViewById(R.id.club_name_edit_text);
        Button addFriendsButton = (Button) createClubActivity.findViewById(R.id.add_friends_button);

        clubNameEditText.setText(clubName);
        addFriendsButton.performClick();
    }

}
