package com.example.bankingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText nameInput, emailInput, passwordInput, registrationDateInput;
    Button registerBtn;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Views
        nameInput = findViewById(R.id.name);
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        registrationDateInput = findViewById(R.id.registerInput); // same EditText you already created
        registerBtn = findViewById(R.id.registerBtn);

        // Initialize Database
        db = AppDatabase.getInstance(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                String registrationDate = registrationDateInput.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || registrationDate.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create User object
                User user = new User(name, email, password, 1000.0, registrationDate);



                // Insert into DB (background thread)
                new Thread(() -> {
                    db.userDao().insert(user);
                    runOnUiThread(() -> {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        finish(); // Go back to login
                    });
                }).start();
            }
        });
    }
}
