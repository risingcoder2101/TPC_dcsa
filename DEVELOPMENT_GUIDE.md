# TPC App - Development Guide

## Adding Students - Code Template

### 1. Add to DatabaseHelper.java
```java
public long addStudent(String name, String email, String phone, String branch, int year) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COL_STUDENT_NAME, name);
    values.put(COL_STUDENT_EMAIL, email);
    values.put(COL_STUDENT_PHONE, phone);
    values.put(COL_STUDENT_BRANCH, branch);
    values.put(COL_STUDENT_YEAR, year);
    return db.insert(TABLE_STUDENTS, null, values);
}

public ArrayList<String[]> getAllStudents() {
    ArrayList<String[]> studentList = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.query(TABLE_STUDENTS, null, null, null, null, null, null);
    
    if (cursor.moveToFirst()) {
        do {
            String[] student = new String[6];
            student[0] = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_ID));
            student[1] = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_NAME));
            student[2] = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_EMAIL));
            student[3] = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_PHONE));
            student[4] = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_BRANCH));
            student[5] = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_YEAR));
            studentList.add(student);
        } while (cursor.moveToNext());
    }
    cursor.close();
    return studentList;
}

public boolean updateStudent(int id, String name, String email, String phone, String branch, int year) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COL_STUDENT_NAME, name);
    values.put(COL_STUDENT_EMAIL, email);
    values.put(COL_STUDENT_PHONE, phone);
    values.put(COL_STUDENT_BRANCH, branch);
    values.put(COL_STUDENT_YEAR, year);
    return db.update(TABLE_STUDENTS, values, COL_STUDENT_ID + "=?", new String[]{String.valueOf(id)}) > 0;
}

public boolean deleteStudent(int id) {
    SQLiteDatabase db = this.getWritableDatabase();
    return db.delete(TABLE_STUDENTS, COL_STUDENT_ID + "=?", new String[]{String.valueOf(id)}) > 0;
}
```

### 2. Create Add Student Form (activity_add_student_form.xml)
```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <EditText
            android:id="@+id/et_student_name"
            android:layout_width="match_parent"
            android:layout_height="48dp"
            android:hint="Student Name"
            android:padding="12dp"
            android:layout_marginBottom="12dp"
            android:background="@drawable/edit_text_bg" />

        <EditText
            android:id="@+id/et_student_email"
            android:layout_width="match_parent"
            android:layout_height="48dp"
            android:hint="Email"
            android:inputType="textEmailAddress"
            android:padding="12dp"
            android:layout_marginBottom="12dp"
            android:background="@drawable/edit_text_bg" />

        <EditText
            android:id="@+id/et_student_phone"
            android:layout_width="match_parent"
            android:layout_height="48dp"
            android:hint="Phone Number"
            android:inputType="phone"
            android:padding="12dp"
            android:layout_marginBottom="12dp"
            android:background="@drawable/edit_text_bg" />

        <EditText
            android:id="@+id/et_student_branch"
            android:layout_width="match_parent"
            android:layout_height="48dp"
            android:hint="Branch (e.g., CSE, ECE)"
            android:padding="12dp"
            android:layout_marginBottom="12dp"
            android:background="@drawable/edit_text_bg" />

        <EditText
            android:id="@+id/et_student_year"
            android:layout_width="match_parent"
            android:layout_height="48dp"
            android:hint="Year (1-4)"
            android:inputType="number"
            android:padding="12dp"
            android:layout_marginBottom="24dp"
            android:background="@drawable/edit_text_bg" />

        <Button
            android:id="@+id/btn_save_student"
            android:layout_width="match_parent"
            android:layout_height="48dp"
            android:text="Save Student"
            android:textColor="@color/white"
            android:background="@drawable/card_blue_bg" />

    </LinearLayout>

</ScrollView>
```

### 3. Update StudentsActivity.java
```java
package com.example.tpc_dcsa;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpc_dcsa.database.DatabaseHelper;

import java.util.ArrayList;

public class StudentsActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private ListView listViewStudents;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> studentNames = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        
        dbHelper = new DatabaseHelper(this);
        listViewStudents = findViewById(R.id.lv_students);
        
        loadStudents();
    }
    
    private void loadStudents() {
        ArrayList<String[]> students = dbHelper.getAllStudents();
        studentNames.clear();
        for (String[] student : students) {
            studentNames.add(student[1]); // Add student name
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentNames);
        listViewStudents.setAdapter(adapter);
    }
}
```

---

## Adding Companies - Code Template

