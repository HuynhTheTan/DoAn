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

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private Context context;
    private List<Expense> expenseList;
    private int layoutId;

    // 1. Khai báo biến Listener (Để hứng sự kiện click)
    private OnItemClickListener listener;

    // 2. Tạo Interface (Khuôn mẫu cho sự kiện)
    public interface OnItemClickListener {
        void onEdit(Expense expense);   // Hàm xử lý khi bấm sửa
        void onDelete(Expense expense); // Hàm xử lý khi bấm xóa
    }

    // 3. Hàm để Fragment gọi vào và gán sự kiện
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Constructor
    public ExpenseAdapter(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
    }

    public void setData(List<Expense> list) {
        this.expenseList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        if (expense == null) return;

        // Gán dữ liệu lên giao diện
        holder.tvTitle.setText(expense.getName());
        holder.tvTime.setText(expense.getDate());

        // Format tiền tệ
        DecimalFormat formatter = new DecimalFormat("#,###đ");
        String formattedAmount = formatter.format(expense.getAmount());

        // Đổi màu sắc (Thu: Xanh, Chi: Đỏ)
        if (expense.getType() == 0) { // Chi tiêu
            holder.tvAmount.setText("- " + formattedAmount);
            holder.tvAmount.setTextColor(context.getResources().getColor(R.color.expense_red));
        } else { // Thu nhập
            holder.tvAmount.setText("+ " + formattedAmount);
            holder.tvAmount.setTextColor(context.getResources().getColor(R.color.income_green));
        }

        // 4. Bắt sự kiện Click vào item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEdit(expense);
            }
        });

        // 5. Bắt sự kiện Giữ lâu (Long Click) vào item
        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onDelete(expense);
            }
            return true; // Trả về true để báo là đã xử lý xong
        });
    }

    @Override
    public int getItemCount() {
        if (expenseList != null) return expenseList.size();
        return 0;
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvTime, tvAmount;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ View từ XML
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvAmount = itemView.findViewById(R.id.tv_amount);
        }
    }
}