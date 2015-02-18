package patrickengelkes.com.alleneune.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.controllers.EventController;
import patrickengelkes.com.alleneune.entities.objects.Event;
import patrickengelkes.com.alleneune.entities.objects.User;
import roboguice.activity.RoboListActivity;

public class ShowEventActivity extends RoboListActivity {
    protected TextView eventNameTextView;
    protected TextView eventDateTextView;
    protected Intent eventIntent;
    protected Event event;
    @Inject
    EventController eventController;
    private List<User> userList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        eventNameTextView = (TextView) findViewById(R.id.name_text_view);
        eventDateTextView = (TextView) findViewById(R.id.event_date_text_view);

        this.eventIntent = getIntent();
        this.event = eventIntent.getParcelableExtra(Event.PARCELABLE);
        eventNameTextView.setText(event.getEventName());

        eventDateTextView.setText(event.getEventDate());

        userList = eventController.getEventParticipants(event.getEventID());
        ArrayAdapter<User> userArrayAdapter = new ArrayAdapter<User>(this,
                android.R.layout.simple_list_item_1, userList);

        setListAdapter(userArrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
