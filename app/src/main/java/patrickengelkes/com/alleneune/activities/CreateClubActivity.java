package patrickengelkes.com.alleneune.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import patrickengelkes.com.alleneune.entities.objects.Club;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.controllers.AbstractEntityController;

public class CreateClubActivity extends Activity {

    final Context context = this;

    protected Button addFriendsButton;
    protected EditText mClubName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);

        mClubName= (EditText) findViewById(R.id.club_name_tf);
        addFriendsButton = (Button) findViewById(R.id.add_friends_button);
        addFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubName = mClubName.getText().toString().trim();

                Club club = new Club(clubName);
                AbstractEntityController controller = new AbstractEntityController(club);
                //Intent addFriendsIntent = new Intent(CreateClubActivity.this, AddFriendsActivity.class);
                //startActivity(addFriendsIntent);
                //TODO: Uncomment Code - above is just for testing
                if (controller.createAbstractEntity()) {
                    Intent addFriendsIntent = new Intent(CreateClubActivity.this, AddFriendsActivity.class);
                    addFriendsIntent.putExtra("club", club);
                    startActivity(addFriendsIntent);
                } else {
                    JSONObject jsonResponse = controller.getCreateAbstractAnswer();
                    try {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                        dialogBuilder.setTitle(getString(R.string.validation_failed_title))
                                .setMessage((String)jsonResponse.get((String) jsonResponse.names().get(0)))
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = dialogBuilder.create();
                        dialog.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
