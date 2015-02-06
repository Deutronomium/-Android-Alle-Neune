package patrickengelkes.com.alleneune.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import patrickengelkes.com.alleneune.CurrentClub;
import patrickengelkes.com.alleneune.array_adapters.adapters.EventsArrayAdapter;
import patrickengelkes.com.alleneune.entities.controllers.EventController;
import patrickengelkes.com.alleneune.entities.objects.Club;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.objects.Event;
import roboguice.activity.RoboListActivity;

public class ClubHomeActivity extends RoboListActivity {
    @Inject
    EventController eventController;
    @Inject
    CurrentClub currentClub;

    protected Button createEventButton;

    protected Intent clubIntent;
    protected List<Event> clubEvents = new ArrayList<Event>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_home);

        clubIntent = getIntent();
        createEventButton = (Button) findViewById(R.id.create_activity_button);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createActivityIntent = new Intent(ClubHomeActivity.this, CreateEventActivity.class);
                startActivity(createActivityIntent);
            }
        });

        setTitle(currentClub.getClubName());

        clubEvents = eventController.getEventsByClub(currentClub.getClubID());
        EventsArrayAdapter eventsArrayAdapter = new EventsArrayAdapter(this, clubEvents);
        setListAdapter(eventsArrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_club_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_action:
                Intent signUpIntent = new Intent(ClubHomeActivity.this, MainActivity.class);
                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signUpIntent);
                break;
            case R.id.edit_members_action:
                Intent editMembersIntent = new Intent(ClubHomeActivity.this, EditFriendsActivity.class);
                startActivity(editMembersIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Event selectedEvent = clubEvents.get(position);
        Intent showEventIntent = new Intent(ClubHomeActivity.this, EventActivity.class);
        showEventIntent.putExtra(Event.PARCELABLE, selectedEvent);
        startActivity(showEventIntent);
    }
}
