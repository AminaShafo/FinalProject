package com.example.androidproject.ui.images;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.androidproject.R;
import com.example.androidproject.SavedData;
import com.example.androidproject.databinding.FragmentImagesBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SavedImagesFragment extends Fragment {

    private FragmentImagesBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<Image> images;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_images, container, false);

        images = SavedData.getAllImages();

        Log.d("SavedImagesList.onCreate", "create list of images");

        recyclerView = view.getRootView().findViewById(R.id.saved_images_list);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        adapter = new SavedImagesAdapter(getActivity(), images, fragmentManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}