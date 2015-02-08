package patrickengelkes.com.alleneune.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.inject.Inject;

import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.dialogs.ErrorDialog;
import patrickengelkes.com.alleneune.entities.controllers.ClubController;
import patrickengelkes.com.alleneune.enums.ApiCall;
import roboguice.activity.RoboActivity;

public class CreateClubActivity extends RoboActivity {

    final Context context = this;
    protected Button addFriendsButton;
    protected EditText clubNameEditText;
    @Inject
    ClubController clubController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);

        clubNameEditText = (EditText) findViewById(R.id.club_name_edit_text);
        addFriendsButton = (Button) findViewById(R.id.add_friends_button);
        addFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubName = clubNameEditText.getText().toString().trim();

                ApiCall response = clubController.createClub(clubName);
                if (response == ApiCall.CREATED) {
                    Intent addFriendsIntent = new Intent(CreateClubActivity.this, AddFriendsActivity.class);
                    startActivity(addFriendsIntent);
                } else if (response == ApiCall.UNPROCESSABLE_ENTITY) {
                    AlertDialog alert = new ErrorDialog(CreateClubActivity.this,
                            getString(R.string.club_name_exist_warning)).create();
                    alert.show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_club, menu);
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
