package com.example.tp_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etConfirm;
    private Button btnSignUp;
    private TextView tvMessage, tvGoLogin;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        session = new SessionManager(this);

        etUsername = findViewById(R.id.etSignUpUsername);
        etPassword = findViewById(R.id.etSignUpPassword);
        etConfirm  = findViewById(R.id.etSignUpConfirm);
        btnSignUp  = findViewById(R.id.btnSignUp);
        tvMessage  = findViewById(R.id.tvSignUpMessage);
        tvGoLogin  = findViewById(R.id.tvGoLogin);

        btnSignUp.setOnClickListener(v -> handleSignUp());

        tvGoLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LogActivity.class));
            finish();
        });
    }

    private void handleSignUp() {
        String user    = etUsername.getText().toString().trim();
        String pass    = etPassword.getText().toString();
        String confirm = etConfirm.getText().toString();
        hideKeyboard();
        if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            tvMessage.setText("Please fill in all fields");
        } else if (pass.length() < 4) {
            tvMessage.setText("Password must be at least 4 characters");
        } else if (!pass.equals(confirm)) {
            tvMessage.setText("Passwords do not match");
        } else {

            session.createAccount(user, pass);
            System.out.println("CREAAAAATED");// 1. save the account
            session.saveSession(user);           // 2. log the user in directly
            goToSignIn(user,pass);                      // 3. move on
        }
    }

    private void goToSignIn(String user, String pass) {
        Intent intent = new Intent(this, LogActivity.class);
        intent.putExtra("username", user);
        intent.putExtra("password", pass);
        startActivity(intent);
        finish();
    }
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();   // also removes the cursor from the EditText
        }
    }
}