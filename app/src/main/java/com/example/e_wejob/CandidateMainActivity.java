package com.example.e_wejob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CandidateMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_main);
    }

    public void ShowAllJobs(View view) {
        Intent i = new Intent(CandidateMainActivity.this,JobGalleryActivity.class);
        startActivity(i);
    }

    public void showJobsbyYears(View view) {
        Intent i = new Intent(CandidateMainActivity.this,JobGalleryActivity.class);
        startActivity(i);
    }
    public void showJobsbyEducationLevel(View view) {
        Intent i = new Intent(CandidateMainActivity.this,JobGalleryActivity.class);
        startActivity(i);
    }
}