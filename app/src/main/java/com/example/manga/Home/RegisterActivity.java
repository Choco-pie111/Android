package com.example.manga.Home;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.manga.R;
import com.example.manga.System.DBHelper;

public class RegisterActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SharedPreferences userPrefs;
    private static final String PREFS_USER = "UserSF";
    EditText edtName, edtEmail, edtPassword;
    Button btnRegister;
    TextView txtLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper=new DBHelper(this);
        userPrefs= getSharedPreferences(PREFS_USER, MODE_PRIVATE);
        setContentView(R.layout.activity_register);


        // Ánh xạ ID
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        // Xử lý sự kiện nút đăng ký
        btnRegister.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                dbHelper.registerUser(name,email, password);
                SharedPreferences.Editor editor = userPrefs.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.apply();

                finish();
            }
        });

        // Chuyển sang màn hình đăng nhập
        txtLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }
}

