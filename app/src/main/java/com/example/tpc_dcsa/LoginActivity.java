package com.example.tpc_dcsa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpc_dcsa.database.DatabaseHelper;

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

    // Database helper for user authentication
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Find UI components from layout
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        cbRememberMe = findViewById(R.id.cb_remember_me);
        btnLogin = findViewById(R.id.btn_login);

        // Set login button click listener
        btnLogin.setOnClickListener(v -> performLogin());
    }

    /**
     * Perform login authentication
     * 1. Get username and password from input fields
     * 2. Check if both fields are filled
     * 3. Query database for matching username
     * 4. Verify password matches
     * 5. Navigate to dashboard if successful
     */
    private void performLogin() {
        // Get input values and remove leading/trailing spaces
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validate that both fields are filled
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Open database for reading
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query users table for matching username
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,           // Table name
                null,                                 // All columns
                DatabaseHelper.COL_USER_USERNAME + "=?",  // WHERE clause
                new String[]{username},               // WHERE arguments
                null,                                 // GROUP BY
                null,                                 // HAVING
                null                                  // ORDER BY
        );

        // Check if user exists
        if (cursor.moveToFirst()) {
            // Get stored password and role from database
            String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_PASSWORD));
            String role = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_ROLE));

            // Verify password matches
            if (storedPassword.equals(password)) {
                // Login successful - show message
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                // Navigate to dashboard with user information
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("role", role);
                startActivity(intent);

                // Close login screen so user can't go back
                finish();
            } else {
                // Password doesn't match
                Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Username not found in database
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
        }

        // Clean up - close cursor and database
        cursor.close();
        db.close();
    }
}

