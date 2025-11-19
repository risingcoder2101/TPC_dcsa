package com.example.tpc_dcsa;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpc_dcsa.model.Student;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StudentsActivity extends AppCompatActivity {
    private LinearLayout studentsContainer;
    private EditText etSearch;
    private FirebaseFirestore firestore;
    private ListenerRegistration studentsListener;
    private final List<DocumentSnapshot> allStudents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        Button btnBack = findViewById(R.id.btn_back);
        Button btnAddStudent = findViewById(R.id.btn_add_student);
        etSearch = findViewById(R.id.et_search);
        studentsContainer = findViewById(R.id.students_list_container);

        btnBack.setOnClickListener(v -> finish());
        btnAddStudent.setOnClickListener(v -> startActivity(new android.content.Intent(StudentsActivity.this, AddStudentActivity.class)));

        firestore = FirebaseFirestore.getInstance();
        subscribeStudents();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { renderFiltered(s.toString()); }
        });
    }

    private void subscribeStudents() {
        if (studentsListener != null) studentsListener.remove();
        studentsListener = firestore.collection("students").addSnapshotListener((QuerySnapshot value, FirebaseFirestoreException error) -> {
            if (error != null) {
                Toast.makeText(StudentsActivity.this, "Failed to load students: " + error.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
            allStudents.clear();
            if (value != null) allStudents.addAll(value.getDocuments());
            renderFiltered(etSearch.getText().toString());
        });
    }

    private void renderFiltered(String query) {
        studentsContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        String q = query.trim().toLowerCase(Locale.getDefault());
        for (DocumentSnapshot doc : allStudents) {
            String name = safe(doc.getString("name"));
            String email = safe(doc.getString("email"));
            String batch = safe(doc.getString("batch"));
            if (!q.isEmpty() && !(name.toLowerCase(Locale.getDefault()).contains(q) || email.toLowerCase(Locale.getDefault()).contains(q) || batch.toLowerCase(Locale.getDefault()).contains(q))) {
                continue;
            }
            View item = inflater.inflate(R.layout.item_student, studentsContainer, false);
            TextView tvName = item.findViewById(R.id.tv_student_name);
            TextView tvEmail = item.findViewById(R.id.tv_student_email);
            TextView tvBatch = item.findViewById(R.id.tv_student_batch);
            ImageButton btnEdit = item.findViewById(R.id.btn_edit_student);
            ImageButton btnDelete = item.findViewById(R.id.btn_delete_student);

            tvName.setText(name);
            tvEmail.setText("📧 " + email);
            tvBatch.setText(batch);

            String docId = doc.getId();
            btnEdit.setOnClickListener(v -> showEditDialog(docId, doc));
            btnDelete.setOnClickListener(v -> confirmDelete(docId, item));

            studentsContainer.addView(item);
        }
        if (studentsContainer.getChildCount() == 0) {
            TextView empty = new TextView(this);
            empty.setText("No students found");
            empty.setPadding(32,32,32,32);
            studentsContainer.addView(empty);
        }
    }

    private void showEditDialog(String docId, DocumentSnapshot doc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_student, null, false);
        EditText etName = dialogView.findViewById(R.id.et_edit_name);
        EditText etEmail = dialogView.findViewById(R.id.et_edit_email);
        EditText etBatch = dialogView.findViewById(R.id.et_edit_batch);
        EditText etPercentage = dialogView.findViewById(R.id.et_edit_percentage);

        etName.setText(safe(doc.getString("name")));
        etEmail.setText(safe(doc.getString("email")));
        etBatch.setText(safe(doc.getString("batch")));
        etPercentage.setText(safe(doc.getString("percentage")));

        builder.setTitle("Edit Student")
                .setView(dialogView)
                .setNegativeButton("Cancel", (d,w)-> d.dismiss())
                .setPositiveButton("Save", (d,w)-> {
                    firestore.collection("students").document(docId).update(
                            "name", etName.getText().toString().trim(),
                            "email", etEmail.getText().toString().trim(),
                            "batch", etBatch.getText().toString().trim(),
                            "percentage", etPercentage.getText().toString().trim()
                    ).addOnSuccessListener(a-> Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show())
                     .addOnFailureListener(e-> Toast.makeText(this, "Failed: "+e.getMessage(), Toast.LENGTH_LONG).show());
                }).show();
    }

    private void confirmDelete(String docId, View item) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Student")
                .setMessage("Are you sure you want to delete this student?")
                .setNegativeButton("Cancel", (d,w)-> d.dismiss())
                .setPositiveButton("Delete", (d,w)-> firestore.collection("students").document(docId).delete()
                        .addOnSuccessListener(a-> Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e-> Toast.makeText(this, "Failed: "+e.getMessage(), Toast.LENGTH_LONG).show()))
                .show();
    }

    private String safe(String v) { return v == null ? "" : v; }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (studentsListener != null) studentsListener.remove();
    }
}
