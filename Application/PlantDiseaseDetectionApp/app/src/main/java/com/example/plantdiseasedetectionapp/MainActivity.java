package com.example.plantdiseasedetectionapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import android.graphics.Bitmap;
import android.widget.TextView;
import android.widget.Toast;
import com.example.plantdiseasedetectionapp.R;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    private final int CAMERA_REQ_CODE = 100;
    private final int GALLERY_REQ_CODE = 200;
    ImageView imgCamera;
    String encodedImage;
    byte[] byteArray;
    String prediction;

    Bitmap img;
    boolean image_received=false;

    TextView result1, result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result1=findViewById(R.id.result1);
        result2=findViewById(R.id.result2);

        imgCamera = findViewById(R.id.imgCamera);
        ImageView imageView2 = findViewById(R.id.imageView2);
        ImageView imageView3 = findViewById(R.id.imageView3);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(iCamera, CAMERA_REQ_CODE);
            }
        });

    }

    public void make_prediction(View view){
        if(image_received==false) {
            Toast.makeText(this, "Please Select an Image First!!!.", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "make_prediction: Please Select an Image First!!!.");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Need to CHANGE this URL repeatedly for different ngrok server instances
                    URL url = new URL("https://fansan.pagekite.me/predict");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);

                    String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW"; // You can use any string here
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                    DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                    wr.writeBytes("--" + boundary + "\r\n");
                    wr.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"image.jpg\"\r\n");
                    wr.writeBytes("Content-Type: image/jpeg\r\n\r\n");

                    // Convert the bitmap to a byte array
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    img.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    byte[] bitmapData = bos.toByteArray();

                    // Write the byte array to the output stream
                    wr.write(bitmapData);
                    wr.writeBytes("\r\n--" + boundary + "--\r\n");
                    wr.flush();
                    wr.close();

                    int responseCode = conn.getResponseCode();
                    Log.d("MainActivity", "Response code: " + responseCode);

                    // Read the response
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder content = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }

                    // Close connections
                    in.close();
                    conn.disconnect();

                    // Display a toast with the response
                    runOnUiThread(new Runnable() {
                        public void run() {
                            prediction =content.toString();

                            try {
                                double conf=Double.parseDouble(new JSONObject(content.toString()).getString("confidence"));
                                Log.d("MainActivity", conf+"");
                                if(conf<60){
                                    result1.setText("Can't Detect Disease Type from the given Image");
                                    result2.setText("Please Choose the Right Crop, or a better Image");
                                }
                                else{
                                    result1.setText(new JSONObject(content.toString()).getString("class"));
                                    result2.setText(String.format("%.2f",conf)+"%");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d("MainActivity", "Response: " + content.toString());
                            //Toast.makeText(MainActivity.this, "Response: " + prediction, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            if(requestCode==GALLERY_REQ_CODE){
                //for gallery
                Uri imageUri = data.getData();
                try {
                    img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    imgCamera.setImageBitmap(img);
                    image_received=true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(requestCode==CAMERA_REQ_CODE){
                //for camera
                img = (Bitmap)(data.getExtras().get("data"));
                imgCamera.setImageBitmap(img);
                image_received=true;
            }

        }
    }
}