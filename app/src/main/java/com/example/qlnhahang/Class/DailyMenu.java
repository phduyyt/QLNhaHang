package com.example.qlnhahang.Class;

public class DailyMenu {
    private int dailyMenuId;
    private String menuDate;

    public DailyMenu(int dailyMenuId, String menuDate) {
        this.dailyMenuId = dailyMenuId;
        this.menuDate = menuDate;
    }

    // Getters and Setters
    public int getDailyMenuId() {
        return dailyMenuId;
    }

    public void setDailyMenuId(int dailyMenuId) {
        this.dailyMenuId = dailyMenuId;
    }

    public String getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(String menuDate) {
        this.menuDate = menuDate;
    }
}
