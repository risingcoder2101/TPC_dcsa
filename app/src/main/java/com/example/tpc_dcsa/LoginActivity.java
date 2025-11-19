package com.example.tpc_dcsa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Login Activity - First screen of the app
 * Allows users to login with username and password
 * Default credentials: admin/admin123 or member/member123
 */
public class LoginActivity extends AppCompatActivity {

    // UI Components
    private EditText etUsername, etPassword;
    private CheckBox cbRememberMe;
    private Button btnLogin;

    // Firebase Authentication instance
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Find UI components from layout
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        cbRememberMe = findViewById(R.id.cb_remember_me);
        btnLogin = findViewById(R.id.btn_login);

        // Set login button click listener
        btnLogin.setOnClickListener(v -> performLogin());
    }

    /**
     * Perform login authentication with Firebase
     * 1. Get email and password from input fields
     * 2. Check if both fields are filled
     * 3. Use FirebaseAuth to sign in with email and password
     * 4. Navigate to dashboard if successful
     */
    private void performLogin() {
        // Get input values and remove leading/trailing spaces
        String email = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validate that both fields are filled
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Authenticate with Firebase
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful - navigate to dashboard
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);

                        // Close login screen so user can't go back
                        finish();
                    } else {
                        // Authentication failed - show error message
                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
