package com.example.androidproject.ui.images;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidproject.R;
import com.example.androidproject.databinding.FragmentImagesBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImagesFragment extends Fragment {

    private FragmentImagesBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<Image> images;
    private EditText search;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentImagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textImages;
        //imagesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        images = new ArrayList<Image>();

        Log.d("ImagesList.onCreate", "create list of images");

        recyclerView = root.findViewById(R.id.images_list);
        search = root.findViewById(R.id.search_text);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        adapter = new ImagesAdapter(getActivity(), images, fragmentManager);
        recyclerView.setAdapter(adapter);

        Button searchButton = root.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = search.getText().toString();
                Log.d("searchButton.onClick", "searching for images '" + s + "'");
                Toast.makeText(getActivity(), "Searching for '" + s + "'", Toast.LENGTH_LONG).show();

                // Creating a shared pref object
                // with a file name "MySharedPref"
                // in private mode
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                // write all the data entered by the user in SharedPreference and apply
                myEdit.putString("search", search.getText().toString());
                myEdit.apply();

                images.clear();

                getImagesFromPexels(s, getActivity(), recyclerView);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Fetch the stored data in onResume()
    // Because this is what will be called
    // when the app opens again
    @Override
    public void onResume() {
        super.onResume();

        // Fetching the stored data
        // from the SharedPreference
        SharedPreferences sh = getActivity().getSharedPreferences("MySharedPref", getActivity().MODE_PRIVATE);

        String s1 = sh.getString("search", "");
        // Setting the fetched data
        // in the EditTexts
        search.setText(s1);
    }

    public void getImagesFromPexels(String search, Context context, View view) {
        String url = "https://api.pexels.com/v1/search?query=";
        // Create a String request using Volley Library
        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url + search,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Process the JSON
                        try{
                            JSONArray responseArray = response.getJSONArray("photos");
                            // Loop through the array elements
                            for(int i=0;i<responseArray.length();i++){
                                // Get current json object
                                JSONObject img = responseArray.getJSONObject(i);

                                String photographer = img.getString("photographer");
                                int width = img.getInt("width");
                                int height = img.getInt("height");
                                int id = img.getInt("id");
                                JSONObject src = img.getJSONObject("src");
                                String url = src.getString("original");
                                Image image = new Image(id, photographer, url, width, height);
                                images.add(image);
                                adapter.notifyDataSetChanged();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Log.d("onErrorResponse", error.toString());
                        Snackbar.make(view, "Error...", Snackbar.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "563492ad6f9170000100000110a4593b0fcc4262a676241fc2b06cef");
                return params;
            }
        };

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }
}