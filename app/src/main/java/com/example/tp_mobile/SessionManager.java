package com.example.tp_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import java.security.MessageDigest;

public class SessionManager {

    private static final String PREFS = "quiz_prefs";
    private static final String KEY_ACCOUNT_USER = "account_user";
    private static final String KEY_ACCOUNT_HASH = "account_hash";
    private static final String KEY_LOGGED_IN = "logged_in_user";

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    // ---- Account (created once, on sign up) ----
    public void setsessionuser(String user){
       prefs.edit().putString(KEY_LOGGED_IN,user).apply();
    }
    public boolean hasAccount() {
        return prefs.getString(KEY_ACCOUNT_USER, null) != null;
    }

    public void createAccount(String user, String password) {
        prefs.edit()
                .putString(KEY_ACCOUNT_USER, user)
                .putString(KEY_ACCOUNT_HASH, hash(password))
                .apply();
    }

    public boolean checkCredentials(String user, String password) {
        String savedUser = prefs.getString(KEY_ACCOUNT_USER, null);
        String savedHash = prefs.getString(KEY_ACCOUNT_HASH, null);
        return user.equals(savedUser) && hash(password).equals(savedHash);
    }

    // ---- Session (who is logged in right now) ----

    public void saveSession(String user) {
        prefs.edit().putString(KEY_LOGGED_IN, user).apply();
    }

    public String getSessionUser() {
        return prefs.getString(KEY_LOGGED_IN, null);
    }

    public void logout() {
        // Remove only the session. The account stays so he can log in again.
        prefs.edit().remove(KEY_LOGGED_IN).apply();
    }

    // ---- Helper ----

    private String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

