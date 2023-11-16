package com.example.plant_disease_detection;
import okhttp3.*;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plant_disease_detection.ui.home.HomeFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plant_disease_detection.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    String prediction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void make_prediction(String crop_ID){
        if(HomeFragment.image_received==false) {
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
                    HomeFragment.img.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    byte[] bitmapData = bos.toByteArray();
                    // Write the byte array to the output stream
                    wr.write(bitmapData);

                    wr.writeBytes("\r\n--" + boundary + "\r\n");
                    wr.writeBytes("Content-Disposition: form-data; name=\"parameter\"\r\n\r\n");
                    wr.writeBytes(crop_ID);

                    wr.writeBytes("\r\n--" + boundary + "--\r\n");

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
                                    HomeFragment.status.setText("Can't Predict this Image, Please Retry");
                                    HomeFragment.result1.setText("");
                                    HomeFragment.result2.setText("");
                                }
                                else{
                                    String leafDisease=new JSONObject(content.toString()).getString("class");
                                    HomeFragment.result1.setText(new JSONObject(content.toString()).getString("class"));
                                    HomeFragment.result2.setText(String.format("%.2f",conf)+"%");
                                    HomeFragment.status.setText("Collecting Info from Web");
                                    displayInfo(leafDisease);
                                }
                            } catch (JSONException e) {
                                Log.d("MainActivity", "run: error");
                                Toast.makeText(MainActivity.this, "error in run", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                            Log.d("MainActivity", "Response: " + content.toString());
                            //Toast.makeText(MainActivity.this, "Response: " + prediction+" | "+crop_ID, Toast.LENGTH_LONG).show();

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void displayInfo(String leafDisease) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://fansan.pagekite.me/getInfo?content=" + leafDisease)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // Do something with the response.
                    final String gpt3Response = response.body().string().replace("\\n", "\n").replace("\"", "");;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Update the UI with the GPT-3 response
                            // For example, if you have a TextView to display the response:
                            if (gpt3Response != null && !gpt3Response.isEmpty()) {
                                HomeFragment.DiseaseDetails.setText(gpt3Response);
                                HomeFragment.status.setText("");
                            } else {
                                HomeFragment.DiseaseDetails.setText("");
                                HomeFragment.status.setText("Can't fetch Disease Details");
                            }
                        }
                    });
                }
            }
        });
    }
}