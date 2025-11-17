package com.example.tpc_dcsa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpc_dcsa.database.DatabaseHelper;

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

    // Database helper for accessing SQLite database
    private DatabaseHelper dbHelper;


    // Sidebar state - tracks if sidebar is currently visible
    private boolean isSidebarOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);


        // Setup all UI components and their click listeners
        initViews();

        // Load and display statistics from database
        updateDashboardStats();

        // Setup sidebar menu functionality
        setupSidebarToggle();
    }

    /**
     * Initialize all UI components and set up click listeners
     */
    private void initViews() {
        // Find views by ID from layout
        btnMenu = findViewById(R.id.btn_menu);
        sidebar = findViewById(R.id.sidebar);
        overlay = findViewById(R.id.overlay);

        // Navigation menu items
        LinearLayout llDashboard = findViewById(R.id.ll_dashboard);
        LinearLayout llNavStudents = findViewById(R.id.ll_nav_students);
        LinearLayout llNavCompanies = findViewById(R.id.ll_nav_companies);
        LinearLayout llNavPlaced = findViewById(R.id.ll_nav_placed);
        LinearLayout llNavOffers = findViewById(R.id.ll_nav_offers);

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
        llNavOffers.setOnClickListener(v -> {
            closeSidebar();
            startActivity(new Intent(DashboardActivity.this, OfferLettersActivity.class));
        });



        // Quick Action Buttons
        Button btnAddStudent = findViewById(R.id.btn_add_student);
        Button btnAddCompany = findViewById(R.id.btn_add_company);
        Button btnUpcomingDrives = findViewById(R.id.btn_upcoming_drives);

        btnAddStudent.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, AddStudentActivity.class));
        });

        btnAddCompany.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, CompaniesActivity.class));
        });

        btnUpcomingDrives.setOnClickListener(v -> {
            Toast.makeText(this, "Upcoming Drives", Toast.LENGTH_SHORT).show();
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
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Count total students in database
        Cursor studentCursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_STUDENTS, null);
        studentCursor.moveToFirst();
        int totalStudents = studentCursor.getInt(0);
        studentCursor.close();

        // Count placed students
        Cursor placedCursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_PLACEMENTS, null);
        placedCursor.moveToFirst();
        int placedStudents = placedCursor.getInt(0);
        placedCursor.close();

        // Count total companies
        Cursor companyCursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_COMPANIES, null);
        companyCursor.moveToFirst();
        int totalCompanies = companyCursor.getInt(0);
        companyCursor.close();

        // Calculate placement rate percentage
        float placementRate = (totalStudents > 0) ? (float) placedStudents / totalStudents * 100 : 0;

        // Update dashboard stat cards with data
        TextView tvTotalStudents = findViewById(R.id.tv_total_students);
        TextView tvPlacedStudents = findViewById(R.id.tv_placed_students);
        TextView tvTotalCompanies = findViewById(R.id.tv_total_companies);
        TextView tvPlacementRate = findViewById(R.id.tv_placement_rate);

        tvTotalStudents.setText(String.valueOf(totalStudents));
        tvPlacedStudents.setText(String.valueOf(placedStudents));
        tvTotalCompanies.setText(String.valueOf(totalCompanies));
        tvPlacementRate.setText(String.format(Locale.getDefault(), "%.2f%%", placementRate));

        db.close();
    }
}
