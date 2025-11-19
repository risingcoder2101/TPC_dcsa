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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;

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
        try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header
                String name = getCellString(row, 0);
                String rollNumber = getCellString(row, 1);
                String gender = getCellString(row, 2);
                String batch = getCellString(row, 3);
                String email = getCellString(row, 4);
                String phone = getCellString(row, 5);
                String campus = getCellString(row, 6);
                String percentage = getCellString(row, 7);
                if (name.isEmpty()) continue;
                addStudentToFirestore(name, rollNumber, gender, batch, email, phone, campus, percentage);
            }
            Toast.makeText(this, "Students imported successfully.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to import: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String getCellString(Row row, int col) {
        Cell cell = row.getCell(col);
        return cell == null ? "" : cell.toString().trim();
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
