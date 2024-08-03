package com.example.qlnhahang.MenuItems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlnhahang.Adapter.MonAnAdapter;
import com.example.qlnhahang.Class.MenuItems;
import com.example.qlnhahang.Main;
import com.example.qlnhahang.MyDatabase;
import com.example.qlnhahang.R;

import java.util.ArrayList;

public class QLMonAn extends AppCompatActivity {
    private MyDatabase myDatabase;
    private ListView listmonan;
    private MonAnAdapter adapter;
    private ArrayList<MenuItems> arrMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qlmon_an);

        myDatabase = new MyDatabase(this);
        listmonan = findViewById(R.id.listmonan);

        // Load menu items and set up adapter
        loadMenuItems();

        listmonan.setOnItemClickListener((adapterView, view, i, l) -> {
            MenuItems selectedItem = arrMenu.get(i);
            Intent intent = new Intent(QLMonAn.this, EditMonAn.class);
            intent.putExtra("id", String.valueOf(selectedItem.getMenuItemId()));
            intent.putExtra("name", selectedItem.getName());
            intent.putExtra("price", selectedItem.getPrice());
            intent.putExtra("description", selectedItem.getDescription());
            intent.putExtra("image", selectedItem.getImage());
            startActivity(intent);
        });
    }

    private void loadMenuItems() {
        try {
            arrMenu = myDatabase.getAllMenuItems();
            if (arrMenu != null) {
                adapter = new MonAnAdapter(QLMonAn.this, R.layout.layout_monan, arrMenu);
                listmonan.setAdapter(adapter);
            } else {
                Toast.makeText(this, "Không có món ăn nào trong cơ sở dữ liệu.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi tải danh sách món ăn.", Toast.LENGTH_SHORT).show();
        }
    }

    public void themmonan(View view) {
        Intent themmonan = new Intent(QLMonAn.this, AddMOnAn.class);
        startActivity(themmonan);
    }

    public void quaylai(View view) {
        Intent quaylai = new Intent(QLMonAn.this, Main.class);
        startActivity(quaylai);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list when returning from other activities
        loadMenuItems();
    }
}
