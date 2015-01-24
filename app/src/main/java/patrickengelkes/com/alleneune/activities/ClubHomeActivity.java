package patrickengelkes.com.alleneune.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import patrickengelkes.com.alleneune.array_adapters.EventsArrayAdapter;
import patrickengelkes.com.alleneune.entities.controllers.EventController;
import patrickengelkes.com.alleneune.entities.objects.Club;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.objects.Event;

public class ClubHomeActivity extends ListActivity {

    protected TextView clubName;
    protected Button createEventButton;

    protected Intent clubIntent;
    protected Club club;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_home);

        clubIntent = getIntent();
        club = Club.getInstance();
        createEventButton = (Button) findViewById(R.id.create_activity_button);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createActivityIntent = new Intent(ClubHomeActivity.this, CreateEventActivity.class);
                createActivityIntent.putExtra("club", club);
                startActivity(createActivityIntent);
            }
        });
        clubName = (TextView) findViewById(R.id.club_name_tv);

        clubName.setText(club.getClubName());

        Event event = new Event(club.getClubID());
        EventController eventController = new EventController(event);
        List<Event> clubEvents = eventController.getEventsByClub();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_logout:
                Intent signUpIntent = new Intent(ClubHomeActivity.this, MainActivity.class);
                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signUpIntent);
                break;
            case R.id.action_edit_members:
                Intent editMembersIntent = new Intent(ClubHomeActivity.this, EditFriendsActivity.class);
                startActivity(editMembersIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
