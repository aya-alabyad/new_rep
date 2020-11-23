package com.example.e_wejob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CandidateMainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_main);

        sharedPreferences = getSharedPreferences("e_job", MODE_PRIVATE);

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.clear();
                myEdit.apply();
                Intent i = new Intent(CandidateMainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        TextView company = findViewById(R.id.candidateName);
        String name = sharedPreferences.getString("name", "null");
        company.setText("Welcome: " + name);
    }

    public void ShowAllJobs(View view) {
        Intent i = new Intent(CandidateMainActivity.this, JobGalleryActivity.class);
        startActivity(i);
    }

    public void showJobsbyYears(View view) {
        Intent i = new Intent(CandidateMainActivity.this, JobsByYearsActivity.class);
        startActivity(i);
    }

    public void showJobsbyEducationLevel(View view) {
        Intent i = new Intent(CandidateMainActivity.this, JobsByEducationLevelActivity.class);
        startActivity(i);
    }

    public void showSuitableJobs(View view) {
        Intent i = new Intent(CandidateMainActivity.this, SuitableJobsForCandidateActivity.class);
        startActivity(i);
    }
}