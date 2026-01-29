package com.example.bankingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class LoginActivity extends AppCompatActivity {
    EditText emailInput, passwordInput;
    Button loginBtn, registerRedirectBtn;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "banking_db")
                .allowMainThreadQueries()
                .build();

        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        registerRedirectBtn = findViewById(R.id.registerRedirect);

        loginBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = db.userDao().getUserByEmailAndPassword(email, password);
            if (user != null) {
                Log.d("LoginActivity", "Sending email: " + email);
                Intent intent = new Intent(this, DashboardActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        registerRedirectBtn.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );
    }
}
