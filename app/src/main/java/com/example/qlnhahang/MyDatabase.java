package com.example.qlnhahang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.qlnhahang.Class.Employees;
import com.example.qlnhahang.Class.MenuItems;
import com.example.qlnhahang.Class.User;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QLNhaHang.db";
    private static final int DATABASE_VERSION = 1;
    // Tên bảng và các cột
    private static final String TABLE_USER = "user";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String TABLE_EMPLOYEES = "employees";
    private static final String EMPLOYEE_ID = "employee_id";
    private static final String FULL_NAME = "full_name";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String POSITION = "position";
    private static final String SALARY = "salary";

    private static final String TABLE_TABLES = "tables";
    private static final String TABLE_ID = "table_id";
    private static final String TABLE_NUMBER = "table_number";
    private static final String CAPACITY = "capacity";
    private static final String STATUS = "status";

    private static final String TABLE_MENU_ITEMS = "menu_items";
    private static final String MENU_ITEM_ID = "menu_item_id";
    private static final String MENU_ITEM_NAME = "menu_item_name";
    private static final String MENU_ITEM_DESCRIPTION = "menu_item_description";
    private static final String MENU_ITEM_PRICE = "menu_item_price";
    private static final String MENU_ITEM_IMAGE = "image";

    private static final String TABLE_DAILY_MENU = "daily_menu";
    private static final String DAILY_MENU_ID = "daily_menu_id";
    private static final String MENU_DATE = "menu_date";

    private static final String TABLE_TABLE_MENU_ITEMS = "table_menu_items";
    private static final String TABLE_MENU_ITEM_ID = "table_menu_item_id";
    private static final String TABLE_MENU_ITEM_TABLE_ID = "table_id";
    private static final String TABLE_MENU_ITEM_MENU_ITEM_ID = "menu_item_id";
    private static final String TABLE_MENU_ITEM_QUANTITY = "quantity";
    private static final String TABLE_MENU_ITEM_ORDER_DATE = "order_date";


    // Câu lệnh tạo bảng
    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng người dùng
        String createTableUser = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT)",
                TABLE_USER, USERNAME, PASSWORD);
        db.execSQL(createTableUser);

        // Tạo bảng nhân viên
        String createTableEmployees = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, " +
                        "%s TEXT, %s TEXT, %s TEXT, %s DECIMAL)",
                TABLE_EMPLOYEES, EMPLOYEE_ID, FULL_NAME, PHONE_NUMBER, POSITION, SALARY);
        db.execSQL(createTableEmployees);

        // Tạo bảng bàn ăn
        String createTableTables = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s INTEGER, %s INTEGER, %s TEXT)",
                TABLE_TABLES, TABLE_ID, TABLE_NUMBER, CAPACITY, STATUS);
        db.execSQL(createTableTables);

        // Tạo bảng món ăn
        String createTableMenuItems = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT, %s TEXT, %s DECIMAL, %s TEXT)",
                TABLE_MENU_ITEMS, MENU_ITEM_ID, MENU_ITEM_NAME, MENU_ITEM_DESCRIPTION, MENU_ITEM_PRICE, MENU_ITEM_IMAGE);
        db.execSQL(createTableMenuItems);

        // Tạo bảng menu hàng ngày
        String createTableDailyMenu = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT)",
                TABLE_DAILY_MENU, DAILY_MENU_ID, MENU_DATE);
        db.execSQL(createTableDailyMenu);

        // Tạo bảng liên kết bàn ăn và món ăn
        String createTableTableMenuItems = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s INTEGER, %s INTEGER, %s INTEGER, %s DATETIME)",
                TABLE_TABLE_MENU_ITEMS, TABLE_MENU_ITEM_ID, TABLE_MENU_ITEM_TABLE_ID,
                TABLE_MENU_ITEM_MENU_ITEM_ID, TABLE_MENU_ITEM_QUANTITY, TABLE_MENU_ITEM_ORDER_DATE);
        db.execSQL(createTableTableMenuItems);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa các bảng cũ và tạo lại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TABLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_MENU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TABLE_MENU_ITEMS);

        onCreate(db);
    }

    public int findUser(User u) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM user WHERE username=?";
        Cursor cursor = db.rawQuery(query, new String[]{u.getUsername()});

        if (cursor.moveToFirst()) {
            String foundPassword = cursor.getString(1);
            if (foundPassword.equals(u.getPassword())) {
                cursor.close();
                db.close();
                return 1;  // Người dùng được tìm thấy và mật khẩu khớp
            } else {
                cursor.close();
                db.close();
                return -1; // Người dùng được tìm thấy nhưng mật khẩu không khớp
            }
        } else {
            cursor.close();
            db.close();
            return 0; // Người dùng không được tìm thấy
        }
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, user.getUsername());
        values.put(PASSWORD, user.getPassword());
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public ArrayList<Employees> getAllEmployees() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Employees> employeesList = new ArrayList<>();
        String query = "SELECT * FROM employees";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int employeeId = cursor.getInt(0);
                String fullName = cursor.getString(1);
                String phoneNumber = cursor.getString(2);
                String position = cursor.getString(3);
                double salary = cursor.getDouble(4);
                Employees employee = new Employees(employeeId, fullName, phoneNumber, position, salary);
                employeesList.add(employee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return employeesList;
    }

    public void addEmployee(Employees employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMPLOYEE_ID, employee.getEmployeeId());
        values.put(FULL_NAME, employee.getFullName());
        values.put(PHONE_NUMBER, employee.getPhoneNumber());
        values.put(POSITION, employee.getPosition());
        values.put(SALARY, employee.getSalary());
        db.insert(TABLE_EMPLOYEES, null, values);
        db.close();
    }

    public void deleteEmployee(int employeeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEES, EMPLOYEE_ID + "=?", new String[]{String.valueOf(employeeId)});
        db.close();
    }

    public void updateEmployee(Employees employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMPLOYEE_ID, employee.getEmployeeId());
        values.put(FULL_NAME, employee.getFullName());
        values.put(PHONE_NUMBER, employee.getPhoneNumber());
        values.put(POSITION, employee.getPosition());
        values.put(SALARY, employee.getSalary());
        db.update(TABLE_EMPLOYEES, values, EMPLOYEE_ID + "=?", new String[]{String.valueOf(employee.getEmployeeId())});
        db.close();
    }

    public void AddMenuItem(MenuItems menuItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MENU_ITEM_NAME, menuItem.getName());
        values.put(MENU_ITEM_DESCRIPTION, menuItem.getDescription());
        values.put(MENU_ITEM_PRICE, menuItem.getPrice());
        values.put(MENU_ITEM_IMAGE, menuItem.getImage());
        db.insert(TABLE_MENU_ITEMS, null, values);
        db.close();
    }

    public void updateMenuItem(MenuItems menuItems) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MENU_ITEM_ID, menuItems.getMenuItemId());
        values.put(MENU_ITEM_NAME, menuItems.getName());
        values.put(MENU_ITEM_DESCRIPTION, menuItems.getDescription());
        values.put(MENU_ITEM_PRICE, menuItems.getPrice());
        values.put(MENU_ITEM_IMAGE, menuItems.getImage());
        db.update(TABLE_MENU_ITEMS, values, MENU_ITEM_ID + "=?", new String[]{String.valueOf(menuItems.getMenuItemId())});
        db.close();
    }

    public void deleteMenuItem(int menuItemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MENU_ITEMS, MENU_ITEM_ID + "=?", new String[]{String.valueOf(menuItemId)});
        db.close();
    }

    public ArrayList<MenuItems> getAllMenuItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MenuItems> menuItemsList = new ArrayList<>();
        String query = "SELECT * FROM menu_items";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                double price = cursor.getDouble(3);
                String image = cursor.getString(4);
                MenuItems menuItem = new MenuItems(id, name, price, description, image);
                menuItemsList.add(menuItem);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return menuItemsList;
    }

}
