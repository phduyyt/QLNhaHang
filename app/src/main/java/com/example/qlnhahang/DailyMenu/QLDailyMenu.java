package com.example.qlnhahang.DailyMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlnhahang.Adapter.DailyMenuAdapter;
import com.example.qlnhahang.Class.DailyMenu;
import com.example.qlnhahang.Main;
import com.example.qlnhahang.MyDatabase;
import com.example.qlnhahang.R;

import java.util.ArrayList;

public class QLDailyMenu extends AppCompatActivity {
    private MyDatabase myDatabase;
    ListView listView;
    DailyMenuAdapter adapter;
    ArrayList<DailyMenu> listDailyMenus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daily_menu);
        myDatabase = new MyDatabase(this);
        listView = findViewById(R.id.listdailymenu);
        listDailyMenus = myDatabase.getAllDailyMenus();
        adapter = new DailyMenuAdapter(this, R.layout.daily_menu, listDailyMenus);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DailyMenu d = listDailyMenus.get(i);
                Intent intent = new Intent(QLDailyMenu.this, EditDailyMenu.class);
                intent.putExtra("date", d.getDate());
                startActivity(intent);
            }
        });
    }
    public void quaylai(View view){
        Intent ql = new Intent(QLDailyMenu.this, Main.class);
        startActivity(ql);
    }
    public void themdailymenu(View view){
        Intent themdailymenu = new Intent(QLDailyMenu.this, AddDailyMenu.class);
        startActivity(themdailymenu);
    }
}