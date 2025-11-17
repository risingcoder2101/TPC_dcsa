# Testing Guide - TPC Management System

## Build & Run the App

### Step 1: Clean and Build
```bash
gradlew clean assembleDebug
```

Or from Android Studio:
- **Build** → **Clean Project**
- **Build** → **Rebuild Project**

### Step 2: Run on Device/Emulator
- Connect Android device or start emulator
- Click **Run** button in Android Studio
- Or use command: `gradlew installDebug`

## Testing the App

### Test 1: First Launch & Database Creation
1. **Launch the app**
2. You should see the **splash screen** (purple background with 🎓 icon)
3. After 2 seconds, it auto-redirects to **Login Page**
4. Database is automatically created with default users

### Test 2: Login with Admin
1. **Username**: `admin`
2. **Password**: `admin123`
3. Click **Sign In**
4. Should see **Dashboard** with:
   - Role badge showing "ADMIN"
   - Navigation menu on left
   - Statistics cards (will show 0 initially)
   - Quick action buttons

### Test 3: Login with Member
1. Logout from dashboard
2. Login again with:
   - **Username**: `member`
   - **Password**: `member123`
3. Should see **Dashboard** with role "MEMBER"

### Test 4: Navigation
From Dashboard, test clicking:
- 👥 **Students** → Opens StudentsActivity
- 🏢 **Companies** → Opens CompaniesActivity
- ✅ **Placed Students** → Opens PlacedStudentsActivity
- 📄 **Offer Letters** → Opens OfferLettersActivity
- 👤 **User Management** → Opens UserManagementActivity (Admin only)

### Test 5: Logout
- Click **Logout** button in top-right
- Should return to Login page

## Expected Behavior

### ✅ What Should Work:
- [x] App launches with splash screen
- [x] Redirects to login page
- [x] Login with admin/admin123
- [x] Login with member/member123
- [x] Dashboard displays correctly
- [x] Logout works
- [x] Data persists (SQLite)
- [x] Works offline (no internet needed)

### ⚠️ What Needs Implementation:
- [ ] Add new student functionality
- [ ] Add new company functionality
- [ ] Record placements
- [ ] View/edit student details
- [ ] View/edit company details
- [ ] Generate reports
- [ ] Export data

## Common Issues & Solutions

### Issue: App crashes on launch
**Solution**: Check if SQLite database is created properly
- Go to: Device File Explorer → data/data/com.example.tpc_dcsa/databases/
- Should see `tpc_database.db`

### Issue: Login fails
**Solution**: 
1. Check credentials are exactly: `admin` / `admin123`
2. Uninstall and reinstall app to recreate database
3. Check database has users table with default data

### Issue: Dashboard shows errors
**Solution**: 
1. All activity classes must exist
2. All layout files must be present
3. Check AndroidManifest.xml has all activities declared

## Database Inspection

To view database contents (for debugging):
1. Use **Android Studio Database Inspector**
2. Or use ADB:
```bash
adb shell
run-as com.example.tpc_dcsa
cd databases
sqlite3 tpc_database.db
.tables
SELECT * FROM users;
```

## Next Development Steps

1. **Students Module**: Complete CRUD operations
2. **Companies Module**: Complete CRUD operations
3. **Placements Module**: Link students to companies
4. **Reports**: Generate placement statistics
5. **Excel Import**: Add ability to bulk import data
6. **Validation**: Add input validation
7. **Search**: Add search functionality
8. **Filters**: Add filtering options

## Notes
- This is a **fully offline app** - no internet required
- All data stored in local SQLite database
- Database location: `/data/data/com.example.tpc_dcsa/databases/tpc_database.db`
- First launch creates database and default users automatically

