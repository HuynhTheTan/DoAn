package com.example.do_an.utils;

import android.Manifest;
import android.annotation.SuppressLint; // 1. Thêm import này
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.do_an.MainActivity;
import com.example.do_an.R;

public class DailyReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
    }

    // 2. Thêm dòng này để tắt báo lỗi đỏ
    @SuppressLint("MissingPermission")
    private void showNotification(Context context) {
        String channelId = "daily_reminder_channel";

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("Đừng quên ghi chép!")
                .setContentText("Hãy ghi lại chi tiêu hôm nay nhé.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Nhắc nhở hàng ngày", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager systemManager = context.getSystemService(NotificationManager.class);
            if (systemManager != null) {
                systemManager.createNotificationChannel(channel);
            }
        }

        // Kiểm tra quyền (Vẫn giữ đoạn này để App không bị crash lúc chạy)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return; // Nếu chưa có quyền thì thoát, không notify
            }
        }

        // Dòng này giờ sẽ hết đỏ nhờ @SuppressLint ở trên
        notificationManager.notify(1, builder.build());
    }
}