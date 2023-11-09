package com.example.plant_disease_detection.ui.home;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
    TextView result1, result2;
    Bitmap img;
    boolean image_received=false;


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
            textView5.setText("Content for Image 2 - TextView3");
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
            textView5.setText("Content for Image 3 - TextView3");
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
            textView5.setText("Content for Image 4 - TextView3");
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
            textView1.setText("RICE");
            textView5.setText("Content for Image 5 - TextView3");
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
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            if(requestCode==CAMERA_REQ_CODE_ImageView1){
                //for camera of imageview1 = apple

                Bitmap img1 = (Bitmap)(data.getExtras().get("data"));
                imgDisplay.setImageBitmap(img1);
            }
            else if(requestCode==CAMERA_REQ_CODE_ImageView2){
                //for camera of imageview2 = potato

                Bitmap img2 = (Bitmap)(data.getExtras().get("data"));
                imgDisplay.setImageBitmap(img2);
            }
            else if(requestCode==CAMERA_REQ_CODE_ImageView3){
                //for camera of imageview2 = potato

                Bitmap img3 = (Bitmap)(data.getExtras().get("data"));
                imgDisplay.setImageBitmap(img3);
            }
            else if(requestCode==CAMERA_REQ_CODE_ImageView4){
                //for camera of imageview2 = potato

                Bitmap img4 = (Bitmap)(data.getExtras().get("data"));
                imgDisplay.setImageBitmap(img4);
            }
            else if(requestCode==CAMERA_REQ_CODE_ImageView5){
                //for camera of imageview2 = potato

                Bitmap img5 = (Bitmap)(data.getExtras().get("data"));
                imgDisplay.setImageBitmap(img5);
            }
            else if(requestCode==GALLERY_REQ_CODE_ImageView1){
                //for gallery of imageview1 = apple

                imgDisplay.setImageURI(data.getData());
            }
            else if(requestCode==GALLERY_REQ_CODE_ImageView2){
                //for gallery of imageview2 = potato

                imgDisplay.setImageURI(data.getData());
            }
            else if(requestCode==GALLERY_REQ_CODE_ImageView3){
                //for gallery of imageview3 = corn

                imgDisplay.setImageURI(data.getData());
            }
            else if(requestCode==GALLERY_REQ_CODE_ImageView4){
                //for gallery of imageview4 = tomato

                imgDisplay.setImageURI(data.getData());
            }
            else if(requestCode==GALLERY_REQ_CODE_ImageView5){
                //for gallery of imageview5 = rice

                imgDisplay.setImageURI(data.getData());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}