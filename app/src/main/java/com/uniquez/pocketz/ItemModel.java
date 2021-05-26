package com.uniquez.pocketz;

import java.util.Date;

public class ItemModel {
    private String name;
    private int qty;
    private Date expDate;
    private String category;
    private String storage;

    //Non-param constructor
    public ItemModel() {
    }

    //Param constructor without exp date
    public ItemModel(String name, int qty, String category, String storage) {
        this.name = name;
        this.qty = qty;
        this.category = category;
        this.storage = storage;
    }

    //Param constructor
    public ItemModel(String name, int qty, Date expDate, String category, String storage) {
        this.name = name;
        this.qty = qty;
        this.expDate = expDate;
        this.category = category;
        this.storage = storage;
    }

    //getter and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    //to string method
    @Override
    public String toString() {
        return "ItemModel{" +
                "name='" + name + '\'' +
                ", qty=" + qty +
                ", expDate=" + expDate +
                ", category='" + category + '\'' +
                ", storage='" + storage + '\'' +
                '}';
    }

}