### DatabaseHelper.java Methods
```java
public long addCompany(String name, String sector, String location) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COL_COMPANY_NAME, name);
    values.put(COL_COMPANY_SECTOR, sector);
    values.put(COL_COMPANY_LOCATION, location);
    return db.insert(TABLE_COMPANIES, null, values);
}

public ArrayList<String[]> getAllCompanies() {
    ArrayList<String[]> companies = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.query(TABLE_COMPANIES, null, null, null, null, null, null);
    
    if (cursor.moveToFirst()) {
        do {
            String[] company = new String[4];
            company[0] = cursor.getString(cursor.getColumnIndexOrThrow(COL_COMPANY_ID));
            company[1] = cursor.getString(cursor.getColumnIndexOrThrow(COL_COMPANY_NAME));
            company[2] = cursor.getString(cursor.getColumnIndexOrThrow(COL_COMPANY_SECTOR));
            company[3] = cursor.getString(cursor.getColumnIndexOrThrow(COL_COMPANY_LOCATION));
            companies.add(company);
        } while (cursor.moveToNext());
    }
    cursor.close();
    return companies;
}
```

---

## Adding Placements - Code Template

### DatabaseHelper.java Methods
```java
public long addPlacement(int studentId, int companyId, double packageAmount, String date) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COL_PLACEMENT_STUDENT_ID, studentId);
    values.put(COL_PLACEMENT_COMPANY_ID, companyId);
    values.put(COL_PLACEMENT_PACKAGE, packageAmount);
    values.put(COL_PLACEMENT_DATE, date);
    return db.insert(TABLE_PLACEMENTS, null, values);
}

public ArrayList<String[]> getRecentPlacements(int limit) {
    ArrayList<String[]> placements = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    String query = "SELECT p.id, s.name, c.name, p.package FROM placements p " +
                   "JOIN students s ON p.student_id = s.id " +
                   "JOIN companies c ON p.company_id = c.id " +
                   "ORDER BY p.date DESC LIMIT ?";
    Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(limit)});
    
    if (cursor.moveToFirst()) {
        do {
            String[] placement = new String[4];
            placement[0] = cursor.getString(0); // placement id
            placement[1] = cursor.getString(1); // student name
            placement[2] = cursor.getString(2); // company name
            placement[3] = cursor.getString(3); // package
            placements.add(placement);
        } while (cursor.moveToNext());
    }
    cursor.close();
    return placements;
}
```

---

## Import Statements for Java Classes

```java
// Standard Android imports
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

// AndroidX imports
import androidx.appcompat.app.AppCompatActivity;

// App-specific imports
import com.example.tpc_dcsa.database.DatabaseHelper;

// Java utilities
import java.util.ArrayList;
import java.util.Locale;
```

---

## RecyclerView Adapter Template

```java
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private ArrayList<Student> students;
    private Context context;

    public StudentAdapter(Context context, ArrayList<Student> students) {
        this.context = context;
        this.students = students;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student student = students.get(position);
        holder.nameView.setText(student.name);
        holder.emailView.setText(student.email);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public TextView emailView;

        public ViewHolder(View view) {
            super(view);
            nameView = view.findViewById(R.id.tv_student_name);
            emailView = view.findViewById(R.id.tv_student_email);
        }
    }
}
```

---

## Google Sheets Integration Template (Future)

```java
// Add to build.gradle.kts dependencies
implementation("com.google.auth:google-auth-library-oauth2-http:1.11.0")
implementation("com.google.http-client:google-http-client-gson:1.43.3")

// GoogleSheetsHelper.java template
public class GoogleSheetsHelper {
    private Sheets sheetsService;
    private String spreadsheetId;

    public void authenticate(GoogleSignInAccount account) {
        // Implement OAuth2 authentication
    }

    public void readFromSheet(String range) {
        // Read data from Google Sheet
    }

    public void writeToSheet(String range, List<List<Object>> data) {
        // Write data to Google Sheet
    }
}
```

---

## Testing Checklist

- [ ] Add student with valid data
- [ ] Add student with duplicate email (should fail)
- [ ] Update student information
- [ ] Delete student
- [ ] View all students
- [ ] Search for student
- [ ] Add company
- [ ] Add placement record
- [ ] View placements
- [ ] Database persistence (close and reopen app)

---

## Common Error Solutions

### Error: "Cannot resolve symbol 'DatabaseHelper'"
**Fix**: Add import statement at top of file:
```java
import com.example.tpc_dcsa.database.DatabaseHelper;
```

### Error: "Cannot find symbol 'findViewById'"
**Fix**: Ensure you called `setContentView()` before using `findViewById()`:
```java
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_students);  // Must be before findViewById
    
    ListView listView = findViewById(R.id.list_view);
}
```

### Error: "Database locked"
**Fix**: Ensure you close database cursors:
```java
cursor.close();
db.close();
```

---

## Performance Tips

1. Use RecyclerView instead of ListView for large lists
2. Load data in background threads (AsyncTask or Coroutines)
3. Cache frequently accessed data
4. Use database indexes for better query performance
5. Implement pagination for large datasets

---

**Happy Development! 🚀**

