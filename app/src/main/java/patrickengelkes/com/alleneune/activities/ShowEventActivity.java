package patrickengelkes.com.alleneune.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import patrickengelkes.com.alleneune.CurrentClub;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.array_adapters.adapters.ParticipantArrayAdapter;
import patrickengelkes.com.alleneune.entities.controllers.DrinkController;
import patrickengelkes.com.alleneune.entities.controllers.EventController;
import patrickengelkes.com.alleneune.entities.objects.Drink;
import patrickengelkes.com.alleneune.entities.objects.Event;
import patrickengelkes.com.alleneune.entities.objects.User;
import roboguice.activity.RoboListActivity;

public class ShowEventActivity extends RoboListActivity {
    protected Intent eventIntent;
    protected Event event;
    @Inject
    EventController eventController;
    @Inject
    DrinkController drinkController;
    @Inject
    CurrentClub currentClub;
    private List<User> userList = new ArrayList<User>();
    private List<Drink> drinkList = new ArrayList<Drink>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        this.eventIntent = getIntent();
        this.event = eventIntent.getParcelableExtra(Event.PARCELABLE);

        setTitle(this.event.getName());

        userList = eventController.getEventParticipants(event.getId());
        drinkList = drinkController.getByClub(currentClub.getId());

        ParticipantArrayAdapter participantArrayAdapter = new ParticipantArrayAdapter(ShowEventActivity.this,
                userList, this.event.getId(), drinkList);
        setListAdapter(participantArrayAdapter);
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
