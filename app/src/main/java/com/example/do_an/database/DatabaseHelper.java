package com.example.do_an.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expense_manager.db";
    // 👇 QUAN TRỌNG: Đã tăng version từ 1 lên 2 để kích hoạt cập nhật
    private static final int DATABASE_VERSION = 2;

    // --- BẢNG 1: EXPENSES (Giao dịch) ---
    public static final String TABLE_EXPENSE = "expenses";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TYPE = "type"; // 0: Chi, 1: Thu
    public static final String COLUMN_CATEGORY_ID = "category_id";

    // --- BẢNG 2: CATEGORIES (Danh mục) ---
    public static final String TABLE_CATEGORY = "categories";
    public static final String COLUMN_CATEGORY_NAME = "name";

    // --- BẢNG 3: BUDGET (Ngân sách) ---
    public static final String TABLE_BUDGET = "budget";
    public static final String COLUMN_BUDGET_AMOUNT = "amount";
    public static final String COLUMN_BUDGET_PERIOD = "period"; // Ví dụ: "11/2023"

    // Câu lệnh tạo bảng EXPENSES
    private static final String CREATE_TABLE_EXPENSES = "CREATE TABLE " + TABLE_EXPENSE + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_AMOUNT + " INTEGER, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_TYPE + " INTEGER, " +
            COLUMN_CATEGORY_ID + " INTEGER)";

    // Câu lệnh tạo bảng CATEGORIES
    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORY + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_CATEGORY_NAME + " TEXT)";

    // Câu lệnh tạo bảng BUDGET
    private static final String CREATE_TABLE_BUDGET = "CREATE TABLE " + TABLE_BUDGET + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BUDGET_AMOUNT + " INTEGER, " +
            COLUMN_BUDGET_PERIOD + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. Tạo các bảng
        db.execSQL(CREATE_TABLE_EXPENSES);
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_BUDGET);

        // 2. Thêm dữ liệu mẫu cho Category ngay lần đầu cài app
        insertDefaultCategories(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET);

        // Tạo lại từ đầu
        onCreate(db);
    }

    // Hàm hỗ trợ tạo danh mục mặc định
    private void insertDefaultCategories(SQLiteDatabase db) {
        String[] categories = {"Ăn uống", "Di chuyển", "Mua sắm", "Hóa đơn", "Giải trí", "Y tế", "Giáo dục", "Khác"};

        for (String name : categories) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CATEGORY_NAME, name);
            db.insert(TABLE_CATEGORY, null, values);
        }
    }
}