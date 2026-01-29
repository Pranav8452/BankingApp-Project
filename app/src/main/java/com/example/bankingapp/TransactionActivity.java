package com.example.bankingapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    TextView noTransactionsText;
    ListView transactionListView;

    AppDatabase db;
    String userEmail;
    ArrayList<String> transactionDetails;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        noTransactionsText = findViewById(R.id.noTransactionsText);
        transactionListView = findViewById(R.id.transactionListView);

        db = AppDatabase.getInstance(this);

        // ✅ Get logged-in user email from intent
        userEmail = getIntent().getStringExtra("email");

        transactionDetails = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, transactionDetails);
        transactionListView.setAdapter(adapter);

        loadTransactions();
    }

    private void loadTransactions() {
        // ✅ Get transactions where user is sender OR receiver
        List<Transaction> transactions = db.transactionDao()
                .getTransactionsForUser(userEmail);

        if (transactions.isEmpty()) {
            noTransactionsText.setVisibility(TextView.VISIBLE);
            transactionListView.setVisibility(ListView.GONE);
        } else {
            noTransactionsText.setVisibility(TextView.GONE);
            transactionListView.setVisibility(ListView.VISIBLE);

            for (Transaction t : transactions) {
                String type = t.getFromEmail().equals(userEmail) ? "Sent to" : "Received from";
                String otherParty = t.getFromEmail().equals(userEmail) ? t.getToEmail() : t.getFromEmail();
                String detail = type + " " + otherParty + "\nAmount: ₹" + t.getAmount() + "\nDate: " + t.getTimestamp();
                transactionDetails.add(detail);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
