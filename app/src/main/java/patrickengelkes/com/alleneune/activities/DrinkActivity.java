package patrickengelkes.com.alleneune.activities;

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
import patrickengelkes.com.alleneune.array_adapters.adapters.DrinkArrayAdapter;
import patrickengelkes.com.alleneune.dialogs.DrinkDialog;
import patrickengelkes.com.alleneune.entities.controllers.DrinkController;
import patrickengelkes.com.alleneune.entities.objects.Drink;
import roboguice.activity.RoboListActivity;

public class DrinkActivity extends RoboListActivity {
    @Inject
    CurrentClub currentClub;
    @Inject
    DrinkController drinkController;

    protected Button createDrinkButton;

    DrinkArrayAdapter drinkArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        List<Drink> drinksList = drinkController.getByClub(currentClub.getClubID());
        drinkArrayAdapter = new DrinkArrayAdapter(DrinkActivity.this, drinksList);

        createDrinkButton = (Button) findViewById(R.id.create_drink_button);
        createDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrinkDialog drinkDialog = new DrinkDialog(DrinkActivity.this,
                        drinkArrayAdapter, null);

                drinkDialog.show();
            }
        });

        setListAdapter(drinkArrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drink, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.events_action:
                break;
            case R.id.drinks_action:
            case R.id.fines_action:
                Intent fineIntent = new Intent(DrinkActivity.this, FineActivity.class);
                startActivity(fineIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent clubHomeIntent = new Intent(DrinkActivity.this, ClubHomeActivity.class);
        startActivity(clubHomeIntent);
        finish();
    }
}
