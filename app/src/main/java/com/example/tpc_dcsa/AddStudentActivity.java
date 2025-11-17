package com.example.tpc_dcsa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddStudentActivity extends AppCompatActivity {
    private EditText etName, etEmail, etBranch, etYear;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etBranch = findViewById(R.id.et_branch);
        etYear = findViewById(R.id.et_year);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(v -> submitStudent());
    }

    private void submitStudent() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String branch = etBranch.getText().toString().trim();
        String year = etYear.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || branch.isEmpty() || year.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Call method to send data to Google Sheets
        // sendStudentToGoogleSheets(name, email, branch, year);
    }
}
