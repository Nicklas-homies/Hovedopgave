package com.homies.hovedopgave.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.UserRepo;

public class ForgotPasswordActivity extends AppCompatActivity implements Updatable {

    private EditText emailEditText;
    private Button resetPasswordButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        UserRepo.r().setup(this);
        emailEditText = findViewById(R.id.forgotPasswordEmail);
        resetPasswordButton = findViewById(R.id.resetButton);
        progressBar = findViewById(R.id.progressBar);


    }

    public void resetPasswordClicked (View view) {
        String email = emailEditText.getText().toString();

        if (email.isEmpty()) {
            emailEditText.setError(getString(R.string.email_empty));
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError(getString(R.string.email_pattern));
            emailEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        UserRepo.r().resetPassword(email, getApplicationContext());
    }

    @Override
    public void update(Object o) {
        finish();
    }
}