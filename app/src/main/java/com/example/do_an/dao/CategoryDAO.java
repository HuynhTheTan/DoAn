package com.example.do_an.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.do_an.database.DatabaseHelper;
import com.example.do_an.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public CategoryDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // Lấy tất cả danh mục
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_CATEGORY, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                category.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY_NAME)));
                list.add(category);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // Nếu chưa có danh mục nào, tự động tạo mặc định
        if (list.isEmpty()) {
            initDefaultCategories();
            return getAllCategories(); // Gọi lại để lấy danh sách vừa tạo
        }

        return list;
    }

    // Hàm tạo danh mục mặc định
    private void initDefaultCategories() {
        String[] defaults = {"Ăn uống", "Di chuyển", "Mua sắm", "Hóa đơn", "Giải trí", "Y tế", "Khác"};
        for (String name : defaults) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_CATEGORY_NAME, name);
            database.insert(DatabaseHelper.TABLE_CATEGORY, null, values);
        }
    }

    public String getCategoryNameById(int id) {
        String name = "Khác";
        Cursor cursor = database.query(DatabaseHelper.TABLE_CATEGORY,
                new String[]{DatabaseHelper.COLUMN_CATEGORY_NAME},
                DatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            name = cursor.getString(0);
            cursor.close();
        }
        return name;
    }
}