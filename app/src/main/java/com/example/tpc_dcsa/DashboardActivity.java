package com.example.tpc_dcsa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Locale;

/**
 * Dashboard Activity - Main screen after login
 * Shows statistics, quick actions, and navigation sidebar
 */
public class DashboardActivity extends AppCompatActivity {

    // UI Components - Views from layout file
    private LinearLayout sidebar;
    private View overlay;
    private ImageView btnMenu;

    // Statistic TextViews
    private TextView tvTotalStudents, tvPlacedStudents, tvTotalCompanies, tvPlacementRate;

    // Firebase Firestore database instance
    private FirebaseFirestore db;

    private ListenerRegistration studentsListener;
    private ListenerRegistration companiesListener;

    // Sidebar state - tracks if sidebar is currently visible
    private boolean isSidebarOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize Firestore database
        db = FirebaseFirestore.getInstance();

        // Setup all UI components and their click listeners
        initViews();

        // Setup hamburger menu and sidebar functionality
        setupSidebarToggle();

        // Load dashboard data from Firestore and update UI
        updateDashboardStats();
    }

    /**
     * Initialize all UI components and set up click listeners
     */
    private void initViews() {
        // Find views by ID from layout
        btnMenu = findViewById(R.id.btn_menu);
        sidebar = findViewById(R.id.sidebar);
        overlay = findViewById(R.id.overlay);

        // Statistic text views
        tvTotalStudents = findViewById(R.id.tv_total_students);
        tvPlacedStudents = findViewById(R.id.tv_placed_students);
        tvTotalCompanies = findViewById(R.id.tv_total_companies);
        tvPlacementRate = findViewById(R.id.tv_placement_rate);

        // Navigation menu items
        LinearLayout llDashboard = findViewById(R.id.ll_dashboard);
        LinearLayout llNavStudents = findViewById(R.id.ll_nav_students);
        LinearLayout llNavCompanies = findViewById(R.id.ll_nav_companies);
        LinearLayout llNavPlaced = findViewById(R.id.ll_nav_placed);

        // Dashboard menu item - just closes sidebar
        llDashboard.setOnClickListener(v -> {
            closeSidebar();
            Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show();
        });

        // Students menu - opens students list screen
        llNavStudents.setOnClickListener(v -> {
            closeSidebar();
            startActivity(new Intent(DashboardActivity.this, StudentsActivity.class));
        });

        // Companies menu - opens companies list screen
        llNavCompanies.setOnClickListener(v -> {
            closeSidebar();
            startActivity(new Intent(DashboardActivity.this, CompaniesActivity.class));
        });

        // Placed students menu - opens placed students screen
        llNavPlaced.setOnClickListener(v -> {
            closeSidebar();
            startActivity(new Intent(DashboardActivity.this, PlacedStudentsActivity.class));
        });

        // Offer letters menu - opens offer letters screen
        // llNavOffers.setOnClickListener(v -> {
        //     closeSidebar();
        //     startActivity(new Intent(DashboardActivity.this, OfferLettersActivity.class));
        // });


        // Quick Action Buttons
        Button btnAddStudent = findViewById(R.id.btn_add_student);
        Button btnAddCompany = findViewById(R.id.btn_add_company);

        btnAddStudent.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, AddStudentActivity.class));
        });

        btnAddCompany.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, AddCompanyActivity.class));
        });

    }

    /**
     * Setup hamburger menu button and overlay click to toggle sidebar
     */
    private void setupSidebarToggle() {
        // Click hamburger (three lines) to open/close sidebar
        btnMenu.setOnClickListener(v -> toggleSidebar());

        // Click dark overlay to close sidebar
        overlay.setOnClickListener(v -> closeSidebar());
    }

    /**
     * Toggle sidebar open or closed
     */
    private void toggleSidebar() {
        if (isSidebarOpen) {
            closeSidebar();
        } else {
            openSidebar();
        }
    }

    /**
     * Open the sidebar menu with animation
     */
    private void openSidebar() {
        isSidebarOpen = true;
        // Slide sidebar from left to visible position
        sidebar.animate().translationX(0).setDuration(300).start();
        // Show and fade in the dark overlay
        overlay.setVisibility(View.VISIBLE);
        overlay.animate().alpha(1f).setDuration(300).start();
    }

    /**
     * Close the sidebar menu with animation
     */
    private void closeSidebar() {
        isSidebarOpen = false;
        // Calculate sidebar width in pixels and slide it out to the left
        float sidebarWidth = -280 * getResources().getDisplayMetrics().density;
        sidebar.animate().translationX(sidebarWidth).setDuration(300).start();
        // Fade out and hide the overlay
        overlay.animate().alpha(0f).setDuration(300)
                .withEndAction(() -> overlay.setVisibility(View.GONE)).start();
    }

    /**
     * Load statistics from database and update dashboard cards
     */
    private void updateDashboardStats() {
        // Real-time listener for students
        if (studentsListener != null) studentsListener.remove();
        studentsListener = db.collection("students").addSnapshotListener((studentsSnap, e) -> {
            if (e != null) {
                Toast.makeText(this, "Failed to load student stats: " + e.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
            int totalStudents = studentsSnap != null ? studentsSnap.size() : 0;
            int placedCount = 0;
            if (studentsSnap != null) {
                for (DocumentSnapshot doc : studentsSnap.getDocuments()) {
                    // Check common fields that indicate placement
                    Object placedObj = doc.get("placed");
                    if (placedObj instanceof Boolean) {
                        if ((Boolean) placedObj) placedCount++;
                        continue;
                    }
                    Object companyObj = doc.get("company");
                    if (companyObj != null && !companyObj.toString().trim().isEmpty()) {
                        placedCount++;
                        continue;
                    }
                    Object statusObj = doc.get("status");
                    if (statusObj != null && statusObj.toString().equalsIgnoreCase("placed")) {
                        placedCount++;
                    }
                }
            }
            tvTotalStudents.setText(String.valueOf(totalStudents));
            tvPlacedStudents.setText(String.valueOf(placedCount));
            double rate = totalStudents > 0 ? (placedCount * 100.0 / totalStudents) : 0.0;
            tvPlacementRate.setText(String.format(Locale.getDefault(), "%.0f%%", rate));
        });

        // Real-time listener for companies
        if (companiesListener != null) companiesListener.remove();
        companiesListener = db.collection("companies").addSnapshotListener((companiesSnap, e) -> {
            if (e != null) {
                Toast.makeText(this, "Failed to load companies: " + e.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
            int totalCompanies = companiesSnap != null ? companiesSnap.size() : 0;
            tvTotalCompanies.setText(String.valueOf(totalCompanies));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (studentsListener != null) studentsListener.remove();
        if (companiesListener != null) companiesListener.remove();
    }
}
