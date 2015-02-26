package patrickengelkes.com.alleneune.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import patrickengelkes.com.alleneune.CurrentClub;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.adapters.array_adapters.ExpandableEventAdapter;
import patrickengelkes.com.alleneune.entities.controllers.EventController;
import patrickengelkes.com.alleneune.entities.objects.Event;
import patrickengelkes.com.alleneune.entities.objects.User;
import roboguice.activity.RoboActivity;

public class ClubHomeActivity extends RoboActivity {
    protected Button createEventButton;

    protected List<Event> eventList = new ArrayList<Event>();
    protected HashMap<Event, List<User>> userList = new HashMap<Event, List<User>>();
    @Inject
    EventController eventController;
    @Inject
    CurrentClub currentClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_home);

        setTitle(currentClub.getName());

        eventList = eventController.getEventsByClub();
        for (Event event : eventList) {
            List<User> users = eventController.getEventParticipants(event.getId());
            userList.put(event, users);
        }

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandable_event_view);
        ExpandableEventAdapter expandableEventAdapter = new ExpandableEventAdapter(this, eventList, userList);
        expandableListView.setAdapter(expandableEventAdapter);
        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View convertView, int position, long id) {
                Event event = eventList.get(position);
                Intent showEventIntent = new Intent(ClubHomeActivity.this, ShowEventActivity.class);
                showEventIntent.putExtra(Event.PARCELABLE, event);
                startActivity(showEventIntent);

                return true;
            }
        });

        createEventButton = (Button) findViewById(R.id.create_event_button);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createEventIntent = new Intent(ClubHomeActivity.this, CreateEventActivity.class);
                startActivity(createEventIntent);
            }
        });
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
            case R.id.events_action:
                break;
            case R.id.drinks_action:
                Intent drinkIntent = new Intent(ClubHomeActivity.this, DrinkActivity.class);
                startActivity(drinkIntent);
                break;
            case R.id.fines_action:
                Intent fineIntent = new Intent(ClubHomeActivity.this, FineActivity.class);
                startActivity(fineIntent);
                break;
            case R.id.logout_action:
                Intent signUpIntent = new Intent(ClubHomeActivity.this, MainActivity.class);
                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signUpIntent);
                break;
            case R.id.edit_friends_action:
                Intent editMembersIntent = new Intent(ClubHomeActivity.this, EditFriendsActivity.class);
                startActivity(editMembersIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
