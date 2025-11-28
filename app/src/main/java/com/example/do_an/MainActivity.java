package com.example.do_an;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.do_an.fragment.HomeFragment;
import com.example.do_an.fragment.ReportFragment;
import com.example.do_an.fragment.TransactionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);

        // Mặc định load HomeFragment khi mở app
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        // Bắt sự kiện click vào menu
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_transaction) { // Lưu ý: ID này phải khớp với bottom_nav_menu.xml
                selectedFragment = new TransactionFragment();
            } else if (itemId == R.id.nav_report) {
                selectedFragment = new ReportFragment();
            } else if (itemId == R.id.nav_profile) {
                // Tạm thời chưa có Profile, dùng Home đỡ hoặc tạo Fragment rỗng
                selectedFragment = new HomeFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;
        });

        // Setup Floating Action Button ở HomeFragment hoạt động
        // (Logic chuyển màn hình đã nằm trong HomeFragment rồi)
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}