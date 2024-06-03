package fpt.hieudmph47182.bookstoreapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import fpt.hieudmph47182.bookstoreapplication.databinding.ActivitySplashScreenBinding;

@SuppressLint("CustomSplashScreen")
public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fpt.hieudmph47182.bookstoreapplication.databinding.
                ActivitySplashScreenBinding binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler().postDelayed(() -> {
            startActivity(new Intent(ActivitySplashScreen.this, ActivityLogin.class));
            finish();
        },1500);
    }
}