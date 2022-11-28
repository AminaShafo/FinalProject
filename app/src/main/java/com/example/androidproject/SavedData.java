package com.example.androidproject;

import android.app.Application;

import com.example.androidproject.ui.images.Image;

import java.util.ArrayList;
import java.util.List;

public class SavedData extends Application {
    private static List<Image> imagesList = new ArrayList<Image>();

    public SavedData() {
    }

    public static List<Image> getAllImages() {
        return imagesList;
    }

    public static void AddImage(Image image)
    {
        imagesList.add(image);
    }

    public static void DeteleImage(int i) {
        imagesList.remove(i);
    }
}
