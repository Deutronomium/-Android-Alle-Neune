package patrickengelkes.com.alleneune.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.inject.Inject;

import java.util.List;

import patrickengelkes.com.alleneune.CurrentClub;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.array_adapters.adapters.FineArrayAdapter;
import patrickengelkes.com.alleneune.dialogs.FineDialog;
import patrickengelkes.com.alleneune.entities.controllers.FineController;
import patrickengelkes.com.alleneune.entities.objects.Fine;
import roboguice.activity.RoboListActivity;

public class FineActivity extends RoboListActivity {
    @Inject
    CurrentClub currentClub;
    @Inject
    FineController fineController;

    protected Button createFineButton;

    FineArrayAdapter fineArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fine);

        List<Fine> fineList = fineController.getByClub(currentClub.getClubID());
        fineArrayAdapter = new FineArrayAdapter(FineActivity.this, fineList);

        createFineButton = (Button) findViewById(R.id.create_fine_button);
        createFineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FineDialog fineDialog = new FineDialog(FineActivity.this,
                        fineArrayAdapter, null);
                fineDialog.show();
            }
        });

        setListAdapter(fineArrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fine, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.events_action:
                break;
            case R.id.drinks_action:
                Intent drinkIntent = new Intent(FineActivity.this, DrinkActivity.class);
                startActivity(drinkIntent);
                break;
            case R.id.fines_action:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent clubHomeIntent = new Intent(FineActivity.this, ClubHomeActivity.class);
        startActivity(clubHomeIntent);
        finish();
    }
}
