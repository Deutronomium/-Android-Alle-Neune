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

import patrickengelkes.com.alleneune.entities.objects.User;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.controllers.AbstractEntityController;

public class SignUpActivity extends Activity {

    final Context context = this;

    protected Button mSignUpButton;
    protected EditText mUserName;
    protected EditText mPassword;
    protected EditText mConfirmationPassword;
    protected EditText mEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUserName = (EditText) findViewById(R.id.userNameTF);
        mPassword = (EditText) findViewById(R.id.passwordTF);
        mConfirmationPassword = (EditText) findViewById(R.id.password_confirmationTF);
        mEmail = (EditText) findViewById(R.id.emailTF);

        mSignUpButton = (Button) findViewById(R.id.sign_up_button);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mUserName.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String passwordConfirmation = mConfirmationPassword.getText().toString().trim();
                String email = mEmail.getText().toString().trim();

                User user = new User(userName, email, password, passwordConfirmation);
                AbstractEntityController controller = new AbstractEntityController(user);
                if (controller.checkForValidity()) {
                    Intent phoneNumberIntent = new Intent(SignUpActivity.this, PhoneNumberActivity.class);
                    phoneNumberIntent.putExtra("user", user);
                    startActivity(phoneNumberIntent);
                } else {
                    JSONObject jsonResponse = controller.getValidateAbstractAnswer();
                    try {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                        dialogBuilder.setTitle(getString(R.string.validation_failed_title))
                            .setMessage((String)jsonResponse.get((String)jsonResponse.names().get(0)))
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
