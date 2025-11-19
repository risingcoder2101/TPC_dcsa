package com.example.tpc_dcsa;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tpc_dcsa.model.Company;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class AddCompanyActivity extends AppCompatActivity {
    private EditText etName, etLocation, etRoles, etPackage, etDriveDate;
    private RadioGroup rgStatus;
    private Button btnSubmit;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        // Toolbar support
        Toolbar toolbar = findViewById(R.id.toolbar_company);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        etName = findViewById(R.id.et_name);
        etLocation = findViewById(R.id.et_location);
        etRoles = findViewById(R.id.et_roles);
        etPackage = findViewById(R.id.et_package);
        etDriveDate = findViewById(R.id.et_drive_date);
        rgStatus = findViewById(R.id.rg_status);
        btnSubmit = findViewById(R.id.btn_submit);

        firestore = FirebaseFirestore.getInstance();

        // Setup DatePicker for Drive Date
        etDriveDate.setOnClickListener(v -> showDatePicker());

        btnSubmit.setOnClickListener(v -> submitCompany());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    etDriveDate.setText(date);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void submitCompany() {
        String name = etName.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String roles = etRoles.getText().toString().trim();
        String pkg = etPackage.getText().toString().trim();
        String driveDate = etDriveDate.getText().toString().trim();

        // Get status from RadioGroup
        int selectedId = rgStatus.getCheckedRadioButtonId();
        final String status;
        if (selectedId == R.id.rb_completed) {
            status = "Completed";
        } else if (selectedId == R.id.rb_upcoming) {
            status = "Upcoming";
        } else {
            status = "";
        }

        if (name.isEmpty() || location.isEmpty() || roles.isEmpty() || pkg.isEmpty() || driveDate.isEmpty() || status.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prevent double clicks
        btnSubmit.setEnabled(false);

        // Check for duplicates by name+location
        firestore.collection("companies")
                .whereEqualTo("name", name)
                .whereEqualTo("location", location)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        Toast.makeText(AddCompanyActivity.this, "Company already exists.", Toast.LENGTH_SHORT).show();
                        btnSubmit.setEnabled(true);
                        return;
                    }

                    // Add new company
                    Company company = new Company(name, location, roles, pkg, driveDate, status);
                    firestore.collection("companies").add(company)
                            .addOnSuccessListener(docRef -> {
                                Toast.makeText(AddCompanyActivity.this, "Company added successfully", Toast.LENGTH_SHORT).show();
                                // Close activity and return to previous screen
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(AddCompanyActivity.this, "Failed to add company: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                btnSubmit.setEnabled(true);
                            });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddCompanyActivity.this, "Error checking duplicates: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    btnSubmit.setEnabled(true);
                });
    }
}
