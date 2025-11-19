package com.example.tpc_dcsa;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

/**
 * Custom Application class to initialize Firebase and enable offline persistence
 * This ensures data is cached locally and syncs when internet is available
 */
public class TPCApplication extends Application {
    private static final String TAG = "TPCApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            // Initialize Firebase
            FirebaseApp.initializeApp(this);

            // Enable Firestore offline persistence using newer API
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .build();
            firestore.setFirestoreSettings(settings);

            // Firestore now enables persistence by default
            Log.d(TAG, "Firebase initialized with offline persistence enabled");
        } catch (Exception e) {
            Log.e(TAG, "Error initializing Firebase: " + e.getMessage(), e);
        }
    }
}

