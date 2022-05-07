package com.example.mireamarket.model;

import android.view.View;

import com.example.mireamarket.FunctionInterface;

public class MenuItem {
    int id, category;
    String img, tittle, price;
    FunctionInterface onClickListener;

    public MenuItem(int id, String img, String tittle, String price, int category, FunctionInterface onClickListener) {
        this.id = id;
        this.img = img;
        this.tittle = tittle;
        this.price = price;
        this.category = category;
        this.onClickListener = onClickListener;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public FunctionInterface getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(FunctionInterface onClickListener) {
        this.onClickListener = onClickListener;
    }
}
