package com.example.quizletfake.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StudySessionPrefs {
    private static final String PREF_NAME = "study_session_prefs";
    private static final String KEY_HISTORY = "study_session_history";

    public static void saveSession(Context context, StudySession session) {
        List<StudySession> history = getHistory(context);
        history.add(session);
        saveHistory(context, history);
    }

    public static List<StudySession> getHistory(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_HISTORY, null);
        if (json == null) return new ArrayList<>();
        Type type = new TypeToken<List<StudySession>>(){}.getType();
        return new Gson().fromJson(json, type);
    }

    public static void saveHistory(Context context, List<StudySession> history) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = new Gson().toJson(history);
        prefs.edit().putString(KEY_HISTORY, json).apply();
    }

    public static void clearHistory(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_HISTORY).apply();
    }
} 