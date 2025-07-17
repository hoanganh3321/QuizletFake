package com.example.quizletfake.model;

import java.util.Date;

public class StudySession {
    private int id;
    private int accountId;
    private int quizId;
    private Date date;
    private int correctCount;
    private int totalCount;
    private long durationMillis;

    public StudySession(int id, int accountId, int quizId, Date date, int correctCount, int totalCount, long durationMillis) {
        this.id = id;
        this.accountId = accountId;
        this.quizId = quizId;
        this.date = date;
        this.correctCount = correctCount;
        this.totalCount = totalCount;
        this.durationMillis = durationMillis;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public int getQuizId() { return quizId; }
    public void setQuizId(int quizId) { this.quizId = quizId; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public int getCorrectCount() { return correctCount; }
    public void setCorrectCount(int correctCount) { this.correctCount = correctCount; }

    public int getTotalCount() { return totalCount; }
    public void setTotalCount(int totalCount) { this.totalCount = totalCount; }

    public long getDurationMillis() { return durationMillis; }
    public void setDurationMillis(long durationMillis) { this.durationMillis = durationMillis; }
} 