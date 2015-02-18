package patrickengelkes.com.alleneune.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.inject.Inject;

import java.util.List;

import patrickengelkes.com.alleneune.CurrentClub;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.array_adapters.adapters.DrinksArrayAdapter;
import patrickengelkes.com.alleneune.dialogs.DrinkDialog;
import patrickengelkes.com.alleneune.entities.controllers.DrinkController;
import patrickengelkes.com.alleneune.entities.objects.Drink;
import roboguice.activity.RoboListActivity;

public class DrinkActivity extends RoboListActivity {
    protected Button createDrinkButton;

    @Inject
    CurrentClub currentClub;
    @Inject
    DrinkController drinkController;

    DrinksArrayAdapter drinksArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        List<Drink> drinksList = drinkController.getDrinksByClub(currentClub.getClubID());
        drinksArrayAdapter = new DrinksArrayAdapter(DrinkActivity.this, drinksList);

        createDrinkButton = (Button) findViewById(R.id.create_drink_button);
        createDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrinkDialog drinkDialog = new DrinkDialog(DrinkActivity.this,
                        drinksArrayAdapter, null);

                drinkDialog.show();
            }
        });

        setListAdapter(drinksArrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drink, menu);
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
