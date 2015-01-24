package patrickengelkes.com.alleneune.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import patrickengelkes.com.alleneune.entities.controllers.AbstractEntityController;
import patrickengelkes.com.alleneune.entities.controllers.SessionController;
import patrickengelkes.com.alleneune.entities.objects.Session;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.objects.User;

public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();

    protected EditText mEmail;
    protected EditText mPassword;

    protected Button mLogInButton;
    protected Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = (EditText) findViewById(R.id.userNameTF);
        mPassword = (EditText) findViewById(R.id.passwordTF);

        mLogInButton = (Button) findViewById(R.id.logInButton);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                Session session = new Session(email, password);
                SessionController sessionController = new SessionController(session);
                sessionController.logIn();
                User user = User.getInstance();
                if (user.getUserName() != null) {
                        Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
                        startActivity(intent);
                    } else {
                        //TODO: Error handling
                    }
            }
        });

        mSignUpButton = (Button) findViewById(R.id.signUpButton);
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
