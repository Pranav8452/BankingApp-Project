package com.example.bankingapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String fromEmail;
    private String toEmail;
    private int amount;
    private String timestamp;

    public Transaction(String fromEmail, String toEmail, int amount, String timestamp) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public int getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // Setters (Room needs this sometimes)
    public void setId(int id) {
        this.id = id;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
