package com.example.e_wejob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminMainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        sharedPreferences = getSharedPreferences("e_job", MODE_PRIVATE);

        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.clear();
                myEdit.apply();
                Intent i = new Intent(AdminMainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        TextView adminName = findViewById(R.id.adminName);
        String cName = sharedPreferences.getString("admin_name", "admin");
        adminName.setText("Welcome: " + cName);
    }

    /*public void AddJob(View view) {
        Intent i = new Intent(AdminMainActivity.this, AddJobActivity.class);
        startActivity(i);
    }*/

    public void AddCompany(View view) {
        Intent i = new Intent(AdminMainActivity.this, AddCompanyActivity.class);
        startActivity(i);
    }

}