# TPC Management System - Offline SQLite App

## Overview
This is a fully **offline** Training and Placement Cell (TPC) Management System using SQLite database. No internet connection required!

## App Flow

### 1. Splash Screen (Optional)
- **File**: `SignInActivity.java` / `activity_sign_in.xml`
- Shows welcome screen for 2 seconds
- Automatically redirects to Login

### 2. Login Page ✅
- **File**: `LoginActivity.java` / `activity_login.xml`
- Uses SQLite database for authentication
- Default credentials:
  - **Admin**: username: `admin`, password: `admin123`
  - **Member**: username: `member`, password: `member123`

### 3. Dashboard
- **File**: `DashboardActivity.java` / `activity_dashboard.xml`
- Shows statistics (total students, placements, companies, placement rate)
- Navigation menu to access all sections
- Role-based UI (ADMIN/MEMBER)

## Database Structure (SQLite)

### Tables:
1. **users** - Authentication and user management
   - id, username, password, role

2. **students** - Student records
   - id, name, email, phone, branch, year

3. **companies** - Company information
   - id, name, sector, location

4. **placements** - Placement records
   - id, student_id, company_id, package, date

5. **drives** - Placement drive events
   - id, company_id, date, event_type

## Features

### ✅ Implemented:
- Login system with SQLite
- Dashboard with statistics
- User role management (Admin/Member)
- Database schema for all entities

### 📋 Modules:
1. **Students Management** - `StudentsActivity.java`
2. **Companies Management** - `CompaniesActivity.java`
3. **Placed Students** - `PlacedStudentsActivity.java`
4. **Offer Letters** - `OfferLettersActivity.java`
5. **User Management** - `UserManagementActivity.java`

## How to Use

1. **First Launch**:
   - App creates SQLite database automatically
   - Default admin and member users are created

2. **Login**:
   - Enter username and password
   - Click Login button

3. **Dashboard**:
   - View statistics
   - Navigate to different modules
   - Logout when done

## No Internet Required! 🚫📡
- All data stored locally in SQLite
- No Google Sign-In or cloud services
- Perfect for offline use in institutions

## File Structure
```
app/
├── src/main/java/com/example/tpc_dcsa/
│   ├── LoginActivity.java          # Login screen
│   ├── DashboardActivity.java      # Main dashboard
│   ├── StudentsActivity.java       # Manage students
│   ├── CompaniesActivity.java      # Manage companies
│   ├── PlacedStudentsActivity.java # View placements
│   ├── OfferLettersActivity.java   # Manage offers
│   ├── UserManagementActivity.java # Admin user management
│   └── database/
│       └── DatabaseHelper.java     # SQLite database handler
├── src/main/res/layout/
│   ├── activity_login.xml          # Login UI
│   ├── activity_dashboard.xml      # Dashboard UI
│   └── ... (other layouts)
└── AndroidManifest.xml             # App configuration
```

## Default Login Credentials

### Admin User
- **Username**: `admin`
- **Password**: `admin123`
- **Role**: ADMIN
- Full access to all features

### Member User
- **Username**: `member`
- **Password**: `member123`
- **Role**: MEMBER
- Limited access

## Next Steps

To complete the app, you need to implement:
1. Add student functionality (create, read, update, delete)
2. Add company functionality
3. Record placement data
4. Generate reports
5. Upload/import Excel data (optional)
6. Export data functionality

## Technology Stack
- **Language**: Java
- **Database**: SQLite
- **UI**: XML Layouts with Material Design
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 36

