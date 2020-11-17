package com.example.e_wejob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CompanyMainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_main);
        sharedPreferences = getSharedPreferences("e_job", MODE_PRIVATE);

        btnLogout=findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.clear();
                myEdit.apply();
                Intent i = new Intent(CompanyMainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        TextView company = findViewById(R.id.companyName);
        String cName = sharedPreferences.getString("cName", "null");
        company.setText("Welcome: " + cName);


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