# TPC Management System - Android App

An offline-first Training & Placement Cell (TPC) management application built with Java and SQLite for Android.

## Features

### 1. **Authentication System**
- Login page with username/password authentication
- Pre-configured default users:
  - **Admin**: username `admin`, password `admin123`
  - **Member**: username `member`, password `member123`
- Remember me checkbox functionality
- SQLite-based user storage

### 2. **Dashboard**
- Overview of key statistics:
  - Total Students count
  - Placed Students count
  - Total Companies count
  - Placement Rate percentage
- Quick action buttons:
  - Add Student
  - Add Company
  - Upload Excel
- Upcoming Drives section with company details
- Recent Placements section showcasing placed students

### 3. **Navigation Menu**
- Dashboard
- Students Management
- Companies Management
- Placed Students
- Offer Letters
- User Management

### 4. **Database Structure (SQLite)**
The app uses SQLite with the following tables:

#### Users Table
- `id` (INTEGER PRIMARY KEY)
- `username` (TEXT UNIQUE)
- `password` (TEXT)
- `role` (TEXT) - ADMIN or MEMBER

#### Students Table
- `id` (INTEGER PRIMARY KEY)
- `name` (TEXT)
- `email` (TEXT UNIQUE)
- `phone` (TEXT)
- `branch` (TEXT)
- `year` (INTEGER)

#### Companies Table
- `id` (INTEGER PRIMARY KEY)
- `name` (TEXT)
- `sector` (TEXT)
- `location` (TEXT)

#### Placements Table
- `id` (INTEGER PRIMARY KEY)
- `student_id` (INTEGER - Foreign Key)
- `company_id` (INTEGER - Foreign Key)
- `package` (REAL)
- `date` (TEXT)

#### Drives Table
- `id` (INTEGER PRIMARY KEY)
- `company_id` (INTEGER - Foreign Key)
- `date` (TEXT)
- `event_type` (TEXT)

## Project Structure

```
app/src/main/
├── java/com/example/tpc_dcsa/
│   ├── LoginActivity.java
│   ├── DashboardActivity.java
│   ├── StudentsActivity.java
│   ├── CompaniesActivity.java
│   ├── PlacedStudentsActivity.java
│   ├── OfferLettersActivity.java
│   ├── UserManagementActivity.java
│   ├── database/
│   │   └── DatabaseHelper.java
│   └── model/
│       ├── Student.kt
│       ├── Company.kt
│       └── OfferLetter.kt
├── res/
│   ├── layout/
│   │   ├── activity_login.xml
│   │   ├── activity_dashboard.xml
│   │   ├── activity_students.xml
│   │   ├── activity_companies.xml
│   │   ├── activity_placed_students.xml
│   │   ├── activity_offer_letters.xml
│   │   └── activity_user_management.xml
│   ├── drawable/
│   │   ├── button_gradient_bg.xml
│   │   ├── card_blue_bg.xml
│   │   ├── card_green_bg.xml
│   │   ├── card_cyan_bg.xml
│   │   ├── card_yellow_bg.xml
│   │   ├── edit_text_bg.xml
│   │   └── rounded_white_bg.xml
│   └── values/
│       ├── colors.xml
│       ├── strings.xml
│       └── themes.xml
└── AndroidManifest.xml
```

## UI Design

### Login Screen
- Left side: Purple gradient background with app branding
  - Title: "🎓 TPC Portal"
  - Subtitle: "Training & Placement Cell"
  - Description and key features listed
- Right side: White rounded card with login form
  - Username field
  - Password field
  - Remember Me checkbox
  - Sign In button
  - Default credentials hint

### Dashboard Screen
- Top header: Purple gradient with app title and user info
- Left sidebar: Navigation menu with quick links
- Main content area with:
  - Four colored stat cards (Blue, Green, Cyan, Yellow)
  - Quick action buttons
  - Upcoming Drives section
  - Recent Placements section

## Color Scheme
- **Primary**: Purple (#5C6BC0)
- **Secondary**: Teal (#80DEEA)
- **Card Colors**:
  - Blue: #2196F3 (Total Students)
  - Green: #4CAF50 (Placed Students)
  - Cyan: #00BCD4 (Companies)
  - Yellow: #FFC107 (Placement Rate)

## How to Build and Run

### Prerequisites
- Android Studio (latest version)
- Gradle 8.0+
- Java 11+
- Android SDK API Level 26+

### Build Steps

1. **Clone/Open the project** in Android Studio

2. **Sync Gradle files**:
   ```bash
   cd D:\TPC_dcsa
   ./gradlew.bat sync
   ```

3. **Build the project**:
   ```bash
   ./gradlew.bat build
   ```

4. **Run on emulator or device**:
   ```bash
   ./gradlew.bat installDebug
   ```

### Default Login Credentials
- **Admin User**: admin / admin123
- **Member User**: member / member123

## Dependencies

- AndroidX AppCompat
- Material Design Components
- Google Sheets API (for future integration)
- Google Play Services Auth

## Future Enhancements

1. **Add CRUD Operations**:
   - Add/Edit/Delete students
   - Add/Edit/Delete companies
   - Add/Edit/Delete placements

2. **Google Sheets Sync**:
   - Sync data with Google Sheets
   - Upload/Download functionality
   - Real-time synchronization

3. **Reports Generation**:
   - PDF export
   - Excel export
   - Statistical charts

4. **Advanced Search & Filtering**:
   - Search by student name
   - Filter by branch/year
   - Filter placements by company

5. **Notifications**:
   - Drive reminders
   - Placement alerts
   - Event notifications

## Technical Details

### Database Initialization
The database is automatically created when the app first launches. Default admin and member users are pre-populated in the users table.

### Authentication Flow
1. User enters credentials in login form
2. System queries SQLite database
3. Password validation
4. If successful, navigate to Dashboard
5. If failed, show error message

### Statistics Calculation
- Total Students: COUNT from students table
- Placed Students: COUNT from placements table
- Total Companies: COUNT from companies table
- Placement Rate: (Placed / Total) * 100%

## Notes

- The app is fully offline. All data is stored locally in SQLite
- User sessions are not persisted; app returns to login on restart
- The model files (Student.kt, Company.kt, OfferLetter.kt) are Kotlin data classes for future use
- Remove unused Kotlin files if migrating fully to Java

