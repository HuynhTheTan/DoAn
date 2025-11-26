package com.example.do_an.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.do_an.R;

public class ReportFragment extends Fragment {

    private TextView tvWeek, tvMonth, tvYear;
    private CardView cardWeek, cardMonth, cardYear;

    public ReportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvWeek = view.findViewById(R.id.tv_week);
        tvMonth = view.findViewById(R.id.tv_month);
        tvYear = view.findViewById(R.id.tv_year);

        cardWeek = view.findViewById(R.id.card_week);
        cardMonth = view.findViewById(R.id.card_month);
        cardYear = view.findViewById(R.id.card_year);

        cardWeek.setOnClickListener(v -> {
            updateTabUI(cardWeek, tvWeek); // Hàm cập nhật giao diện
            Toast.makeText(getContext(), "Đã chọn: Tuần", Toast.LENGTH_SHORT).show();
            // TODO: Load dữ liệu báo cáo theo TUẦN ở đây
        });

        cardMonth.setOnClickListener(v -> {
            updateTabUI(cardMonth, tvMonth);
            Toast.makeText(getContext(), "Đã chọn: Tháng", Toast.LENGTH_SHORT).show();
            // TODO: Load dữ liệu báo cáo theo THÁNG ở đây
        });

        cardYear.setOnClickListener(v -> {
            updateTabUI(cardYear, tvYear);
            Toast.makeText(getContext(), "Đã chọn: Năm", Toast.LENGTH_SHORT).show();
            // TODO: Load dữ liệu báo cáo theo NĂM ở đây
        });

        View btnBack = view.findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> Toast.makeText(getContext(), "Nút Back", Toast.LENGTH_SHORT).show());
        }

        TextView tvTotalExpense = view.findViewById(R.id.tv_total_expense);
        if(tvTotalExpense != null) tvTotalExpense.setText("4.500.000đ");
    }

    private void updateTabUI(CardView selectedCard, TextView selectedText) {
        resetTab(cardWeek, tvWeek);
        resetTab(cardMonth, tvMonth);
        resetTab(cardYear, tvYear);

        selectedCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));
        selectedCard.setCardElevation(6f); // 2dp ~ 6f

        selectedText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_dark));
        selectedText.setTypeface(null, Typeface.BOLD);
    }

    private void resetTab(CardView card, TextView text) {
        card.setCardBackgroundColor(Color.TRANSPARENT);
        card.setCardElevation(0f);

        text.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_gray));
        text.setTypeface(null, Typeface.NORMAL);
    }
}