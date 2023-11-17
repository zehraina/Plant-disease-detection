package com.example.plant_disease_detection.ui.home;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.plant_disease_detection.MainActivity;
import com.example.plant_disease_detection.R;
import com.example.plant_disease_detection.databinding.FragmentHomeBinding;

import java.io.IOException;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TextView textView1, textView5;
    private final int CAMERA_REQ_CODE_ImageView1 = 10;
    private final int GALLERY_REQ_CODE_ImageView1 = 20;
    private final int CAMERA_REQ_CODE_ImageView2 = 30;
    private final int GALLERY_REQ_CODE_ImageView2 = 40;
    private final int CAMERA_REQ_CODE_ImageView3 = 50;
    private final int GALLERY_REQ_CODE_ImageView3 = 60;
    private final int CAMERA_REQ_CODE_ImageView4 = 70;
    private final int GALLERY_REQ_CODE_ImageView4 = 80;
    private final int CAMERA_REQ_CODE_ImageView5 = 90;
    private final int GALLERY_REQ_CODE_ImageView5 = 100;
    ImageView imgDisplay;

    public static TextView result1, result2,DiseaseDetails,status,serverStatus;
    public static Bitmap img;
    Uri imageUri;
    public static boolean image_received=false;
    String crop_ID="-1";
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView7, imageView8;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Initialize views
        imgDisplay = root.findViewById(R.id.imgDisplay);

        textView1 = root.findViewById(R.id.textView1);
        DiseaseDetails = root.findViewById(R.id.DiseaseDetails);
        imageView1 = root.findViewById(R.id.imageView1);
        imageView2 = root.findViewById(R.id.imageView2);
        imageView3 = root.findViewById(R.id.imageView3);
        imageView4 = root.findViewById(R.id.imageView4);
        imageView5 = root.findViewById(R.id.imageView5);
        imageView7 = root.findViewById(R.id.imageView7);
        imageView8 = root.findViewById(R.id.imageView8);

        //prediction textView
        serverStatus=root.findViewById(R.id.serverStatus);
        status=root.findViewById(R.id.status);
        status.setText("");
        result1=root.findViewById(R.id.result1);
        result2=root.findViewById(R.id.result2);
        // Set onClick listeners for ImageViews
        imageView1.setOnClickListener(v -> {
            textView1.setText("APPLE");
            crop_ID="3";
            HomeFragment.DiseaseDetails.setText("");
            HomeFragment.result1.setText("");
            HomeFragment.result2.setText("");
            imageView7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(iCamera, CAMERA_REQ_CODE_ImageView1);
                }
            });
            imageView8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iGallery = new Intent(Intent.ACTION_PICK);
                    iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(iGallery, GALLERY_REQ_CODE_ImageView1);
                }
            });
        });

        imageView2.setOnClickListener(v -> {
            textView1.setText("POTATO");
            crop_ID="0";
            HomeFragment.DiseaseDetails.setText("");
            HomeFragment.result1.setText("");
            HomeFragment.result2.setText("");
            imageView7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(iCamera, CAMERA_REQ_CODE_ImageView2);

                }
            });
            imageView8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iGallery = new Intent(Intent.ACTION_PICK);
                    iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(iGallery, GALLERY_REQ_CODE_ImageView2);


                }
            });
        });

        imageView3.setOnClickListener(v -> {
            textView1.setText("CORN");
            crop_ID="2";
            HomeFragment.DiseaseDetails.setText("");
            HomeFragment.result1.setText("");
            HomeFragment.result2.setText("");
            imageView7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(iCamera, CAMERA_REQ_CODE_ImageView3);
                }
            });
            imageView8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iGallery = new Intent(Intent.ACTION_PICK);
                    iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(iGallery, GALLERY_REQ_CODE_ImageView3);
                }
            });
        });

        imageView4.setOnClickListener(v -> {
            textView1.setText("TOMATO");
            crop_ID="1";
            HomeFragment.DiseaseDetails.setText("");
            HomeFragment.result1.setText("");
            HomeFragment.result2.setText("");
            imageView7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(iCamera, CAMERA_REQ_CODE_ImageView4);
                }
            });
            imageView8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iGallery = new Intent(Intent.ACTION_PICK);
                    iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(iGallery, GALLERY_REQ_CODE_ImageView4);
                }
            });
        });

        imageView5.setOnClickListener(v -> {
            textView1.setText("GRAPE");
            crop_ID="4";
            HomeFragment.DiseaseDetails.setText("");
            HomeFragment.result1.setText("");
            HomeFragment.result2.setText("");
            imageView7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(iCamera, CAMERA_REQ_CODE_ImageView5);
                }
            });
            imageView8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iGallery = new Intent(Intent.ACTION_PICK);
                    iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(iGallery, GALLERY_REQ_CODE_ImageView5);
                }
            });
        });

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        MainActivity mainActivity = (MainActivity) getActivity();
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            image_received=true;
            HomeFragment.DiseaseDetails.setText("");
            HomeFragment.result1.setText("");
            HomeFragment.result2.setText("");
            HomeFragment.status.setText("Image Received");
            if((requestCode/10)%2!=0){
                img = (Bitmap)(data.getExtras().get("data"));
                imgDisplay.setImageBitmap(img);
            }

            else if((requestCode/10)%2==0){
                Uri imageUri = data.getData();
                try {
                    img = MediaStore.Images.Media.getBitmap(mainActivity.getContentResolver(), imageUri);
                    imgDisplay.setImageBitmap(img);
                    image_received=true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(mainActivity!=null){
                mainActivity.make_prediction(crop_ID);
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

