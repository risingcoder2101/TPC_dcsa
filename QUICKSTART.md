# TPC App - Quick Start Guide

## 🚀 Getting Started

### Step 1: Prerequisites
Ensure you have installed:
- Android Studio (Latest)
- JDK 11 or higher
- Android SDK (API 26+)

### Step 2: Open Project
1. Open Android Studio
2. Click "Open" → Select `D:\TPC_dcsa` folder
3. Wait for Gradle sync to complete

### Step 3: Build Project
```bash
# In Android Studio Terminal
./gradlew.bat build
```

### Step 4: Run on Emulator
1. Create/Select Android Virtual Device (AVD)
2. Click ▶️ Run button in Android Studio
3. Select emulator
4. Wait for app to launch

### Step 5: Login
Use one of these credentials:
```
Username: admin
Password: admin123
```
OR
```
Username: member
Password: member123
```

---

## 📱 App Navigation

### Login Screen
- Enter credentials
- Click "Sign In"
- Check "Remember me" (for future enhancement)

### Dashboard Screen
After login, you'll see:

**Left Sidebar Menu:**
- 📊 Dashboard (highlighted)
- 👥 Students
- 🏢 Companies
- ✅ Placed Students
- 📄 Offer Letters
- 👤 User Management

**Main Content Area:**
- 4 Stat Cards (Total Students, Placed, Companies, Placement Rate)
- Quick Action Buttons
- Upcoming Drives section
- Recent Placements section

**Top Right:**
- User role badge (ADMIN/MEMBER)
- Logout button

---

## 🗄️ Database

The database is automatically created on first run with:
- **Admin User**: admin / admin123
- **Member User**: member / member123
- Empty tables ready for data entry

---

## 📂 Project Files Reference

### Core Java Classes
```
app/src/main/java/com/example/tpc_dcsa/
├── LoginActivity.java          → Login screen logic
├── DashboardActivity.java       → Main dashboard
├── StudentsActivity.java        → Students management (stub)
├── CompaniesActivity.java       → Companies management (stub)
├── PlacedStudentsActivity.java  → Placements view (stub)
├── OfferLettersActivity.java    → Offer letters (stub)
├── UserManagementActivity.java  → User management (stub)
└── database/
    └── DatabaseHelper.java      → SQLite helper class
```

### Layout Files
```
app/src/main/res/layout/
├── activity_login.xml           → Login UI
├── activity_dashboard.xml       → Dashboard UI
├── activity_students.xml        → Students UI
├── activity_companies.xml       → Companies UI
├── activity_placed_students.xml → Placements UI
├── activity_offer_letters.xml   → Offers UI
└── activity_user_management.xml → Users UI
```

### Resource Files
```
app/src/main/res/
├── drawable/
│   ├── button_gradient_bg.xml   → Button styling
│   ├── card_*_bg.xml            → Card backgrounds
│   ├── edit_text_bg.xml         → Text input styling
│   └── rounded_white_bg.xml     → Rounded container
├── values/
│   ├── colors.xml               → Color definitions
│   ├── strings.xml              → String resources
│   └── themes.xml               → App theme
```

---

## 🎯 Feature Status

### ✅ Completed
- [x] Login authentication
- [x] Dashboard with stats
- [x] SQLite database
- [x] UI layout
- [x] Navigation menu
- [x] Theme & colors
- [x] Default users

### ⏳ To Do (Next Phase)
- [ ] Add Student form
- [ ] View/Edit/Delete students
- [ ] Add Company form
- [ ] View/Edit/Delete companies
- [ ] Add Placement records
- [ ] Add Offer letters
- [ ] Google Sheets integration
- [ ] Reports/Export
- [ ] Advanced search/filter

---

## 🔧 Troubleshooting

### Problem: App won't build
**Solution**:
```bash
./gradlew.bat clean
./gradlew.bat build
```

### Problem: Activities not found
**Solution**:
- Sync Gradle files (File → Sync Now)
- Rebuild project

### Problem: Database not created
**Solution**:
- First run automatically creates database
- Check device file explorer: `/data/data/com.example.tpc_dcsa/databases/tpc_database.db`

### Problem: Login credentials not working
**Solution**:
- Use: admin / admin123 (admin user)
- Or: member / member123 (member user)
- Database auto-populates on first run

---

## 📊 Database Tables

### Users Table
```
CREATE TABLE users (
    id INTEGER PRIMARY KEY,
    username TEXT UNIQUE,
    password TEXT,
    role TEXT
)
```

### Students Table
```
CREATE TABLE students (
    id INTEGER PRIMARY KEY,
    name TEXT,
    email TEXT UNIQUE,
    phone TEXT,
    branch TEXT,
    year INTEGER
)
```

### Companies Table
```
CREATE TABLE companies (
    id INTEGER PRIMARY KEY,
    name TEXT,
    sector TEXT,
    location TEXT
)
```

### Placements Table
```
CREATE TABLE placements (
    id INTEGER PRIMARY KEY,
    student_id INTEGER,
    company_id INTEGER,
    package REAL,
    date TEXT
)
```

### Drives Table
```
CREATE TABLE drives (
    id INTEGER PRIMARY KEY,
    company_id INTEGER,
    date TEXT,
    event_type TEXT
)
```

---

## 🎨 UI Customization

### Colors (in `colors.xml`)
- Primary: `#5C6BC0` (Purple)
- Secondary: `#80DEEA` (Teal)
- Blue Card: `#2196F3`
- Green Card: `#4CAF50`
- Cyan Card: `#00BCD4`
- Yellow Card: `#FFC107`

### Fonts
- Size for titles: 28sp-32sp
- Size for labels: 14sp-16sp
- Size for stats: 36sp

---

## 📞 Support

For issues or questions:
1. Check IMPLEMENTATION_SUMMARY.md
2. Review README.md
3. Check logcat for errors (Ctrl+Alt+L in Android Studio)

---

**Happy coding! 🎉**

