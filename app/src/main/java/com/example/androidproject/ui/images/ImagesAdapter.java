package com.example.androidproject.ui.images;

import android.content.Context;
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

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesViewHolder> {
    Context context;
    List<Image> images;
    FragmentManager fragmentManager;

    public ImagesAdapter(Context context, List<Image> images, FragmentManager fragmentManager){
        this.context = context;
        this.images = images;
        this.fragmentManager = fragmentManager;
    }
    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImagesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {
        holder.photographerView.setText(images.get(position).photographer);
        Glide.with(context).load(images.get(position).url).into(holder.thumbnailView);
        holder.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("detailsButton.onClick", "position '"  + "'");
                Toast.makeText(context, "Loading details...", Toast.LENGTH_LONG).show();

                view.getRootView().findViewById(R.id.images_list).setVisibility(View.GONE);
                view.getRootView().findViewById(R.id.search_button).setVisibility(View.GONE);
                view.getRootView().findViewById(R.id.search_text).setVisibility(View.GONE);

                Bundle bundle = new Bundle();
                bundle.putString("url", images.get(holder.getBindingAdapterPosition()).url);
                bundle.putInt("width", images.get(holder.getBindingAdapterPosition()).width);
                bundle.putInt("height", images.get(holder.getBindingAdapterPosition()).height);

                Fragment imageDetailsFragment = new ImageDetailsFragment();
                imageDetailsFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.images_list_and_search, imageDetailsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
