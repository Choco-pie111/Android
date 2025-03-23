package com.example.manga.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.manga.R;

public class UserActivity extends AppCompatActivity {
    private TextView txt_username;
    private Button btn_login, btn_register, btn_logout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegister = findViewById(R.id.btn_register);
        Button btnLogout = findViewById(R.id.btn_logout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        txt_username = findViewById(R.id.txt_username);
        sharedPreferences = getSharedPreferences("UserSF", MODE_PRIVATE);
        setSupportActionBar(toolbar);

        SharedPreferences userPrefs = getSharedPreferences("UserSF", MODE_PRIVATE);
        String username = userPrefs.getString("userName", null);
        if (username != null) {
            // Người dùng đã đăng nhập -> Hiển thị thông tin và ẩn nút Login/Register
            txt_username.setText("Username: " + username);
            txt_username.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
            btnRegister.setVisibility(View.GONE);
        } else {
            // Chưa đăng nhập -> Hiện nút Login/Register, ẩn thông tin user
            txt_username.setVisibility(View.GONE);
            btnLogout.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.VISIBLE);
        }
        // Chuyển sang màn hình Đăng nhập
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        // Chuyển sang màn hình Đăng ký
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear(); // Xóa session
            editor.apply();

            // Quay lại UserActivity để cập nhật giao diện
            Intent intent = new Intent(UserActivity.this, UserActivity.class);
            startActivity(intent);
            finish();
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Quay lại trang trước
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}