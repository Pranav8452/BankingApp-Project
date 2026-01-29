package com.example.bankingapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String email;
    private String password;
    private double balance;
    private String registrationDate;

    // ✅ Main constructor (used by Room)
    public User(String name, String email, String password, double balance, String registrationDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.registrationDate = registrationDate;
    }

    // ✅ Extra constructor (ignored by Room, used only in RegisterActivity)
    @Ignore
    public User(String name, String email, String password, String registrationDate) {
        this(name, email, password, 0.0, registrationDate);
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(String registrationDate) { this.registrationDate = registrationDate; }
}
