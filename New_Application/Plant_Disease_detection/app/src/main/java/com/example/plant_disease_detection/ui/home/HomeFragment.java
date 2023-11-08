package com.example.plant_disease_detection.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.plant_disease_detection.R;
import com.example.plant_disease_detection.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TextView textView1, textView5;
    private int CAMERA_REQ_CODE;
    private int GALLERY_REQ_CODE;
    ImageView imgDisplay;
    TextView result1, result2;


    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView7, imageView8;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Initialize views
        imgDisplay = findViewById(R.id.imgDisplay);
        textView1 = root.findViewById(R.id.textView1);
        textView5 = root.findViewById(R.id.textView5);
        imageView1 = root.findViewById(R.id.imageView1);
        imageView2 = root.findViewById(R.id.imageView2);
        imageView3 = root.findViewById(R.id.imageView3);
        imageView4 = root.findViewById(R.id.imageView4);
        imageView5 = root.findViewById(R.id.imageView5);
        imageView7 = root.findViewById(R.id.imageView7);
        imageView8 = root.findViewById(R.id.imageView8);


        // Set onClick listeners for ImageViews
        imageView1.setOnClickListener(v -> {
            textView1.setText("APPLE");
            textView5.setText("Content for Image 1 - TextView3");
            CAMERA_REQ_CODE = 10;
            GALLERY_REQ_CODE = 20;
        });

        imageView2.setOnClickListener(v -> {
            textView1.setText("POTATO");
            textView5.setText("Content for Image 2 - TextView3");
            CAMERA_REQ_CODE = 30;
            GALLERY_REQ_CODE = 40;
        });

        imageView3.setOnClickListener(v -> {
            textView1.setText("CORN");
            textView5.setText("Content for Image 3 - TextView3");
            CAMERA_REQ_CODE = 50;
            GALLERY_REQ_CODE = 60;
        });

        imageView4.setOnClickListener(v -> {
            textView1.setText("TOMATO");
            textView5.setText("Content for Image 4 - TextView3");
            CAMERA_REQ_CODE = 70;
            GALLERY_REQ_CODE = 80;
        });

        imageView5.setOnClickListener(v -> {
            textView1.setText("RICE");
            textView5.setText("Content for Image 5 - TextView3");
            CAMERA_REQ_CODE = 90;
            GALLERY_REQ_CODE = 100;
        });

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}