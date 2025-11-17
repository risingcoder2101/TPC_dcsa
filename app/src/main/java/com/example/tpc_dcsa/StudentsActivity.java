package com.example.tpc_dcsa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class StudentsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        Button btnBack = findViewById(R.id.btn_back);
        Button btnAddStudent = findViewById(R.id.btn_add_student);

        btnBack.setOnClickListener(v -> finish());

        btnAddStudent.setOnClickListener(v -> {
            Toast.makeText(this, "Add Student functionality coming soon!", Toast.LENGTH_SHORT).show();
        });
    }
}

