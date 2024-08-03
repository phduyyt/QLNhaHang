package com.example.qlnhahang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlnhahang.DailyMenu.DailyMenu;
import com.example.qlnhahang.Employees.QLNhanVien;
import com.example.qlnhahang.MenuItems.QLMonAn;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main);
    }
    public void qlnhanvien(View view){
        Intent nhanvien = new Intent(Main.this, QLNhanVien.class);
        startActivity(nhanvien);
    }
    public void qlmonan(View view){
        Intent monan = new Intent(Main.this, QLMonAn.class);
        startActivity(monan);
    }
    public void menutheongay(View view){
        Intent menutheongay = new Intent(Main.this, DailyMenu.class);
        startActivity(menutheongay);
    }

}