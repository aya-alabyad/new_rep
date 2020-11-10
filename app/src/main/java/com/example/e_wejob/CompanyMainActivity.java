package com.example.e_wejob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CompanyMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_main);
    }

    public void AddJob(View view) {
        Intent i = new Intent(CompanyMainActivity.this, AddJobActivity.class);
        startActivity(i);
    }

    public void showCandidates(View view) {
        Intent i = new Intent(CompanyMainActivity.this, AddJobActivity.class);
        startActivity(i);
    }

}