package com.example.tp_mobile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GridLayout grid = findViewById(R.id.gridQuizzes);

        Button LogOutBtn = findViewById(R.id.btnLogout);
        LogOutBtn.setOnClickListener(v -> LogOut());
        String[] quizzes = QuizData.QUIZ_NAMES;

        for (int i = 0; i < quizzes.length; i++) {
            Button button = new Button(this);
            button.setText(quizzes[i]);
            String quizName = quizzes[i] ;

            // <================= Button Call =================>


            button.setOnClickListener(v -> openQuiz(quizName));
            grid.addView(button);
        }
    }
    private void openQuiz(String quizName) {

        Intent intent = new Intent(this, QuizActivity.class);

        intent.putExtra("QUIZ_NAME", quizName);

        startActivity(intent);
    }
    private void LogOut(){
        Intent intent = new Intent(this, LogActivity.class);
        intent.putExtra("user","");
        startActivity(intent);
    }
}
