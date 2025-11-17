package com.example.tpc_dcsa.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tpc_database.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_STUDENTS = "students";
    public static final String TABLE_COMPANIES = "companies";
    public static final String TABLE_PLACEMENTS = "placements";
    public static final String TABLE_DRIVES = "drives";

    // Users table columns
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_USERNAME = "username";
    public static final String COL_USER_PASSWORD = "password";
    public static final String COL_USER_ROLE = "role";

    // Students table columns
    public static final String COL_STUDENT_ID = "id";
    public static final String COL_STUDENT_NAME = "name";
    public static final String COL_STUDENT_EMAIL = "email";
    public static final String COL_STUDENT_PHONE = "phone";
    public static final String COL_STUDENT_BRANCH = "branch";
    public static final String COL_STUDENT_YEAR = "year";

    // Companies table columns
    public static final String COL_COMPANY_ID = "id";
    public static final String COL_COMPANY_NAME = "name";
    public static final String COL_COMPANY_SECTOR = "sector";
    public static final String COL_COMPANY_LOCATION = "location";

    // Placements table columns
    public static final String COL_PLACEMENT_ID = "id";
    public static final String COL_PLACEMENT_STUDENT_ID = "student_id";
    public static final String COL_PLACEMENT_COMPANY_ID = "company_id";
    public static final String COL_PLACEMENT_PACKAGE = "package";
    public static final String COL_PLACEMENT_DATE = "date";

    // Drives table columns
    public static final String COL_DRIVE_ID = "id";
    public static final String COL_DRIVE_COMPANY_ID = "company_id";
    public static final String COL_DRIVE_DATE = "date";
    public static final String COL_DRIVE_EVENT_TYPE = "event_type";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_USERNAME + " TEXT UNIQUE NOT NULL, " +
                COL_USER_PASSWORD + " TEXT NOT NULL, " +
                COL_USER_ROLE + " TEXT NOT NULL)";
        db.execSQL(CREATE_USERS_TABLE);

        // Create Students table
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + " (" +
                COL_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_STUDENT_NAME + " TEXT NOT NULL, " +
                COL_STUDENT_EMAIL + " TEXT UNIQUE, " +
                COL_STUDENT_PHONE + " TEXT, " +
                COL_STUDENT_BRANCH + " TEXT, " +
                COL_STUDENT_YEAR + " INTEGER)";
        db.execSQL(CREATE_STUDENTS_TABLE);

        // Create Companies table
        String CREATE_COMPANIES_TABLE = "CREATE TABLE " + TABLE_COMPANIES + " (" +
                COL_COMPANY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_COMPANY_NAME + " TEXT NOT NULL, " +
                COL_COMPANY_SECTOR + " TEXT, " +
                COL_COMPANY_LOCATION + " TEXT)";
        db.execSQL(CREATE_COMPANIES_TABLE);

        // Create Placements table
        String CREATE_PLACEMENTS_TABLE = "CREATE TABLE " + TABLE_PLACEMENTS + " (" +
                COL_PLACEMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PLACEMENT_STUDENT_ID + " INTEGER NOT NULL, " +
                COL_PLACEMENT_COMPANY_ID + " INTEGER NOT NULL, " +
                COL_PLACEMENT_PACKAGE + " REAL, " +
                COL_PLACEMENT_DATE + " TEXT, " +
                "FOREIGN KEY(" + COL_PLACEMENT_STUDENT_ID + ") REFERENCES " + TABLE_STUDENTS + "(" + COL_STUDENT_ID + "), " +
                "FOREIGN KEY(" + COL_PLACEMENT_COMPANY_ID + ") REFERENCES " + TABLE_COMPANIES + "(" + COL_COMPANY_ID + "))";
        db.execSQL(CREATE_PLACEMENTS_TABLE);

        // Create Drives table
        String CREATE_DRIVES_TABLE = "CREATE TABLE " + TABLE_DRIVES + " (" +
                COL_DRIVE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DRIVE_COMPANY_ID + " INTEGER NOT NULL, " +
                COL_DRIVE_DATE + " TEXT NOT NULL, " +
                COL_DRIVE_EVENT_TYPE + " TEXT, " +
                "FOREIGN KEY(" + COL_DRIVE_COMPANY_ID + ") REFERENCES " + TABLE_COMPANIES + "(" + COL_COMPANY_ID + "))";
        db.execSQL(CREATE_DRIVES_TABLE);

        // Insert default users
        insertDefaultUsers(db);
    }

    private void insertDefaultUsers(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_USERS + " (" + COL_USER_USERNAME + ", " + COL_USER_PASSWORD + ", " + COL_USER_ROLE + ") VALUES ('admin', 'admin123', 'ADMIN')");
        db.execSQL("INSERT INTO " + TABLE_USERS + " (" + COL_USER_USERNAME + ", " + COL_USER_PASSWORD + ", " + COL_USER_ROLE + ") VALUES ('member', 'member123', 'MEMBER')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACEMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRIVES);
        onCreate(db);
    }
}

