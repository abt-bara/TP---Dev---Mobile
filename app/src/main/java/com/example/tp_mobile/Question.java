package com.example.tp_mobile;
public class Question {
    private final String text;        // the question itself
    private final String[] options;   // the 3 answers
    private final int correctIndex;   // 0, 1, or 2

    public Question(String text, String[] options, int correctIndex) {
        this.text = text;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public String getText() { return text; }
    public String[] getOptions() { return options; }
    public int getCorrectIndex() { return correctIndex; }
}