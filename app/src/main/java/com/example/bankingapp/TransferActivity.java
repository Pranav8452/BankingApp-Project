package com.example.bankingapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TransferActivity extends AppCompatActivity {

    private EditText toEmailInput, amountInput;
    private Button btnsend;
    private AppDatabase db;
    private String fromEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        // Initialize Room Database
        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "banking_db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        // Get sender email from Intent
        fromEmail = getIntent().getStringExtra("email");

        // Link UI elements
        toEmailInput = findViewById(R.id.etRecipientEmail);
        amountInput = findViewById(R.id.etAmount);
        btnsend = findViewById(R.id.btnSend);

        // On Send Button Click
        btnsend.setOnClickListener(v -> handleTransfer());
    }

    private void handleTransfer() {
        String toEmail = toEmailInput.getText().toString().trim();
        String amountStr = amountInput.getText().toString().trim();

        if (toEmail.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (amount <= 0) {
            Toast.makeText(this, "Enter valid amount", Toast.LENGTH_SHORT).show();
            return;
        }

        User sender = db.userDao().getUserByEmail(fromEmail);
        User receiver = db.userDao().getUserByEmail(toEmail);

        if (sender == null) {
            Toast.makeText(this, "Sender not found", Toast.LENGTH_SHORT).show();
            return;
        }

        if (receiver == null) {
            Toast.makeText(this, "Receiver not found", Toast.LENGTH_SHORT).show();
            return;
        }

        if (sender.getBalance() < amount) {
            Toast.makeText(this, "Insufficient balance", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update balances
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        db.userDao().updateUser(sender);
        db.userDao().updateUser(receiver);

        // Record transaction
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        Transaction transaction = new Transaction(fromEmail, toEmail, amount, timestamp);
        db.transactionDao().insertTransaction(transaction);

        Toast.makeText(this, "Transfer Successful", Toast.LENGTH_SHORT).show();
        finish();
    }
}
