package com.example.spendingmanagement.model;

import java.util.Date;

public class Transaction {
    private int id;
    private Category fromCategory;
    private Category toCategory;
    private String fromType;
    private String toType;
    private String date;
    private int amount;

    public Transaction() {

    }

    public Transaction(Category fromCategory, Category toCategory, String fromType, String toType, String date, int amount) {
        this.fromCategory = fromCategory;
        this.toCategory = toCategory;
        this.fromType = fromType;
        this.toType = toType;
        this.date = date;
        this.amount = amount;
    }

    public Transaction(int id, Category fromCategory, Category toCategory, String fromType, String toType, String date, int amount) {
        this.id = id;
        this.fromCategory = fromCategory;
        this.toCategory = toCategory;
        this.fromType = fromType;
        this.toType = toType;
        this.date = date;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getFromCategory() {
        return fromCategory;
    }

    public void setFromCategory(Category fromCategory) {
        this.fromCategory = fromCategory;
    }

    public Category getToCategory() {
        return toCategory;
    }

    public void setToCategory(Category toCategory) {
        this.toCategory = toCategory;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
