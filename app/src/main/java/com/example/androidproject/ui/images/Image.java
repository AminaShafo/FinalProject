package com.example.androidproject.ui.images;

public class Image {
    int id;
    String photographer;
    String url;
    int width;
    int height;

    public Image(int id, String photographer, String url, int width, int height) {
        this.id = id;
        this.photographer = photographer;
        this.url = url;
        this.width = width;
        this.height = height;
    }
}
