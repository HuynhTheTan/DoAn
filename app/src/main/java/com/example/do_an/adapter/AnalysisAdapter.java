package com.example.do_an.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an.R;
import com.example.do_an.model.Expense;

import java.text.DecimalFormat;
import java.util.List;

public class AnalysisAdapter extends RecyclerView.Adapter<AnalysisAdapter.ViewHolder> {
    private Context context;
    private List<Expense> list;

    public AnalysisAdapter(Context context, List<Expense> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = list.get(position);
        holder.tvName.setText(expense.getName());

        DecimalFormat formatter = new DecimalFormat("#,###đ");
        holder.tvAmount.setText(formatter.format(expense.getAmount()));

        // Tạm thời để trống phần trăm
        holder.tvPercent.setText("");
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPercent, tvAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_category_name);
            tvPercent = itemView.findViewById(R.id.tv_category_percent);
            tvAmount = itemView.findViewById(R.id.tv_category_amount);
        }
    }
}