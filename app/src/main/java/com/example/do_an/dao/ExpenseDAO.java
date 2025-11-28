package com.example.do_an.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.do_an.database.DatabaseHelper;
import com.example.do_an.model.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public ExpenseDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // 1. Thêm giao dịch mới
    public long insertExpense(Expense expense) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_AMOUNT, expense.getAmount());
        values.put(DatabaseHelper.COLUMN_NAME, expense.getName());
        values.put(DatabaseHelper.COLUMN_DATE, expense.getDate());
        values.put(DatabaseHelper.COLUMN_TYPE, expense.getType());
        values.put(DatabaseHelper.COLUMN_CATEGORY_ID, expense.getCategoryId());

        return database.insert(DatabaseHelper.TABLE_EXPENSE, null, values);
    }

    // 2. Lấy danh sách giao dịch (limit = số lượng muốn lấy, -1 là lấy hết)
    public List<Expense> getExpenses(int limit) {
        List<Expense> list = new ArrayList<>();
        String limitQuery = (limit > 0) ? " LIMIT " + limit : "";

        // Lấy tất cả, sắp xếp id giảm dần (cái mới nhất lên đầu)
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_EXPENSE +
                " ORDER BY " + DatabaseHelper.COLUMN_ID + " DESC" + limitQuery;

        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Expense expense = new Expense();
                expense.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                expense.setAmount(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AMOUNT)));
                expense.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)));
                expense.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE)));
                expense.setType(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TYPE)));
                expense.setCategoryId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY_ID)));
                list.add(expense);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    // 3. Tính tổng tiền (type 0: Chi, type 1: Thu)
    public long getTotalAmountByType(int type) {
        long total = 0;
        String query = "SELECT SUM(" + DatabaseHelper.COLUMN_AMOUNT + ") FROM " +
                DatabaseHelper.TABLE_EXPENSE + " WHERE " + DatabaseHelper.COLUMN_TYPE + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(type)});
        if (cursor.moveToFirst()) {
            total = cursor.getLong(0);
        }
        cursor.close();
        return total;
    }
    public boolean deleteExpense(int id) {
        return database.delete(DatabaseHelper.TABLE_EXPENSE,
                DatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}) > 0;
    }
    public void close() {
        dbHelper.close();
    }
}