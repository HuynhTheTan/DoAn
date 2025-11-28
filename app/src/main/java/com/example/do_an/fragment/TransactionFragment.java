package com.example.do_an.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an.R;
import com.example.do_an.adapter.ExpenseAdapter;
import com.example.do_an.dao.ExpenseDAO;
import com.example.do_an.model.Expense;

import java.util.List;

public class TransactionFragment extends Fragment {

    private RecyclerView rvTransactions;
    private TextView tvWeek, tvMonth, tvYear, tvAll;
    private ExpenseDAO expenseDAO;
    private ExpenseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        expenseDAO = new ExpenseDAO(requireContext());
        setupRecyclerView();
        setupFilterEvents();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void initViews(View view) {
        rvTransactions = view.findViewById(R.id.rv_transactions);
        tvWeek = view.findViewById(R.id.tv_week);
        tvMonth = view.findViewById(R.id.tv_month);
        tvYear = view.findViewById(R.id.tv_year);
        tvAll = view.findViewById(R.id.tv_all);
    }

    private void setupRecyclerView() {
        rvTransactions.setLayoutManager(new LinearLayoutManager(getContext()));
        // Sử dụng layout item_transaction
        adapter = new ExpenseAdapter(getContext(), R.layout.item_transaction);

        adapter.setOnItemClickListener(new ExpenseAdapter.OnItemClickListener() {
            @Override
            public void onEdit(Expense expense) {
                Toast.makeText(getContext(), "Sửa: " + expense.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDelete(Expense expense) {
                // 1. Gọi hàm xóa trong DAO
                boolean isDeleted = expenseDAO.deleteExpense(expense.getId());

                if (isDeleted) {
                    Toast.makeText(getContext(), "Đã xóa: " + expense.getName(), Toast.LENGTH_SHORT).show();
                    // 2. Load lại danh sách để cập nhật giao diện
                    loadData();
                } else {
                    Toast.makeText(getContext(), "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rvTransactions.setAdapter(adapter);
    }

    private void loadData() {
        List<Expense> list = expenseDAO.getExpenses(-1); // Lấy tất cả
        adapter.setData(list);
    }

    private void setupFilterEvents() {
        View.OnClickListener listener = v -> {
            resetFilterUI();
            v.setBackgroundResource(R.drawable.bg_chip_selected);
            ((TextView) v).setTextColor(Color.WHITE);

            if (v.getId() == R.id.tv_all) {
                loadData();
            } else {
                // Logic lọc theo ngày sẽ làm sau
                Toast.makeText(getContext(), "Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
            }
        };

        tvWeek.setOnClickListener(listener);
        tvMonth.setOnClickListener(listener);
        tvYear.setOnClickListener(listener);
        tvAll.setOnClickListener(listener);
    }

    private void resetFilterUI() {
        // Reset màu các nút về mặc định
        int colorGray = getResources().getColor(R.color.text_gray);
        tvWeek.setBackgroundResource(R.drawable.bg_chip_normal);
        tvWeek.setTextColor(colorGray);
        tvMonth.setBackgroundResource(R.drawable.bg_chip_normal);
        tvMonth.setTextColor(colorGray);
        tvYear.setBackgroundResource(R.drawable.bg_chip_normal);
        tvYear.setTextColor(colorGray);
        tvAll.setBackgroundResource(R.drawable.bg_chip_normal);
        tvAll.setTextColor(colorGray);
    }
}