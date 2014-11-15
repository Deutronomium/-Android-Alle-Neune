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

import org.json.JSONObject;

import patrickengelkes.com.alleneune.Objects.User;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.controllers.AbstractEntityController;

public class PhoneNumberActivity extends Activity {

    final Context context = this;

    protected EditText phoneNumberTF;
    protected Button signUpButton;
    protected Intent userIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        userIntent = getIntent();

        phoneNumberTF = (EditText) findViewById(R.id.phone_number_TF);
        signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = userIntent.getParcelableExtra("user");
                user.setPhoneNumber(phoneNumberTF.getText().toString().trim());
                AbstractEntityController controller = new AbstractEntityController(user);
                if (controller.createAbstractEntity()) {
                    Intent homeIntent = new Intent(PhoneNumberActivity.this, UserHomeActivity.class);
                    startActivity(homeIntent);
                } else {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                    dialogBuilder.setTitle("Phone number not unique")
                            .setMessage("Please select another one")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = dialogBuilder.create();
                    dialog.show();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_phone_number, menu);
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
