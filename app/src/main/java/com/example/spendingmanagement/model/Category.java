package com.example.spendingmanagement.model;

public class Category {
    private int id;
    private String name;
    private String type;
    private int color;
    private int icon;

    public Category() {

    }

    public Category(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Category(String name, String type, int color, int icon) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.icon = icon;
    }

    public Category(int id, String name, String type, int color, int icon) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.color = color;
        this.icon = icon;
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
