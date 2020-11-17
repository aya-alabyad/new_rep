package com.example.e_wejob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences("e_job", MODE_PRIVATE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean is_Login = sharedPreferences.getBoolean("is_login", false);
                if (is_Login) {
                    String type = sharedPreferences.getString("type", "");
                    if (type.equals("Candidate")) {
                        Intent i = new Intent(SplashActivity.this, CandidateMainActivity.class);
                        startActivity(i);
                        finish();
                    } else if (type.equals("Company")) {
                        Intent i = new Intent(SplashActivity.this, CompanyMainActivity.class);
                        startActivity(i);
                        finish();
                    }

                } else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 2000);
    }
}