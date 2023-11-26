package com.example.plant_disease_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

//        // Set Title
//        getSupportActionBar().setTitle("SignUp SignIn App");
//
        // Open Login Activity
        ImageView login_button = findViewById(R.id.login_button_splash);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Open Register Activity
        ImageView register_button = findViewById(R.id.register_button_splash);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashScreen.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}