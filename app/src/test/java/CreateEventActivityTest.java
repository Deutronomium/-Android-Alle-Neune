import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import java.util.Calendar;

import patrickengelkes.com.alleneune.CurrentClub;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.activities.CreateEventActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by patrickengelkes on 10/02/15.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class CreateEventActivityTest {
    @Mock
    CurrentClub currentClub;
    @Mock
    Calendar globalCalendar;

    CreateEventActivity createEventActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(currentClub.getId()).thenReturn(1);

        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        TestGuiceModule module = new TestGuiceModule();
        module.addBinding(CurrentClub.class, currentClub);
        TestGuiceModule.setUp(this, module);

        createEventActivity = Robolectric.buildActivity(CreateEventActivity.class).create().get();
    }

    @Test
    public void checkActivityNotNull() {
        assertNotNull(createEventActivity);
    }

    @Test
    public void checkComponentsNotNull() {
        EditText eventNameEditText = (EditText) createEventActivity.findViewById(R.id.event_name_edit_text);
        TextView datePickerTextView = (TextView) createEventActivity.findViewById(R.id.date_picker_text_view);
        TextView timePickerTextView = (TextView) createEventActivity.findViewById(R.id.time_picker_text_view);
        Button sendEventInvitesButton = (Button) createEventActivity.findViewById(R.id.send_event_invites_button);

        assertNotNull(eventNameEditText);
        assertNotNull(datePickerTextView);
        assertNotNull(timePickerTextView);
        assertNotNull(sendEventInvitesButton);
    }

    @Test
    public void createEvent() {
        EditText eventNameEditText = (EditText) createEventActivity.findViewById(R.id.event_name_edit_text);
        Button sendEventInvitesButton = (Button) createEventActivity.findViewById(R.id.send_event_invites_button);

        eventNameEditText.setText("Kegeln");

        globalCalendar.set(2014, 2, 28, 6, 36);

        sendEventInvitesButton.performClick();

        assertEquals(ShadowToast.getTextOfLatestToast(), createEventActivity.getString(R.string.event_created_toast));
        ShadowActivity shadowActivity = Robolectric.shadowOf(createEventActivity);
        assertTrue(shadowActivity.isFinishing());
    }
}
