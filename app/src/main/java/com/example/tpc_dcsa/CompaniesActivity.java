package com.example.tpc_dcsa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CompaniesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);

        Button btnBack = findViewById(R.id.btn_back);
        Button btnAddCompany = findViewById(R.id.btn_add_company);

        btnBack.setOnClickListener(v -> finish());

        btnAddCompany.setOnClickListener(v -> {
            Toast.makeText(this, "Add Company functionality coming soon!", Toast.LENGTH_SHORT).show();
        });
    }
}

