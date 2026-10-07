package com.example.tp_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
public class LogActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;

    private Button btnSignUp;
    private TextView tvMessage;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        session = new SessionManager(this);   // 1. create it with a Context
        session.setsessionuser("");
        // 2. already logged in? skip this screen
        if (session.getSessionUser() != "") {
            goToMain();
            return;
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin   = findViewById(R.id.btnLogin);
        tvMessage  = findViewById(R.id.tvMessage);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(v -> HandleSingUp());

        btnLogin.setOnClickListener(v -> handleLogin());

    }

    private void handleLogin() {
        String user = etUsername.getText().toString().trim();
        String pass = etPassword.getText().toString();
        hideKeyboard();
        if (user.isEmpty() || pass.isEmpty()) {
            tvMessage.setText("Please fill in all fields");
        } else if (!session.hasAccount()) {
            tvMessage.setText("No account yet. Please sign up first");
        } else if (session.checkCredentials(user, pass)) {
            session.saveSession(user);   // 3. remember the login
            goToMain();
        } else {
            tvMessage.setText("Wrong username or password");
        }
    }

    private void goToMain() {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();   // so Back doesn't return to the login screen
    }
    private void HandleSingUp(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
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
