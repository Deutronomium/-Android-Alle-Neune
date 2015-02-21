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

import patrickengelkes.com.alleneune.CurrentUser;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.dialogs.ErrorDialog;
import patrickengelkes.com.alleneune.entities.controllers.SessionController;
import patrickengelkes.com.alleneune.entities.objects.Session;
import patrickengelkes.com.alleneune.enums.ApiCall;
import roboguice.activity.RoboActivity;

public class MainActivity extends RoboActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    protected EditText mEmail;
    protected EditText mPassword;
    protected Button mLogInButton;
    protected Button mSignUpButton;
    @Inject
    CurrentUser currentUser;
    @Inject
    SessionController sessionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = (EditText) findViewById(R.id.sign_up_name_edit_text);
        mPassword = (EditText) findViewById(R.id.sign_up_password_edit_text);

        mLogInButton = (Button) findViewById(R.id.log_in_button);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                ApiCall response = sessionController.logIn(email, password);
                if (response == ApiCall.SUCCESS) {
                    Intent userHomeIntent = new Intent(MainActivity.this, UserHomeActivity.class);
                    userHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    userHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(userHomeIntent);
                } else if (response == ApiCall.ACCESS_DENIED) {
                    AlertDialog alert = new ErrorDialog(MainActivity.this,
                            getString(R.string.wrong_user_credentials_warning))
                            .create();
                    alert.show();
                } else if (response == ApiCall.BAD_REQUEST) {
                    AlertDialog alert = new ErrorDialog(MainActivity.this,
                            getString(R.string.bad_request_warning))
                            .create();
                    alert.show();
                }
            }
        });

        mSignUpButton = (Button) findViewById(R.id.sign_up_button);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
