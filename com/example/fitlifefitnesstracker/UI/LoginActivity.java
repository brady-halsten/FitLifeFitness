package com.example.fitlifefitnesstracker.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitlifefitnesstracker.R;

import database.Repository;
import entities.User;

public class LoginActivity extends AppCompatActivity {
    private static EditText editTextEmail;
    private static EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewNewUser;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewNewUser = findViewById(R.id.newUserTextView);

        repository = new Repository(getApplication());

        // Set onClickListener for login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve email and password
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (validateInputs(email, password)) {
                    loginUser(email, password);
                }
            }
        });

        textViewNewUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Registration.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public static boolean validateInputs(@NonNull String email, @NonNull String password) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.isEmpty()) {
            editTextEmail.setError("Empty email address");
            return false;
        }
        if (!email.trim().matches(emailPattern)) {
            editTextEmail.setError("Invalid email address");
            return false;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Empty password");
            return false;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Password too short");
            return false;
        }

        return true;
    }

    private void loginUser(String email, String password) {
        User user = repository.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, Interface.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_LONG).show();
        }
    }
}
