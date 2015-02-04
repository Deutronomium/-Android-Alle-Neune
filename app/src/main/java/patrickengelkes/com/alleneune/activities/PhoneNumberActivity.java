package patrickengelkes.com.alleneune.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.inject.Inject;

import patrickengelkes.com.alleneune.dialogs.ErrorDialog;
import patrickengelkes.com.alleneune.entities.controllers.UserController;
import patrickengelkes.com.alleneune.entities.objects.User;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.enums.ApiCall;
import roboguice.activity.RoboActivity;

public class PhoneNumberActivity extends RoboActivity {

    @Inject
    UserController userController;

    protected EditText phoneNumberTF;
    protected Button signUpButton;
    protected Intent userIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        userIntent = getIntent();

        phoneNumberTF = (EditText) findViewById(R.id.phone_number_edit_text);
        signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = userIntent.getParcelableExtra("user");
                user.setPhoneNumber(phoneNumberTF.getText().toString().trim());

                ApiCall response = userController.createUser(user.getUserName(), user.getEmail(),
                        user.getPassword(), user.getPasswordConfirmation(), user.getPhoneNumber());
                if (response == ApiCall.CREATED) {
                    Intent homeIntent = new Intent(PhoneNumberActivity.this, UserHomeActivity.class);
                    startActivity(homeIntent);
                } else if (response == ApiCall.UNPROCESSABLE_ENTITY) {
                    AlertDialog dialog = new ErrorDialog(PhoneNumberActivity.this,
                            getString(R.string.phone_number_exists_warning))
                            .create();
                    dialog.show();
                } else if (response == ApiCall.BAD_REQUEST) {
                    AlertDialog dialog = new ErrorDialog(PhoneNumberActivity.this,
                            getString(R.string.bad_request_warning))
                            .create();
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
