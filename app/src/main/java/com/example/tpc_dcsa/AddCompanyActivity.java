package com.example.tpc_dcsa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tpc_dcsa.model.Company;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddCompanyActivity extends AppCompatActivity {
    private EditText etName, etLocation, etRoles, etPackage, etDriveDate, etStatus;
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
        etStatus = findViewById(R.id.et_status);
        btnSubmit = findViewById(R.id.btn_submit);

        firestore = FirebaseFirestore.getInstance();

        btnSubmit.setOnClickListener(v -> submitCompany());
    }

    private void submitCompany() {
        String name = etName.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String roles = etRoles.getText().toString().trim();
        String pkg = etPackage.getText().toString().trim();
        String driveDate = etDriveDate.getText().toString().trim();
        String status = etStatus.getText().toString().trim();

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
                                Toast.makeText(AddCompanyActivity.this, "Company added", Toast.LENGTH_SHORT).show();
                                clearInputs();
                                btnSubmit.setEnabled(true);
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

    private void clearInputs() {
        etName.setText("");
        etLocation.setText("");
        etRoles.setText("");
        etPackage.setText("");
        etDriveDate.setText("");
        etStatus.setText("");
    }
}
