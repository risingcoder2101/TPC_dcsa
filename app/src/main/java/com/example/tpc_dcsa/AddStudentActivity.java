package com.example.tpc_dcsa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tpc_dcsa.model.Student;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AddStudentActivity extends AppCompatActivity {
    private EditText etName, etRollNumber, etGender, etBatch, etEmail, etPhone, etCampus, etPercentage;
    private Button btnSubmit, btnImportExcel;
    private FirebaseFirestore firestore;
    private ActivityResultLauncher<Intent> excelPickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        etName = findViewById(R.id.et_name);
        etRollNumber = findViewById(R.id.et_roll_number);
        etGender = findViewById(R.id.et_gender);
        etBatch = findViewById(R.id.et_batch);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etCampus = findViewById(R.id.et_campus);
        etPercentage = findViewById(R.id.et_percentage);
        btnSubmit = findViewById(R.id.btn_submit);
        btnImportExcel = findViewById(R.id.btn_import_excel);

        firestore = FirebaseFirestore.getInstance();
        excelPickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    importStudentsFromExcelFile(uri);
                }
            }
        );

        btnSubmit.setOnClickListener(v -> submitStudent());
        btnImportExcel.setOnClickListener(v -> pickExcelFile());
    }

    private void submitStudent() {
        String name = etName.getText().toString().trim();
        String rollNumber = etRollNumber.getText().toString().trim();
        String gender = etGender.getText().toString().trim();
        String batch = etBatch.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String campus = etCampus.getText().toString().trim();
        String percentage = etPercentage.getText().toString().trim();

        if (name.isEmpty() || rollNumber.isEmpty() || gender.isEmpty() || batch.isEmpty() || email.isEmpty() || phone.isEmpty() || campus.isEmpty() || percentage.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        addStudentToFirestore(name, rollNumber, gender, batch, email, phone, campus, percentage);
    }

    private void pickExcelFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        excelPickerLauncher.launch(intent);
    }

    private void importStudentsFromExcelFile(Uri uri) {
        try {
            List<Student> students = ExcelImporter.importStudentsFromUri(this, uri);
            for (Student s : students) {
                addStudentToFirestore(s.getName(), s.getRollNumber(), s.getGender(), s.getBatch(), s.getEmail(), s.getPhone(), s.getCampus(), s.getPercentage());
            }
            Toast.makeText(this, "Students imported successfully.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to import: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void addStudentToFirestore(String name, String rollNumber, String gender, String batch, String email, String phone, String campus, String percentage) {
        Student student = new Student(name, rollNumber, gender, batch, email, phone, campus, percentage);
        firestore.collection("students").add(student)
                .addOnSuccessListener(docRef -> {
                    Toast.makeText(AddStudentActivity.this, "Student added", Toast.LENGTH_SHORT).show();
                    clearInputs();
                })
                .addOnFailureListener(e -> Toast.makeText(AddStudentActivity.this, "Failed to add student: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void clearInputs() {
        etName.setText("");
        etRollNumber.setText("");
        etGender.setText("");
        etBatch.setText("");
        etEmail.setText("");
        etPhone.setText("");
        etCampus.setText("");
        etPercentage.setText("");
    }
}
