package patrickengelkes.com.alleneune.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.inject.Inject;

import patrickengelkes.com.alleneune.DecimalInputTextWatcher;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.controllers.DrinkController;
import roboguice.activity.RoboActivity;

public class CreateDrinkActivity extends RoboActivity {
    @Inject
    DrinkController drinkController;

    EditText drinkNameEditText;
    EditText drinkPriceEditText;
    Button createDrinkButton;
    DecimalInputTextWatcher decimalInputTextWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_drink);

        drinkNameEditText = (EditText) findViewById(R.id.drink_name_edit_text);
        drinkPriceEditText = (EditText) findViewById(R.id.drink_price_edit_text);
        decimalInputTextWatcher = new DecimalInputTextWatcher(drinkPriceEditText, 2);
        drinkPriceEditText.addTextChangedListener(new DecimalInputTextWatcher(drinkPriceEditText, 2));
        createDrinkButton = (Button) findViewById(R.id.create_drink_button);
        createDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = drinkNameEditText.getText().toString().trim();
                double price = Double.valueOf(drinkPriceEditText.getText().toString().trim());
                drinkController.create(name, price);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_drink, menu);
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
