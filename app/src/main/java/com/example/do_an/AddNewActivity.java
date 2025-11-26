package com.example.do_an;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNewActivity extends AppCompatActivity {

    private final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new);

        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.header), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            v.setPadding(v.getPaddingLeft(), systemBars.top, v.getPaddingRight(), v.getPaddingBottom());
            return insets;
        });

        TextView tvExpenseDate = findViewById(R.id.tv_expense_date);
        TextView tvIncomeDate = findViewById(R.id.tv_income_date);

        LinearLayout layoutExpense = findViewById(R.id.layout_expense);
        LinearLayout layoutIncome = findViewById(R.id.layout_income);

        TextView btnExpense = findViewById(R.id.btn_expense);
        TextView btnIncome  = findViewById(R.id.btn_income);

        btnExpense.setOnClickListener(v -> {
            layoutExpense.setVisibility(View.VISIBLE);
            layoutIncome.setVisibility(View.GONE);

            btnExpense.setBackgroundColor(getResources().getColor(R.color.primary_pink));
            btnExpense.setTextColor(getResources().getColor(R.color.white));

            btnIncome.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btnIncome.setTextColor(getResources().getColor(R.color.text_gray));
        });

        btnIncome.setOnClickListener(v -> {
            layoutExpense.setVisibility(View.GONE);
            layoutIncome.setVisibility(View.VISIBLE);

            btnIncome.setBackgroundColor(getResources().getColor(R.color.income_green));
            btnIncome.setTextColor(getResources().getColor(R.color.white));

            btnExpense.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btnExpense.setTextColor(getResources().getColor(R.color.text_gray));
        });
        tvExpenseDate.setOnClickListener(v -> showDatePicker(tvExpenseDate));
        tvIncomeDate.setOnClickListener(v -> showDatePicker(tvIncomeDate));
        updateLabel(tvExpenseDate);
        updateLabel(tvIncomeDate);
    }

    private void showDatePicker(TextView tvDate) {
        new DatePickerDialog(AddNewActivity.this,
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel(tvDate);
                },
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel(TextView tvDate) {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        tvDate.setText(sdf.format(myCalendar.getTime()));
    }
}
