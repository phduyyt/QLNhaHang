package com.example.qlnhahang.Tables;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlnhahang.Adapter.BanAnMenuAdapter;
import com.example.qlnhahang.Class.MenuItems;
import com.example.qlnhahang.MyDatabase;
import com.example.qlnhahang.R;

import java.util.ArrayList;

public class EditTable extends AppCompatActivity {
    MyDatabase myDatabase;
    EditText etTableName, etTableCapacity;
    Spinner spinnerTableStatus;
    ArrayAdapter<MenuItems> menuItemsAdapter;
    ArrayList<MenuItems> menuItemsList;
    ListView dailymenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_table);

        myDatabase = new MyDatabase(this);
        etTableName = findViewById(R.id.etTableName);
        etTableCapacity = findViewById(R.id.etTableCapacity);
        spinnerTableStatus = findViewById(R.id.spinnerTableStatus);
        dailymenu = findViewById(R.id.dailymenu);

        int number = Integer.parseInt(getIntent().getStringExtra("number"));
        String capacity = getIntent().getStringExtra("capacity");
        String status = getIntent().getStringExtra("status");

        etTableName.setText(String.valueOf(number));
        etTableCapacity.setText(capacity);

        ArrayList<String> tableStatusList = new ArrayList<>();
        tableStatusList.add("Trống");
        tableStatusList.add("Đang sử dụng");
        tableStatusList.add("Đã đặt");


        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, tableStatusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTableStatus.setAdapter(statusAdapter);

        // Set the selected item
        int statusPosition = tableStatusList.indexOf(status);
        spinnerTableStatus.setSelection(statusPosition);

        menuItemsList = myDatabase.getMenuItemsForDate("13-08-2024");
        menuItemsAdapter = new BanAnMenuAdapter(this, R.layout.layout_table_menu, menuItemsList);
        dailymenu.setAdapter(menuItemsAdapter);
    }
}
