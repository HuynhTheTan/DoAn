package com.example.do_an.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an.R;
import com.example.do_an.adapter.AnalysisAdapter;
import com.example.do_an.dao.ExpenseDAO;
import com.example.do_an.model.Expense;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment {

    // View
    private TextView tvTotalIncome, tvTotalExpense, tvBalance;
    private TextView tvTrendAmount;
    private RecyclerView rcvAllocation;

    // Data
    private ExpenseDAO expenseDAO;
    private AnalysisAdapter analysisAdapter;
    private DecimalFormat formatter = new DecimalFormat("#,###đ");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        expenseDAO = new ExpenseDAO(requireContext());
        setupRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadStatistics();
    }

    private void initViews(View view) {
        tvTotalIncome = view.findViewById(R.id.tv_total_income);
        tvTotalExpense = view.findViewById(R.id.tv_total_expense);
        tvBalance = view.findViewById(R.id.tv_balance);
        tvTrendAmount = view.findViewById(R.id.tv_trend_amount);
        rcvAllocation = view.findViewById(R.id.rcv_allocation);
    }

    private void setupRecyclerView() {
        rcvAllocation.setLayoutManager(new LinearLayoutManager(getContext()));
        // Dùng list rỗng ban đầu
        analysisAdapter = new AnalysisAdapter(getContext(), new ArrayList<>());
        rcvAllocation.setAdapter(analysisAdapter);
    }

    private void loadStatistics() {
        // 1. Tính tổng
        long totalIncome = expenseDAO.getTotalAmountByType(1); // Thu
        long totalExpense = expenseDAO.getTotalAmountByType(0); // Chi
        long balance = totalIncome - totalExpense;

        // 2. Hiển thị lên giao diện
        tvTotalIncome.setText(formatter.format(totalIncome));
        tvTotalExpense.setText(formatter.format(totalExpense));
        tvTrendAmount.setText(formatter.format(totalExpense)); // Xu hướng tạm thời lấy tổng chi

        // Xử lý màu sắc số dư
        tvBalance.setText(formatter.format(balance));
        if (balance >= 0) {
            tvBalance.setText("+" + formatter.format(balance));
            tvBalance.setTextColor(getResources().getColor(R.color.income_green));
        } else {
            tvBalance.setTextColor(getResources().getColor(R.color.expense_red));
        }

        // 3. Load danh sách phân bổ (Tạm thời lấy list chi tiêu gần đây)
        // Khi nào có chức năng danh mục, sẽ thay bằng query GROUP BY category
        List<Expense> list = expenseDAO.getExpenses(10);
        analysisAdapter = new AnalysisAdapter(getContext(), list);
        rcvAllocation.setAdapter(analysisAdapter);
    }
}