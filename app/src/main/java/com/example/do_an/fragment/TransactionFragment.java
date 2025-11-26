package com.example.do_an.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.do_an.R;

public class TransactionFragment extends Fragment {

    private TextView tvWeek, tvMonth, tvYear, tvAll;

    public TransactionFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        tvWeek = view.findViewById(R.id.tv_week);
        tvMonth = view.findViewById(R.id.tv_month);
        tvYear = view.findViewById(R.id.tv_year);
        tvAll = view.findViewById(R.id.tv_all);

        tvWeek.setOnClickListener(v -> {
            tvWeek.setBackgroundResource(R.drawable.bg_chip_selected);
            tvMonth.setBackgroundResource(R.drawable.bg_chip_normal);
            tvYear.setBackgroundResource(R.drawable.bg_chip_normal);
            tvAll.setBackgroundResource(R.drawable.bg_chip_normal);
        });

        tvMonth.setOnClickListener(v -> {
            tvWeek.setBackgroundResource(R.drawable.bg_chip_normal);
            tvMonth.setBackgroundResource(R.drawable.bg_chip_selected);
            tvYear.setBackgroundResource(R.drawable.bg_chip_normal);
            tvAll.setBackgroundResource(R.drawable.bg_chip_normal);
        });

        tvYear.setOnClickListener(v -> {
            tvWeek.setBackgroundResource(R.drawable.bg_chip_normal);
            tvMonth.setBackgroundResource(R.drawable.bg_chip_normal);
            tvYear.setBackgroundResource(R.drawable.bg_chip_selected);
            tvAll.setBackgroundResource(R.drawable.bg_chip_normal);
        });

        tvAll.setOnClickListener(v -> {
            tvWeek.setBackgroundResource(R.drawable.bg_chip_normal);
            tvMonth.setBackgroundResource(R.drawable.bg_chip_normal);
            tvYear.setBackgroundResource(R.drawable.bg_chip_normal);
            tvAll.setBackgroundResource(R.drawable.bg_chip_selected);
        });

        return view;
    }
}
