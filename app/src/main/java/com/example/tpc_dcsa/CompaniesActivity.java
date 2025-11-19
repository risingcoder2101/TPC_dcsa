package com.example.tpc_dcsa;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

public class CompaniesActivity extends AppCompatActivity {
    private LinearLayout companyListContainer;
    private Button btnBack, btnAddCompany;
    private FirebaseFirestore firestore;
    private ListenerRegistration companiesListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);

        btnBack = findViewById(R.id.btn_back);
        btnAddCompany = findViewById(R.id.btn_add_company);
        companyListContainer = findViewById(R.id.company_list_container);

        btnBack.setOnClickListener(v -> finish());

        // Open AddCompanyActivity when add clicked
        btnAddCompany.setOnClickListener(v -> startActivity(new android.content.Intent(CompaniesActivity.this, AddCompanyActivity.class)));

        firestore = FirebaseFirestore.getInstance();
        subscribeCompanies();
    }

    private void subscribeCompanies() {
        if (companiesListener != null) companiesListener.remove();
        companiesListener = firestore.collection("companies").addSnapshotListener((QuerySnapshot value, FirebaseFirestoreException error) -> {
            if (error != null) {
                Toast.makeText(CompaniesActivity.this, "Failed to load companies: " + error.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
            companyListContainer.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(CompaniesActivity.this);
            if (value != null) {
                for (DocumentSnapshot doc : value.getDocuments()) {
                    View item = inflater.inflate(R.layout.item_company, companyListContainer, false);
                    TextView tvName = item.findViewById(R.id.tv_company_name);
                    TextView tvSector = item.findViewById(R.id.tv_company_sector);
                    TextView tvLocation = item.findViewById(R.id.tv_company_location);
                    ImageButton btnDelete = item.findViewById(R.id.btn_delete_company);

                    String docId = doc.getId();
                    item.setTag(docId);

                    tvName.setText(doc.getString("name") != null ? doc.getString("name") : "Unknown");
                    tvSector.setText(doc.getString("roles") != null ? doc.getString("roles") : "");
                    tvLocation.setText(doc.getString("location") != null ? doc.getString("location") : "");

                    btnDelete.setOnClickListener(v -> confirmAndDeleteCompany(docId, item, btnDelete));

                    companyListContainer.addView(item);
                }
            }
        });
    }

    private void confirmAndDeleteCompany(String docId, View itemView, ImageButton btn) {
        new AlertDialog.Builder(this)
                .setTitle("Delete company")
                .setMessage("Are you sure you want to delete this company? This action cannot be undone.")
                .setNegativeButton("Cancel", (d, w) -> d.dismiss())
                .setPositiveButton("Delete", (d, w) -> {
                    btn.setEnabled(false);
                    firestore.collection("companies").document(docId).delete()
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(CompaniesActivity.this, "Company deleted", Toast.LENGTH_SHORT).show();
                                companyListContainer.removeView(itemView);
                            })
                            .addOnFailureListener(e -> {
                                btn.setEnabled(true);
                                Toast.makeText(CompaniesActivity.this, "Failed to delete: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            });
                }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (companiesListener != null) companiesListener.remove();
    }
}
