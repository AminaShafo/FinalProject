package com.example.androidproject.ui.images;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.SavedData;


public class ImageDetailsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_details, container, false);

        String url = getArguments().getString("url");
        int width = getArguments().getInt("width");
        int height = getArguments().getInt("height");


        TextView widthView = (TextView) view.findViewById(R.id.width_text);
        TextView heightView = (TextView) view.findViewById(R.id.height_text);
        TextView urlView = (TextView) view.findViewById(R.id.url_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);

        widthView.setText("Width: " + width);
        heightView.setText("Height: " + height);
        urlView.setText(url);
        urlView.setMovementMethod(LinkMovementMethod.getInstance());
        Glide.with(this).load(url).into(imageView);

        Button save = view.findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("saveButton.onClick", "save image");
                Toast.makeText(getView().getContext(), "Saving image...", Toast.LENGTH_LONG).show();

                Image image = new Image(1, "photographer", url, width, height);
                SavedData.AddImage(image);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getView().getContext());
                builder1.setMessage("Total number of saved images: " + SavedData.getAllImages().size());

                builder1.setPositiveButton(
                        "Go back",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                                view.getRootView().findViewById(R.id.images_list).setVisibility(View.VISIBLE);
                                view.getRootView().findViewById(R.id.search_button).setVisibility(View.VISIBLE);
                                view.getRootView().findViewById(R.id.search_text).setVisibility(View.VISIBLE);

                                view.getRootView().findViewById(R.id.image_details_fragment).setVisibility(View.GONE);

                            }
                        });

                builder1.setNegativeButton("Go to saved images", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        Fragment fragment = new SavedImagesFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.images_list_and_search, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        view.getRootView().findViewById(R.id.images_list).setVisibility(View.GONE);
                        view.getRootView().findViewById(R.id.search_button).setVisibility(View.GONE);
                        view.getRootView().findViewById(R.id.search_text).setVisibility(View.GONE);
                        view.getRootView().findViewById(R.id.image_details_fragment).setVisibility(View.GONE);
                    }
                });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        return view;
    }
}