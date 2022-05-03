package com.example.spendingmanagement.model;

public class Category {
    private int id;
    private String name;
    private String type;
    private int color;
    private int colorCode;
    private int icon;
    private int amount;

    public Category() {

    }

    public Category(int id, String name, String type, int color, int colorCode, int icon) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.color = color;
        this.colorCode = colorCode;
        this.icon = icon;
    }

    public Category(String name, String type, int color, int colorCode, int icon) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.colorCode = colorCode;
        this.icon = icon;
    }

    public Category(int id, String name, String type, int color, int colorCode, int icon, int amount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.color = color;
        this.colorCode = colorCode;
        this.icon = icon;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
