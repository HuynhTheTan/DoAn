package com.example.do_an.model;

import java.io.Serializable;

public class Expense implements Serializable {
    private int id;
    private long amount;       // Số tiền
    private String name;       // Tên/Mô tả (Ví dụ: Ăn trưa, Tiền nhà...)
    private String date;       // Ngày tháng
    private int type;          // 0: Chi tiêu, 1: Thu nhập
    private int categoryId;    // ID danh mục

    // Constructor rỗng (bắt buộc)
    public Expense() {
    }

    // Constructor dùng để thêm mới
    public Expense(long amount, String name, String date, int type, int categoryId) {
        this.amount = amount;
        this.name = name;
        this.date = date;
        this.type = type;
        this.categoryId = categoryId;
    }

    // --- CÁC HÀM GETTER (Để lấy dữ liệu ra) ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }

    // 👇 ĐÂY RỒI: Hàm getName() mà code Adapter đang gọi
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getType() { return type; }
    public void setType(int type) { this.type = type; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
}