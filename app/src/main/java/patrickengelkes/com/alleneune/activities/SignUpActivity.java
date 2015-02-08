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

import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.dialogs.ErrorDialog;
import patrickengelkes.com.alleneune.entities.controllers.UserController;
import patrickengelkes.com.alleneune.entities.objects.User;
import patrickengelkes.com.alleneune.enums.UserValidation;
import roboguice.activity.RoboActivity;

public class SignUpActivity extends RoboActivity {

    protected Button continueButton;
    protected EditText userNameEditText;
    protected EditText passwordEditText;
    protected EditText confirmationPasswordEditText;
    protected EditText emailEditText;
    @Inject
    UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userNameEditText = (EditText) findViewById(R.id.sign_up_name_edit_text);
        passwordEditText = (EditText) findViewById(R.id.sign_up_password_edit_text);
        confirmationPasswordEditText = (EditText) findViewById(R.id.password_confirmation_edit_text);
        emailEditText = (EditText) findViewById(R.id.sign_up_email_edit_text);

        continueButton = (Button) findViewById(R.id.sign_up_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String passwordConfirmation = confirmationPasswordEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();

                User user = new User(userName, email, password, passwordConfirmation);
                UserValidation response = userController.checkValidity(userName, email,
                        password, passwordConfirmation, "");
                if (response == UserValidation.SUCCESS) {
                    Intent phoneNumberIntent = new Intent(SignUpActivity.this, PhoneNumberActivity.class);
                    phoneNumberIntent.putExtra("user", user);
                    startActivity(phoneNumberIntent);
                } else if (response == UserValidation.USER_AND_EMAIL) {
                    AlertDialog alert = new ErrorDialog(SignUpActivity.this,
                            getString(R.string.user_and_email_used_warning)).create();
                    alert.show();
                } else if (response == UserValidation.USER) {
                    AlertDialog alert = new ErrorDialog(SignUpActivity.this,
                            getString(R.string.user_used_warning)).create();
                    alert.show();
                } else if (response == UserValidation.EMAIL) {
                    AlertDialog alert = new ErrorDialog(SignUpActivity.this,
                            getString(R.string.email_used_warning)).create();
                    alert.show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_up, menu);
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
