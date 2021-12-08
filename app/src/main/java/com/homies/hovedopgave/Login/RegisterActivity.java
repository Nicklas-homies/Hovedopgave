package com.homies.hovedopgave.Login;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.UserRepo;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.models.User;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements Updatable {
    private List<User> users = new ArrayList<>();
    private Button registerUser;
    private EditText editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserRepo.r().setup(this, users);
        setContentView(R.layout.activity_register);

        registerUser = findViewById(R.id.registerButton);

        editTextEmail = (EditText) findViewById(R.id.emailRegister);
        editTextPassword = (EditText) findViewById(R.id.passwordRegister);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    public void registerUserClicked (View v) {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.email_empty));
            editTextEmail.requestFocus();
            return;
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
        UserRepo.r().createAuth(email, password);
    }

    @Override
    public void update(Object o) {
        progressBar.setVisibility(View.GONE);
        int result = (int) o;
        if (result == 1) {
            Toast.makeText(RegisterActivity.this, getString(R.string.registeruser_succes), Toast.LENGTH_LONG).show();
            finish();
        } else if (result == -1){
            Toast.makeText(RegisterActivity.this, getString(R.string.registeruser_unsucces), Toast.LENGTH_LONG).show();
        } else {
            System.out.println("Update is GG");
        }
    }
}