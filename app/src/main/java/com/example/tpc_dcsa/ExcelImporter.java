package com.example.tpc_dcsa;

import android.content.Context;
import android.net.Uri;

import com.example.tpc_dcsa.model.Student;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Lightweight helper to import students from an .xlsx file using Apache POI.
 * This class isolates POI references so activities that don't use Excel won't fail
 * at class verification time on some Android runtimes.
 */
public class ExcelImporter {
    public static List<Student> importStudentsFromUri(Context context, Uri uri) throws Exception {
        List<Student> students = new ArrayList<>();
        try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header
                String name = getCellString(row, 0);
                String rollNumber = getCellString(row, 1);
                String gender = getCellString(row, 2);
                String batch = getCellString(row, 3);
                String email = getCellString(row, 4);
                String phone = getCellString(row, 5);
                String campus = getCellString(row, 6);
                String percentage = getCellString(row, 7);
                if (name == null || name.isEmpty()) continue;
                students.add(new Student(name, rollNumber, gender, batch, email, phone, campus, percentage));
            }
        }
        return students;
    }

    private static String getCellString(Row row, int col) {
        Cell cell = row.getCell(col);
        return cell == null ? "" : cell.toString().trim();
    }
}

