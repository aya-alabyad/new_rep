package com.example.e_wejob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_wejob.models.Job;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CompanyJobsGalleryActivity extends AppCompatActivity {
    RequestQueue requestQueue;

    RecyclerView jobList;
    List<Job> jobs;
    String JsonURL = "https://dry-everglades-05566.herokuapp.com/api/jobs";

    SharedPreferences sharedPreferences;

    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_jobs_gallery);

        sharedPreferences = getSharedPreferences("e_job", MODE_PRIVATE);

        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.clear();
                myEdit.apply();
                Intent i = new Intent(CompanyJobsGalleryActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        jobList = findViewById(R.id.jobList);

        jobs = new ArrayList(1);

        new ShowJobsAsyncTask().execute();
    }

    class ShowJobsAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... v) {

            // Creates the Volley request queue
            requestQueue = Volley.newRequestQueue(CompanyJobsGalleryActivity.this);


            // Creating the JsonArrayRequest class called arrayreq, passing the required parameters
            //JsonURL is the URL to be fetched from
            JsonArrayRequest jobsRequest = new JsonArrayRequest(JsonURL,
                    // The second parameter Listener overrides the method onResponse() and passes
                    //JSONArray as a parameter

                    new Response.Listener<JSONArray>() {

                        // Takes the response from the JSON request
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.e("ooooo", response.toString());
                            try {
                                // Retrieves first JSON object in outer array

                                jobs = new ArrayList(response.length());
                                for (int i = 0; i < response.length(); i++) {
                                    //gets each JSON object within the JSON array
                                    JSONObject jsonObject = response.getJSONObject(i);

                                    // Retrieves the string labeled "colorName" and "hexValue",
                                    // and converts them into javascript objects
                                    int id = jsonObject.getInt("id");
                                    int company_id = jsonObject.getInt("company_id");
                                    String title = jsonObject.getString("title");
                                    String salary = jsonObject.getString("salary");
                                    String requiredEducationLevel = jsonObject.getString("requiredEducationLevel");
                                    int requiredExperienceYears = jsonObject.getInt("requiredExperienceYears");


                                    int id_from_pref = sharedPreferences.getInt("id", 0);
                                    if (company_id == id_from_pref) {
                                        Job j = new Job(id, company_id, title, salary, requiredEducationLevel, requiredExperienceYears);

                                        jobs.add(j);
                                    }
                                }
                                if (jobs.size() < 1) {
                                    Toast.makeText(CompanyJobsGalleryActivity.this, "No jobs Assigned Yet!", Toast.LENGTH_LONG).show();
                                }
                                CompanyJobItemAdapter jobItemAdapter = new CompanyJobItemAdapter(CompanyJobsGalleryActivity.this, jobs, new MyOnClick() {
                                    @Override
                                    public void onClick(int id) {
                                        Intent i = new Intent(CompanyJobsGalleryActivity.this, JobCandidatesActivity.class);
                                        i.putExtra("job_id", id + "");
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                                jobList.setAdapter(jobItemAdapter);
                            }
                            // Try and catch are included to handle any errors due to JSON
                            catch (JSONException e) {
                                // If an error occurs, this prints the error to the log
                                e.printStackTrace();
                            }
                        }
                    },
                    // The final parameter overrides the method onErrorResponse() and passes VolleyError
                    //as a parameter
                    new Response.ErrorListener() {
                        @Override
                        // Handles errors that occur due to Volley
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley", "Error");
                        }
                    }
            );
            // Adds the JSON array request "arrayreq" to the request queue
            requestQueue.add(jobsRequest);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

// update the UI after background processes completes
        }
    }
}