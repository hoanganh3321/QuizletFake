package com.example.quizletfake.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName= "account")
public class Account {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "accountID")
    private int accountID;
    @NotNull
    @ColumnInfo(name = "accountName")
    private String accountName;
    @NotNull
    @ColumnInfo(name = "accountPassword")
    private String accountPassword;
    @ColumnInfo(name = "accountEmail")
    private String accountEmail;
    @ColumnInfo(name = "accountDOB")
    private String accountDOB;

    public Account() {
    }

    public Account(int accountID, @NotNull String accountName, @NotNull String accountPassword, @NotNull String accountEmail, @NotNull String accountDOB) {
        this.accountID = accountID;
        this.accountName = accountName;
        this.accountPassword = accountPassword;
        this.accountEmail = accountEmail;
        this.accountDOB = accountDOB;
    }
    public Account( @NotNull String accountName, @NotNull String accountPassword, @NotNull String accountEmail, @NotNull String accountDOB) {

        this.accountName = accountName;
        this.accountPassword = accountPassword;
        this.accountEmail = accountEmail;
        this.accountDOB = accountDOB;
    }


    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    @NotNull
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(@NotNull String accountName) {
        this.accountName = accountName;
    }

    @NotNull
    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(@NotNull String accountPassword) {
        this.accountPassword = accountPassword;
    }

    @NotNull
    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(@NotNull String accountEmail) {
        this.accountEmail = accountEmail;
    }

    @NotNull
    public String getAccountDOB() {
        return accountDOB;
    }

    public void setAccountDOB(@NotNull String accountDOB) {
        this.accountDOB = accountDOB;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountID=" + accountID +
                ", accountName='" + accountName + '\'' +
                ", accountPassword='" + accountPassword + '\'' +
                ", accountEmail='" + accountEmail + '\'' +
                ", accountDOB='" + accountDOB + '\'' +
                '}';
    }
}
