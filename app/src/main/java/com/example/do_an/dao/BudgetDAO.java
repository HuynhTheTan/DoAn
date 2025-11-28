package com.example.do_an.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.do_an.database.DatabaseHelper;
import com.example.do_an.model.Budget;

public class BudgetDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public BudgetDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // Đặt ngân sách (nếu đã có của tháng đó thì cập nhật, chưa có thì thêm mới)
    public void setBudget(Budget budget) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_BUDGET_AMOUNT, budget.getAmount());
        values.put(DatabaseHelper.COLUMN_BUDGET_PERIOD, budget.getPeriod());

        // Kiểm tra xem tháng này đã đặt ngân sách chưa
        Cursor cursor = database.query(DatabaseHelper.TABLE_BUDGET, null,
                DatabaseHelper.COLUMN_BUDGET_PERIOD + "=?",
                new String[]{budget.getPeriod()}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            // Đã có -> Cập nhật (Update)
            database.update(DatabaseHelper.TABLE_BUDGET, values,
                    DatabaseHelper.COLUMN_BUDGET_PERIOD + "=?",
                    new String[]{budget.getPeriod()});
            cursor.close();
        } else {
            // Chưa có -> Thêm mới (Insert)
            database.insert(DatabaseHelper.TABLE_BUDGET, null, values);
        }
    }

    // Lấy ngân sách theo tháng (Ví dụ: "11/2023")
    public Budget getBudgetByPeriod(String period) {
        Budget budget = null;
        Cursor cursor = database.query(DatabaseHelper.TABLE_BUDGET, null,
                DatabaseHelper.COLUMN_BUDGET_PERIOD + "=?",
                new String[]{period}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            budget = new Budget();
            budget.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
            budget.setAmount(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BUDGET_AMOUNT)));
            budget.setPeriod(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BUDGET_PERIOD)));
            cursor.close();
        }
        return budget;
    }
}