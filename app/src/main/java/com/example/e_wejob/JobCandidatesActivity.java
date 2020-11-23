package com.example.e_wejob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_wejob.models.Candidate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JobCandidatesActivity extends AppCompatActivity {
    RecyclerView candidatesList;
    List<Candidate> candidates;
    String JsonURL = "https://dry-everglades-05566.herokuapp.com/api/getSuitableCandidates";
    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    String job_id = "";
    Button btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_candidates);
        sharedPreferences = getSharedPreferences("e_job", MODE_PRIVATE);

        Intent i = getIntent();
        job_id = i.getStringExtra("job_id");
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.clear();
                myEdit.apply();
                Intent i = new Intent(JobCandidatesActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        candidatesList = findViewById(R.id.candidatesList);

        candidates = new ArrayList(1);
//        JobItemAdapter jobItemAdapter = new JobItemAdapter(this, jobs);

        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);
        TextView noCandidates = findViewById(R.id.noCandidates);


        // Creating the JsonArrayRequest class called arrayreq, passing the required parameters
        //JsonURL is the URL to be fetched from
        JsonObjectRequest jobsCandidatesRequest = new JsonObjectRequest(JsonURL + "?Id=" + job_id,
                // The second parameter Listener overrides the method onResponse() and passes
                //JSONArray as a parameter

                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("kkkkk", response.toString());
                        try {
                            String msg = response.getString("message");
                            if (msg.contains("No")) {
                                noCandidates.setVisibility(View.VISIBLE);
                                noCandidates.setText(msg);
//                                Toast.makeText(JobCandidatesActivity.this, msg, Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            // Retrieves first JSON object in outer array

                            JSONArray data = response.getJSONArray("data");
                            candidates = new ArrayList(data.length());
                            for (int i = 0; i < data.length(); i++) {
                                //gets each JSON object within the JSON array
                                JSONObject jsonObject = data.getJSONObject(i);

                                // Retrieves the string labeled "colorName" and "hexValue",
                                // and converts them into javascript objects
                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("name");
                                String experienceYears = jsonObject.getString("experienceYears");
                                String phone = jsonObject.getString("phone");

                                String diploma = jsonObject.getString("phone");

                                //ToDo: manipulate diploma string to list

                                String created_at = jsonObject.getString("created_at");
                                String updated_at = jsonObject.getString("updated_at");

                                Candidate j = new Candidate(id, name, phone, experienceYears, "", null);

                                candidates.add(j);
                            }

                            CandidateItemAdapter candidateItemAdapter = new CandidateItemAdapter(JobCandidatesActivity.this, candidates);
                            candidatesList.setAdapter(candidateItemAdapter);
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                       /* } else {
                            Log.e("hhh", "jjjjjj");
//                            Toast.makeText()
                        }*/
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error" + error.getMessage());
                    }
                }
        );
        // Adds the JSON array request "arrayreq" to the request queue
        requestQueue.add(jobsCandidatesRequest);

    }
}