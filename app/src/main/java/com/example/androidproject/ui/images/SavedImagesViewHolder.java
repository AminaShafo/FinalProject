package com.example.androidproject.ui.images;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;

public class SavedImagesViewHolder extends RecyclerView.ViewHolder {
    TextView photographerView;
    ImageView thumbnailView;
    Button deleteButton;

    public SavedImagesViewHolder(@NonNull View itemView) {
        super(itemView);
        photographerView = itemView.findViewById(R.id.photographer_text_view);
        thumbnailView = itemView.findViewById(R.id.thumbnail_view);
        deleteButton = itemView.findViewById(R.id.delete_button);
    }
}
