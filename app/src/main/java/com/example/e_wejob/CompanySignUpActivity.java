package com.example.e_wejob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CompanySignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comapny_sign_up);
        Button btnAddJob = findViewById(R.id.btnAddJob);
        btnAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompanySignUpActivity.this, AddJobActivity.class);
                startActivity(intent);
            }
        });
    }
}