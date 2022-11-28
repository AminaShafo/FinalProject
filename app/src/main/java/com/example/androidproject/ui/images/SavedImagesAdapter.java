package com.example.androidproject.ui.images;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.SavedData;

import java.util.List;

public class SavedImagesAdapter extends RecyclerView.Adapter<SavedImagesViewHolder> {
    Context context;
    List<Image> images;
    FragmentManager fragmentManager;

    public SavedImagesAdapter(Context context, List<Image> images, FragmentManager fragmentManager){
        this.context = context;
        this.images = images;
        this.fragmentManager = fragmentManager;
    }
    @NonNull
    @Override
    public SavedImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SavedImagesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_saved_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SavedImagesViewHolder holder, int position) {
        holder.photographerView.setText(images.get(position).photographer);
        Glide.with(context).load(images.get(position).url).into(holder.thumbnailView);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("deleteButton.onClick", "position '"  + "'");

                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                builder1.setMessage("Are you sure you want to delete this item?");

                builder1.setPositiveButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                                Toast.makeText(context, "Deleting image...", Toast.LENGTH_LONG).show();
                                SavedData.DeteleImage(0);
                                notifyItemRemoved(0);
                            }
                        });

                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
