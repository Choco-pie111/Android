package com.example.manga.Home;

public class Item {
    private String name;
    private int imageResId; // Lưu ID ảnh trong drawable

    public Item(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
}
