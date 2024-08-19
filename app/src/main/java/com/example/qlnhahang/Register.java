package com.example.qlnhahang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlnhahang.Class.User;

public class Register extends AppCompatActivity {
    EditText txtusername, txtpass, txtconfirm;
    MyDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);
        myDatabase = new MyDatabase(this);
        txtusername = findViewById(R.id.etUsername);
        txtpass = findViewById(R.id.etPassword);
        txtconfirm = findViewById(R.id.etConfirmPassword);

    }
    public void register(View view) {
        String username = txtusername.getText().toString();
        String password = txtpass.getText().toString();
        String confirmPassword = txtconfirm.getText().toString();

        // Kiểm tra các trường có trống không
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu và xác nhận mật khẩu có khớp không
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(username, password);
        int result = myDatabase.findUser(user);

        if (result == 0) { // Người dùng chưa tồn tại
            myDatabase.addUser(user);
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
        } else if (result == 1) { // Tên đăng nhập đã tồn tại
            Toast.makeText(this, "Tên đăng nhập đã tồn tại!", Toast.LENGTH_SHORT).show();
        } else { // Tên đăng nhập đã tồn tại với mật khẩu khác
            Toast.makeText(this, "Tên đăng nhập đã tồn tại với mật khẩu khác!", Toast.LENGTH_SHORT).show();
        }
    }

}