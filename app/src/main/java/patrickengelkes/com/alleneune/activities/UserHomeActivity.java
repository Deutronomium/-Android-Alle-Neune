package patrickengelkes.com.alleneune.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.inject.Inject;

import org.json.JSONException;

import patrickengelkes.com.alleneune.CurrentClub;
import patrickengelkes.com.alleneune.CurrentUser;
import patrickengelkes.com.alleneune.entities.objects.Club;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.controllers.UserController;
import patrickengelkes.com.alleneune.enums.UserClub;
import roboguice.activity.RoboActivity;


public class UserHomeActivity extends RoboActivity {
    public static final String TAG = UserHomeActivity.class.getSimpleName();

    @Inject
    CurrentUser currentUser;
    @Inject
    UserController userController;
    @Inject
    CurrentClub currentClub;

    protected Button mCreateClubButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserClub response = UserClub.USER_HAS_NO_CLUB;

        try {
            String userName = currentUser.getUserName();
            if (userController.getClubByUserName(userName) == UserClub.USER_HAS_CLUB) {
                response = UserClub.USER_HAS_CLUB;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (response == UserClub.USER_HAS_CLUB) {
            Intent clubHomeIntent = new Intent(UserHomeActivity.this, ClubHomeActivity.class);
            clubHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            clubHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(clubHomeIntent);
        } else {
            setContentView(R.layout.activity_user_home);

            setTitle(currentUser.getUserName());

            mCreateClubButton = (Button) findViewById(R.id.create_club_button);
            mCreateClubButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent createClubIntent = new Intent(UserHomeActivity.this, CreateClubActivity.class);
                    startActivity(createClubIntent);
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.logout_action:
                Intent signUpIntent = new Intent(UserHomeActivity.this, MainActivity.class);
                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signUpIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
