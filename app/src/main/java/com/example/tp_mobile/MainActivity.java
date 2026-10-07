package com.example.tp_mobile;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "QuizLifecycle";
    private static final String KEY_INDEX = "currentIndex";
    private static final String KEY_ANSWERS = "answers";

    // Views
    private TextView tvQuestion, tvProgress;
    private RadioGroup rgAnswers;
    private final RadioButton[] radioButtons = new RadioButton[3];
    private ProgressBar progressBar;
    private Button btnSubmit, btnPrevious, btnNext, btnRestart;

    // Data and state
    private final List<Question> questions = new ArrayList<>();
    private int currentIndex = 0;   // which question is on screen
    private int[] answers;          // chosen option per question, -1 = unanswered

    // ---------------- LIFECYCLE ----------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        linkViews();
        loadQuestions();

        // Fresh array: every question unanswered
        answers = new int[questions.size()];
        Arrays.fill(answers, -1);

        // savedInstanceState is null on first launch,
        // and holds our saved data after rotation
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            int[] saved = savedInstanceState.getIntArray(KEY_ANSWERS);
            if (saved != null && saved.length == answers.length) {
                answers = saved;
            }
        }

        progressBar.setMax(questions.size());

        btnSubmit.setOnClickListener(v -> submitAnswer());
        btnNext.setOnClickListener(v -> goTo(currentIndex + 1));
        btnPrevious.setOnClickListener(v -> goTo(currentIndex - 1));
        btnRestart.setOnClickListener(v -> restart());

        showQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState); // always call super
        Log.d(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, currentIndex);
        outState.putIntArray(KEY_ANSWERS, answers);
    }

    @Override protected void onStart()   { super.onStart();   Log.d(TAG, "onStart"); }
    @Override protected void onResume()  { super.onResume();  Log.d(TAG, "onResume"); }
    @Override protected void onPause()   { super.onPause();   Log.d(TAG, "onPause"); }
    @Override protected void onStop()    { super.onStop();    Log.d(TAG, "onStop"); }
    @Override protected void onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy"); }

    // ---------------- SETUP ----------------

    private void linkViews() {
        tvQuestion = findViewById(R.id.tvQuestion);
        tvProgress = findViewById(R.id.tvProgress);
        rgAnswers = findViewById(R.id.rgAnswers);
        radioButtons[0] = findViewById(R.id.rbOption1);
        radioButtons[1] = findViewById(R.id.rbOption2);
        radioButtons[2] = findViewById(R.id.rbOption3);
        progressBar = findViewById(R.id.progressBar);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        btnRestart = findViewById(R.id.btnRestart);
    }

    private void loadQuestions() {
        questions.add(new Question("What is the capital of Tunisia?",
                new String[]{"Tunis", "Sfax", "Sousse"}, 0));
        questions.add(new Question("How many pillars does Islam have?",
                new String[]{"Four", "Five", "Six"}, 1));
        questions.add(new Question("Which surah is called the heart of the Quran?",
                new String[]{"Al-Fatiha", "Yasin", "Al-Ikhlas"}, 1));
    }

    // ---------------- QUIZ LOGIC ----------------

    // Draws the current question. Reads answers[] but never changes it.
    private void showQuestion() {
        Question q = questions.get(currentIndex);
        tvQuestion.setText(q.getText());

        rgAnswers.clearCheck();
        for (int i = 0; i < 3; i++) {
            radioButtons[i].setText(q.getOptions()[i]);
        }

        // Already answered? Show the choice and lock it.
        int saved = answers[currentIndex];
        boolean answered = saved != -1;
        if (answered) {
            radioButtons[saved].setChecked(true);
        }
        for (RadioButton rb : radioButtons) {
            rb.setEnabled(!answered);
        }
        btnSubmit.setEnabled(!answered);

        // Disable navigation at the edges
        btnPrevious.setEnabled(currentIndex > 0);
        btnNext.setEnabled(currentIndex < questions.size() - 1);

        updateProgress();
    }

    // Previous / Next call this. Note: it only moves currentIndex.
    private void goTo(int newIndex) {
        if (newIndex < 0 || newIndex >= questions.size()) return;
        currentIndex = newIndex;
        showQuestion();
    }

    // The ONLY place that records an answer
    private void submitAnswer() {
        int checkedId = rgAnswers.getCheckedRadioButtonId();
        if (checkedId == -1) {
            Toast.makeText(this, "Please choose an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        int selected = -1;
        for (int i = 0; i < 3; i++) {
            if (radioButtons[i].getId() == checkedId) selected = i;
        }

        answers[currentIndex] = selected;

        boolean correct = selected == questions.get(currentIndex).getCorrectIndex();
        Toast.makeText(this, correct ? "Correct!" : "Wrong", Toast.LENGTH_SHORT).show();

        showQuestion(); // redraw: locks this question and updates the bar
    }

    private void restart() {
        Arrays.fill(answers, -1);
        currentIndex = 0;
        showQuestion();
    }

    // ---------------- HELPERS ----------------

    private int countAnswered() {
        int count = 0;
        for (int a : answers) {
            if (a != -1) count++;
        }
        return count;
    }

    private int computeScore() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (answers[i] == questions.get(i).getCorrectIndex()) score++;
        }
        return score;
    }

    private void updateProgress() {
        int answered = countAnswered();
        progressBar.setProgress(answered);

        if (answered == questions.size()) {
            tvProgress.setText("Completed! Score: " + computeScore() + " / " + questions.size());
        } else {
            tvProgress.setText("Question " + (currentIndex + 1) + " of " + questions.size()
                    + "  (answered: " + answered + ")");
        }
    }
}