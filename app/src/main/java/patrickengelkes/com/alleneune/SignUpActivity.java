package patrickengelkes.com.alleneune;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;


public class SignUpActivity extends Activity {

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
                String confirmationPassword = mConfirmationPassword.getText().toString().trim();
                String email = mEmail.getText().toString().trim();

                UserParams params = new UserParams(userName, password, confirmationPassword, email, "", "", "", "", 0);
                UserController userController = new UserController(params);
                if (userController.createUser()) {
                    Intent intent = new Intent(SignUpActivity.this, UserHomeActivity.class);
                    startActivity(intent);
                } else {
                    JSONObject jsonResponse = userController.getCreateUserAnswer();
                    Toast.makeText(SignUpActivity.this, "User creation failed", Toast.LENGTH_LONG).show();
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
