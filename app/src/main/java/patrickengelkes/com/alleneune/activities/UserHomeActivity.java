package patrickengelkes.com.alleneune.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import patrickengelkes.com.alleneune.entities.objects.Club;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.controllers.UserController;
import patrickengelkes.com.alleneune.entities.objects.User;


public class UserHomeActivity extends Activity {

    protected Button mCreateClubButton;
    protected Button mJoinClubButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Club club = null;
        try {
            User user = new User("Deutro");
            UserController userController = new UserController(user);
            club = userController.getClubByUser();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        if (club != null) {
            Intent clubHomeIntent = new Intent(UserHomeActivity.this, ClubHomeActivity.class);
            clubHomeIntent.putExtra("club", club);
            clubHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            clubHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(clubHomeIntent);
        } else {
            setContentView(R.layout.activity_user_home);

            mCreateClubButton = (Button) findViewById(R.id.createClubButton);
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
        getMenuInflater().inflate(R.menu.user_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
