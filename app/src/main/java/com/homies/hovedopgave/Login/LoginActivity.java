package com.homies.hovedopgave.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.homies.hovedopgave.Fragments.ProfileFragment;
import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.UserRepo;
import com.homies.hovedopgave.models.User;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements Updatable {
    private List<User> users = new ArrayList<>();
    private TextView register, forgotpassword;
    private EditText editTextEmail, editTextPassword;
    private Button loginButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserRepo.r().setup(this, users);

        register = (TextView) findViewById(R.id.register);
        forgotpassword = (TextView) findViewById(R.id.forgotPassword);

        loginButton = (Button) findViewById(R.id.loginButton);

        editTextEmail = (EditText) findViewById(R.id.emailLogin);
        editTextPassword = (EditText) findViewById(R.id.passwordLogin);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


    }

    public void onClickSwitch(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.loginButton:
                userLogin();
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.email_empty));
            editTextEmail.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getString(R.string.email_pattern));
            editTextEmail.requestFocus();
        }

        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.password_empty));
            editTextPassword.requestFocus();
        }

        if (password.length() < 6) {
            editTextPassword.setError(getString(R.string.password_length));
            editTextPassword.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);
        UserRepo.r().checkAuth(email, password, getApplicationContext());
    }

    @Override
    public void update(Object o) {
        progressBar.setVisibility(View.GONE);
        int result = (int) o;
        if (result == 1) {
            Toast.makeText(LoginActivity.this, getString(R.string.login_succes), Toast.LENGTH_LONG).show();
            finish();
        } else if (result == -1){
            Toast.makeText(LoginActivity.this, getString(R.string.login_unsucces), Toast.LENGTH_LONG).show();
        } else {
            System.out.println("Update is GG");
        }
    }
}