package com.example.do_an;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.do_an.dao.ExpenseDAO;
import com.example.do_an.model.Expense;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNewActivity extends AppCompatActivity {

    // Khai báo các View (Giao diện)
    private TextView btnExpenseTab, btnIncomeTab;
    private LinearLayout layoutExpense, layoutIncome;

    // Các ô nhập liệu bên Tab Chi tiêu
    private EditText etExpenseAmount, etExpenseDesc;
    private TextView tvExpenseDate;
    private Button btnSaveExpense;

    // Các ô nhập liệu bên Tab Thu nhập
    private EditText etIncomeAmount, etIncomeDesc;
    private TextView tvIncomeDate;
    private Button btnSaveIncome;

    // Xử lý logic
    private ExpenseDAO expenseDAO;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new); // Phải chắc chắn file giao diện tên là add_new.xml

        // Khởi tạo cơ sở dữ liệu và công cụ ngày tháng
        expenseDAO = new ExpenseDAO(this);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // 1. Ánh xạ (Kết nối code Java với giao diện XML)
        initViews();

        // 2. Cài đặt sự kiện (Bấm nút, chọn ngày...)
        setupEvents();

        // Mặc định hiển thị ngày hôm nay lên giao diện
        String today = dateFormat.format(calendar.getTime());
        tvExpenseDate.setText(today);
        tvIncomeDate.setText(today);
    }

    private void initViews() {
        // Các nút chuyển Tab
        btnExpenseTab = findViewById(R.id.btn_expense);
        btnIncomeTab = findViewById(R.id.btn_income);
        layoutExpense = findViewById(R.id.layout_expense);
        layoutIncome = findViewById(R.id.layout_income);

        // Ánh xạ bên Chi tiêu (Nếu báo đỏ R.id... hãy kiểm tra lại file XML)
        etExpenseAmount = findViewById(R.id.et_expense_amount);
        etExpenseDesc = findViewById(R.id.et_expense_desc);
        tvExpenseDate = findViewById(R.id.tv_expense_date);
        btnSaveExpense = findViewById(R.id.btn_save_expense);

        // Ánh xạ bên Thu nhập
        etIncomeAmount = findViewById(R.id.et_income_amount);
        etIncomeDesc = findViewById(R.id.et_income_desc);
        tvIncomeDate = findViewById(R.id.tv_income_date);
        btnSaveIncome = findViewById(R.id.btn_save_income);

        // Nút Quay lại
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void setupEvents() {
        // Bấm nút Tab -> Chuyển giao diện
        btnExpenseTab.setOnClickListener(v -> switchTab(true));
        btnIncomeTab.setOnClickListener(v -> switchTab(false));

        // Bấm vào ngày -> Hiện lịch chọn
        tvExpenseDate.setOnClickListener(v -> showDatePicker(tvExpenseDate));
        tvIncomeDate.setOnClickListener(v -> showDatePicker(tvIncomeDate));

        // Bấm nút Lưu -> Gọi hàm saveTransaction
        btnSaveExpense.setOnClickListener(v -> saveTransaction(0)); // 0 = Chi tiêu
        btnSaveIncome.setOnClickListener(v -> saveTransaction(1));  // 1 = Thu nhập
    }

    private void switchTab(boolean isExpense) {
        if (isExpense) {
            // Đang chọn Chi tiêu: Hiện form Chi tiêu, Ẩn form Thu nhập
            layoutExpense.setVisibility(View.VISIBLE);
            layoutIncome.setVisibility(View.GONE);

            // Đổi màu nút bấm
            btnExpenseTab.setBackgroundColor(getColor(R.color.primary_pink));
            btnExpenseTab.setTextColor(Color.WHITE);

            btnIncomeTab.setBackgroundColor(Color.TRANSPARENT);
            btnIncomeTab.setTextColor(ContextCompat.getColor(this, R.color.text_gray));
        } else {
            // Đang chọn Thu nhập
            layoutExpense.setVisibility(View.GONE);
            layoutIncome.setVisibility(View.VISIBLE);

            btnIncomeTab.setBackgroundColor(getColor(R.color.primary_green));
            btnIncomeTab.setTextColor(Color.WHITE);

            btnExpenseTab.setBackgroundColor(Color.TRANSPARENT);
            btnExpenseTab.setTextColor(ContextCompat.getColor(this, R.color.text_gray));
        }
    }

    private void showDatePicker(TextView textView) {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            textView.setText(dateFormat.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void saveTransaction(int type) {
        String amountStr, desc, date;

        // Lấy dữ liệu từ form tương ứng
        if (type == 0) { // Chi tiêu
            amountStr = etExpenseAmount.getText().toString().trim();
            desc = etExpenseDesc.getText().toString().trim();
            date = tvExpenseDate.getText().toString();
        } else { // Thu nhập
            amountStr = etIncomeAmount.getText().toString().trim();
            desc = etIncomeDesc.getText().toString().trim();
            date = tvIncomeDate.getText().toString();
        }

        // Kiểm tra dữ liệu trống
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số tiền!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (desc.isEmpty()) {
            desc = (type == 0) ? "Chi tiêu" : "Thu nhập";
        }

        try {
            long amount = Long.parseLong(amountStr);

            // Tạo đối tượng Expense (Sử dụng Constructor 5 tham số, không cần ID)
            // amount: số tiền, desc: mô tả, date: ngày, type: loại, 1: id danh mục mặc định
            Expense expense = new Expense(amount, desc, date, type, 1);

            // Lưu vào Database
            long result = expenseDAO.insertExpense(expense);

            if (result != -1) {
                Toast.makeText(this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
                finish(); // Đóng màn hình này để quay về trang chủ
            } else {
                Toast.makeText(this, "Lỗi hệ thống, không lưu được!", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Số tiền nhập vào không hợp lệ!", Toast.LENGTH_SHORT).show();
        }
    }
}