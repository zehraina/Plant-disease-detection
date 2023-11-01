package com.example.plantdiseasedetectionapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class MainActivity extends AppCompatActivity {
    private final int CAMERA_REQ_CODE =100;
    ImageView imgCamera;
    String encodedImage;
    byte[] byteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgCamera = findViewById(R.id.imgCamera);
        ImageView imageView3 = findViewById(R.id.imageView3);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(iCamera, CAMERA_REQ_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode==CAMERA_REQ_CODE){
                //for camera

                final Bitmap img = (Bitmap)(data.getExtras().get("data"));
                imgCamera.setImageBitmap(img);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Need to CHANGE this URL repeatedly for different ngrok server instances
                            URL url = new URL("https://f486-2409-4081-9e1d-c64f-5b05-991e-c84a-2766.ngrok.io/predict");
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
                                    Log.d("MainActivity", "Response: " + content.toString());
                                    Toast.makeText(MainActivity.this, "Response: " + content.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }
}