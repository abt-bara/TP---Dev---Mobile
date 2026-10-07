package com.example.tp_mobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // No setContentView: this screen has no UI, it only redirects.

        SessionManager session = new SessionManager(this);
        String user = session.getSessionUser();

        Intent intent;
        if (user != null) {
            // Logged in -> Home, carrying the username in the Intent
            intent = new Intent(this, HomeActivity.class);
            intent.putExtra("username", user);
        } else if (session.hasAccount()) {
            intent = new Intent(this, LogActivity.class);
        } else {
            intent = new Intent(this, SignUpActivity.class);
        }

        startActivity(intent);
        finish();   // remove MainActivity so Back doesn't return to it
    }

}