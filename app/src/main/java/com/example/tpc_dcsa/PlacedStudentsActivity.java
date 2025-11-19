package com.example.tpc_dcsa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlacedStudentsActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private ListenerRegistration placedListener;
    private LinearLayout placedContainer;
    private final List<DocumentSnapshot> placedDocs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_students);

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        // Try to find the container by id name (safer when R.id may not include it depending on resource generation)
        int placedContainerId = getResources().getIdentifier("placed_list_container", "id", getPackageName());
        if (placedContainerId != 0) {
            placedContainer = findViewById(placedContainerId);
        } else {
            placedContainer = null;
        }
        if (placedContainer == null) {
            Toast.makeText(this, "Layout error: container not found", Toast.LENGTH_LONG).show();
            return;
        }

        firestore = FirebaseFirestore.getInstance();
        subscribePlaced();
    }

    private void subscribePlaced() {
        if (placedListener != null) placedListener.remove();
        placedListener = firestore.collection("students").addSnapshotListener((QuerySnapshot value, FirebaseFirestoreException error) -> {
            if (error != null) {
                Toast.makeText(PlacedStudentsActivity.this, "Failed to load placed students: " + error.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
            placedDocs.clear();
            if (value != null) {
                for (DocumentSnapshot doc : value.getDocuments()) {
                    if (isPlaced(doc)) placedDocs.add(doc);
                }
            }
            renderPlaced();
        });
    }

    private boolean isPlaced(DocumentSnapshot doc) {
        Object placedObj = doc.get("placed");
        if (placedObj instanceof Boolean && (Boolean) placedObj) return true;
        Object companyObj = doc.get("company");
        if (companyObj != null && !companyObj.toString().trim().isEmpty()) return true;
        Object statusObj = doc.get("status");
        return statusObj != null && statusObj.toString().equalsIgnoreCase("placed");
    }

    private void renderPlaced() {
        if (placedContainer == null) return;
        placedContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (DocumentSnapshot doc : placedDocs) {
            View item = inflater.inflate(R.layout.item_placed_student, placedContainer, false);
            TextView tvName = item.findViewById(R.id.tv_placed_student_name);
            TextView tvBatch = item.findViewById(R.id.tv_placed_student_batch);
            TextView tvCompany = item.findViewById(R.id.tv_placed_company);
            TextView tvPackage = item.findViewById(R.id.tv_placed_package);
            TextView tvDate = item.findViewById(R.id.tv_placed_date);

            tvName.setText(safe(doc.getString("name")));
            tvBatch.setText(safe(doc.getString("batch")));
            tvCompany.setText(safe(doc.getString("company")));
            tvPackage.setText(safe(doc.getString("package")));

            // Date handling: support Timestamp or String
            Object placedOnObj = doc.get("placedOn");
            String dateStr = "";
            if (placedOnObj instanceof Timestamp) {
                Date d = ((Timestamp) placedOnObj).toDate();
                dateStr = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(d);
            } else if (placedOnObj != null) {
                dateStr = placedOnObj.toString();
            }
            tvDate.setText(dateStr);

            placedContainer.addView(item);
        }

        if (placedContainer.getChildCount() == 0) {
            TextView empty = new TextView(this);
            empty.setText("No placed students found");
            empty.setPadding(32,32,32,32);
            placedContainer.addView(empty);
        }
    }

    private String safe(String v) { return v == null ? "" : v; }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (placedListener != null) placedListener.remove();
    }
}
