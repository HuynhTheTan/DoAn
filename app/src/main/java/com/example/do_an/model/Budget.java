package com.example.do_an.model;

public class Budget {
    private int id;
    private long amount;
    private String period; // Ví dụ: "11/2023"

    public Budget() {
    }

    public Budget(long amount, String period) {
        this.amount = amount;
        this.period = period;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}