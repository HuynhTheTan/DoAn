package com.example.do_an.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an.AddNewActivity;
import com.example.do_an.R;
import com.example.do_an.adapter.AnalysisAdapter;
import com.example.do_an.adapter.ExpenseAdapter;
import com.example.do_an.dao.ExpenseDAO;
import com.example.do_an.model.Expense;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    // Khai báo các View
    private TextView tvBalance, tvIncome, tvExpense, tvChartTotal;
    private TextView tvTitleTrans, tvTitleAnalysis;
    private CardView cardAnalysis;
    private RecyclerView rcvRecent, rcvAnalysis;
    private FloatingActionButton fabAdd;

    // Logic & Dữ liệu
    private ExpenseDAO expenseDAO;
    private ExpenseAdapter recentAdapter;
    private AnalysisAdapter analysisAdapter;
    private DecimalFormat formatter = new DecimalFormat("#,###đ");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Ánh xạ View
        initViews(view);

        // 2. Khởi tạo Database
        expenseDAO = new ExpenseDAO(requireContext());

        // 3. Cài đặt danh sách
        setupRecyclerViews();

        // 4. Sự kiện nút Thêm mới
        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddNewActivity.class));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Tải lại dữ liệu mỗi khi màn hình hiện lên (để cập nhật số liệu mới nhất)
        loadData();
    }

    private void initViews(View view) {
        tvBalance = view.findViewById(R.id.tv_balance_amount);
        tvIncome = view.findViewById(R.id.tv_income_amount);
        tvExpense = view.findViewById(R.id.tv_expense_amount);
        tvChartTotal = view.findViewById(R.id.tv_chart_total);

        tvTitleTrans = view.findViewById(R.id.tv_title_trans);
        tvTitleAnalysis = view.findViewById(R.id.tv_title_analysis); // Có thể null nếu XML chưa có ID này
        cardAnalysis = view.findViewById(R.id.card_analysis);

        rcvRecent = view.findViewById(R.id.rcv_recent_transactions);
        rcvAnalysis = view.findViewById(R.id.rcv_category_analysis);
        fabAdd = view.findViewById(R.id.fab_add);
    }

    private void setupRecyclerViews() {
        // Cài đặt List Giao dịch gần đây
        rcvRecent.setLayoutManager(new LinearLayoutManager(getContext()));
        // Sử dụng layout item_home cho gọn
        recentAdapter = new ExpenseAdapter(getContext(), R.layout.item_home);
        rcvRecent.setAdapter(recentAdapter);

        // Cài đặt List Phân tích (Tạm thời)
        if (rcvAnalysis != null) {
            rcvAnalysis.setLayoutManager(new LinearLayoutManager(getContext()));
            analysisAdapter = new AnalysisAdapter(getContext(), new ArrayList<>());
            rcvAnalysis.setAdapter(analysisAdapter);
        }
    }

    private void loadData() {
        // 1. Tính toán tổng tiền
        long totalIncome = expenseDAO.getTotalAmountByType(1); // 1: Thu nhập
        long totalExpense = expenseDAO.getTotalAmountByType(0); // 0: Chi tiêu
        long balance = totalIncome - totalExpense;

        // 2. Hiển thị số liệu
        tvIncome.setText(formatter.format(totalIncome));
        tvExpense.setText(formatter.format(totalExpense));
        tvBalance.setText(formatter.format(balance));
        tvChartTotal.setText(formatter.format(totalExpense));

        // 3. Lấy danh sách giao dịch gần đây (5 cái mới nhất)
        List<Expense> recentList = expenseDAO.getExpenses(5);

        if (recentList == null || recentList.isEmpty()) {
            // Nếu không có dữ liệu -> Ẩn bớt cho gọn
            if (tvTitleTrans != null) tvTitleTrans.setVisibility(View.GONE);
            rcvRecent.setVisibility(View.GONE);
        } else {
            // Có dữ liệu -> Hiện lên
            if (tvTitleTrans != null) tvTitleTrans.setVisibility(View.VISIBLE);
            rcvRecent.setVisibility(View.VISIBLE);
            recentAdapter.setData(recentList);
        }

        // 4. Hiển thị phân tích (Logic ẩn hiện)
        boolean hasExpense = totalExpense > 0;
        if (cardAnalysis != null) {
            cardAnalysis.setVisibility(hasExpense ? View.VISIBLE : View.GONE);
        }
        if (tvTitleAnalysis != null) {
            tvTitleAnalysis.setVisibility(hasExpense ? View.VISIBLE : View.GONE);
        }

        // Tạm thời hiển thị list recent vào bảng phân tích để demo
        if (hasExpense && analysisAdapter != null) {
            analysisAdapter = new AnalysisAdapter(getContext(), recentList);
            rcvAnalysis.setAdapter(analysisAdapter);
        }
    }
}