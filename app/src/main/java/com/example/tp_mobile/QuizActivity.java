package com.example.tp_mobile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private LinearLayout quizContainer;

    // Stores the RadioGroup for every question
    private ArrayList<RadioGroup> answerGroups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizContainer = findViewById(R.id.quizContainer);

        // Receive quiz name
        String quizName = getIntent().getStringExtra("QUIZ_NAME");

        if (quizName == null) {
            Toast.makeText(this, "Quiz not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Find quiz index
        int quizIndex = findQuizIndex(quizName);

        if (quizIndex == -1) {
            Toast.makeText(this, "Quiz not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Get questions
        String[][] currentQuestions = QuizData.QUIZZES[quizIndex];
        renderQuiz(currentQuestions);
    }

    private int findQuizIndex(String quizName) {

        for (int i = 0; i < QuizData.QUIZ_NAMES.length; i++) {

            if (QuizData.QUIZ_NAMES[i].equals(quizName)) {
                return i;
            }
        }

        return -1;
    }

    private void renderQuiz(String[][] questions) {

        for (int i = 0; i < questions.length; i++) {

            String question = questions[i][0];
            String option1 = questions[i][1];
            String option2 = questions[i][2];

            // Question
            TextView questionText = new TextView(this);

            questionText.setText(
                    (i + 1) + ". " + question
            );

            questionText.setTextSize(18);
            questionText.setPadding(0, 24, 0, 12);

            quizContainer.addView(questionText);

            // Radio group for this question
            RadioGroup optionsGroup = new RadioGroup(this);

            RadioButton optionButton1 = new RadioButton(this);
            optionButton1.setText(option1);

            RadioButton optionButton2 = new RadioButton(this);
            optionButton2.setText(option2);

            optionsGroup.addView(optionButton1);
            optionsGroup.addView(optionButton2);

            quizContainer.addView(optionsGroup);

            // Save this group
            answerGroups.add(optionsGroup);
        }

        // Submit button
        Button submitButton = new Button(this);
        submitButton.setText("Submit");

        quizContainer.addView(submitButton);

        // Submit click
        submitButton.setOnClickListener(v -> {

            int[] answers = getAnswers();

            OnSubmit(answers,questions);
        });
    }

    private int[] getAnswers() {

        int[] answers = new int[answerGroups.size()];

        for (int i = 0; i < answerGroups.size(); i++) {

            RadioGroup group = answerGroups.get(i);

            int selectedId = group.getCheckedRadioButtonId();

            if (selectedId == -1) {
                // Nothing selected
                answers[i] = -1;
            } else {

                RadioButton selectedButton =
                        findViewById(selectedId);

                int selectedIndex = group.indexOfChild(selectedButton);

                answers[i] = selectedIndex;
            }
        }

        return answers;
    }

    private void OnSubmit(int[] answers, String[][] currentQuestions) {

        int score = 0;

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < answers.length; i++) {

            int correctAnswer =
                    Integer.parseInt(currentQuestions[i][3]);

            if (answers[i] == correctAnswer) {
                score++;

                result.append("Question ")
                        .append(i + 1)
                        .append(": Correct ✓\n");
            } else {
                result.append("Question ")
                        .append(i + 1)
                        .append(": Wrong ✗\n");

                if (answers[i] == -1) {
                    result.append("  No answer selected\n");
                } else {
                    result.append("  Your answer: ")
                            .append(currentQuestions[i][answers[i] + 1])
                            .append("\n");
                }

                result.append("  Correct answer: ")
                        .append(currentQuestions[i][correctAnswer + 1])
                        .append("\n");
            }

            result.append("\n");
        }

        new android.app.AlertDialog.Builder(this)
                .setTitle("Quiz Result")
                .setMessage(
                        "Score: " + score + "/" + answers.length +
                                "\n\n" +
                                result.toString()
                )
                .setPositiveButton("OK", null)
                .show();
    }
}

