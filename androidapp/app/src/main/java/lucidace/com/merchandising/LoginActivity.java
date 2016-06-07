package lucidace.com.merchandising;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import lucidace.com.merchandising.app.MyApplication;
import lucidace.com.merchandising.models.common.User;
import lucidace.com.merchandising.network.handlers.AuthHandler;
import lucidace.com.merchandising.network.handlers.AuthListener;

public class LoginActivity extends AppCompatActivity {

    private EditText inputName;
    private EditText inputPassword;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        inputName = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);

        inputLayoutName = (TextInputLayout) findViewById(R.id.usernameWrapper);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.passwordWrapper);
        setSupportActionBar(toolbar);
    }
    public void onLoginClicked(View v){
        login();
    }


    private void login() {
        if (!validateName()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        final String name = inputName.getText().toString();
        final String password = inputPassword.getText().toString();

        AuthHandler authHandler = new AuthHandler();
        authHandler.login(name, password, new AuthListener() {
            @Override
            public void loginSuccessFull(User user) {

                MyApplication.getInstance().getPrefManager().storeUser(user);
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                finish();
            }

            @Override
            public void loginFailed() {
                Toast.makeText(getApplicationContext(), "User Credentials Not Valid", Toast.LENGTH_LONG).show();
            }

            @Override
            public void networkFailed() {
                Toast.makeText(getApplicationContext(), "NetWork Error", Toast.LENGTH_LONG).show();

            }
        });
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_pwd));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
