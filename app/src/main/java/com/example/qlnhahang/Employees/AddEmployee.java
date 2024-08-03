package com.example.qlnhahang.Employees;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlnhahang.Class.Employees;
import com.example.qlnhahang.MyDatabase;
import com.example.qlnhahang.R;

import java.util.ArrayList;

public class AddEmployee extends AppCompatActivity {
    private MyDatabase myDatabase;
    private EditText editTextId, editTextName, editTextPhone, editTextPosition, editTextSalary;
    ArrayList<Employees> listEmployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_employee);
        editTextId = findViewById(R.id.editTextId);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPosition = findViewById(R.id.editTextPosition);
        editTextSalary = findViewById(R.id.editTextSalary);
        myDatabase = new MyDatabase(this);
        listEmployee = myDatabase.getAllEmployees();
        myDatabase = new MyDatabase(this);

    }
    public void quaylai(View view){
        Intent quaylai = new Intent(AddEmployee.this, QLNhanVien.class);
        startActivity(quaylai);
    }
    public void themnhanvien(View view){
        try{
            boolean check = true;
            for(Employees e: listEmployee){
                if(e.getEmployeeId() == Integer.parseInt(editTextId.getText().toString())){
                    check = false;
                    break;
                }
            }
            if(check){
                int id = Integer.parseInt(editTextId.getText().toString());
                String name = editTextName.getText().toString();
                String phone = editTextPhone.getText().toString();
                String position = editTextPosition.getText().toString();
                double salary = Double.parseDouble(editTextSalary.getText().toString());
                Employees employee = new Employees(id,name,phone,position,salary);
                myDatabase.addEmployee(employee);
                Intent quaylai = new Intent(AddEmployee.this,QLNhanVien.class);
                startActivity(quaylai);
            }
            else Toast.makeText(this,"ID đã tồn tại",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this,"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
        }
    }
}