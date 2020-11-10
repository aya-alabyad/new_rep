package com.example.e_wejob;

import android.os.Bundle;
import android.util.Log;

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

public class JobGalleryActivity extends AppCompatActivity {

    RecyclerView jobList;
    List<Job> jobs;
    String JsonURL = "https://dry-everglades-05566.herokuapp.com/api/jobs";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_gallery);
        jobList = findViewById(R.id.jobList);

        jobs = new ArrayList(1);
//        JobItemAdapter jobItemAdapter = new JobItemAdapter(this, jobs);

        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);


        // Creating the JsonArrayRequest class called arrayreq, passing the required parameters
        //JsonURL is the URL to be fetched from
        JsonArrayRequest jobsRequest = new JsonArrayRequest(JsonURL,
                // The second parameter Listener overrides the method onResponse() and passes
                //JSONArray as a parameter

                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {
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
                                String created_at = jsonObject.getString("created_at");
                                String updated_at = jsonObject.getString("updated_at");

                                Job j = new Job(id, company_id, title, salary, requiredEducationLevel, requiredExperienceYears, created_at, updated_at);

                                jobs.add(j);
                            }

                            JobItemAdapter jobItemAdapter = new JobItemAdapter(JobGalleryActivity.this, jobs);
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
    }
}