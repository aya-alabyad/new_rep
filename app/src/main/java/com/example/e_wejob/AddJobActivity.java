package com.example.e_wejob;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddJobActivity extends AppCompatActivity {

    EditText title, salary, requiredExperienceYears;
    Spinner requiredEducation;
    String titleText, salaryText, requiredEducationText, requiredExperienceYearsText;

    Button btnAddJob;
    int educationLevel = 0;


    SharedPreferences sharedPreferences;

    Button btnLogout;

    RequestQueue requestQueue;
    String jsonUrl = "http://dry-everglades-05566.herokuapp.com/api/createJobApi";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        sharedPreferences = getSharedPreferences("e_job", MODE_PRIVATE);

        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.clear();
                myEdit.apply();
                Intent i = new Intent(AddJobActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


        btnAddJob = findViewById(R.id.btnAddJob);

        title = findViewById(R.id.title);
        salary = findViewById(R.id.salary);
        requiredEducation = findViewById(R.id.requiredEducationLevel);
        requiredExperienceYears = findViewById(R.id.requiredExperienceYears);


        requiredEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextInputLayout cNameLayout = findViewById(R.id.cNameLayout);
                MaterialCardView candidateCard = findViewById(R.id.candidateCard);
                if (position == 1) {
                    educationLevel = 1;
                    requiredEducationText = "Bachelor";
                }
                if (position == 2) {
                    educationLevel = 2;
                    requiredEducationText = "Diploma";
                }
                if (position == 3) {
                    educationLevel = 3;
                    requiredEducationText = "Master";
                }
                if (position == 4) {
                    educationLevel = 4;
                    requiredEducationText = "PhD";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validationMethod()) return;
                if (isNetworkAvailable())
                    createJob(titleText, salaryText, requiredEducationText, requiredExperienceYearsText);
                else
                    Toast.makeText(AddJobActivity.this, "No Network Connection Found", Toast.LENGTH_LONG).show();


            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void createJob(String title, String salary, String educationLevel, String experienceYears) {


        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(AddJobActivity.this);

        JSONObject params = new JSONObject();
        try {
            int id = sharedPreferences.getInt("id", 0);

            params.put("Company_id", id);
            params.put("Title", title);
            params.put("Salary", salary);
            params.put("Diploma", educationLevel);
            params.put("Years", experienceYears);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Creating the JsonArrayRequest class called arrayreq, passing the required parameters
        //JsonURL is the URL to be fetched from
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, jsonUrl, params,
                // The second parameter Listener overrides the method onResponse() and passes
                //JSONArray as a parameter

                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("respone", response.toString());
                        try {
                            String msg = response.getString("message");

                            Toast.makeText(AddJobActivity.this, msg, Toast.LENGTH_LONG).show();
                            finish();
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            Log.e("JSONException", e.getMessage());
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
                        Log.e("Volley", "Error " + error.getMessage());
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        // Adds the JSON array request "arrayreq" to the request queue
        requestQueue.add(request);

    }


    public boolean validationMethod() {
        boolean valid = true;
        titleText = title.getText().toString();
        salaryText = salary.getText().toString();
        requiredExperienceYearsText = requiredExperienceYears.getText().toString();
        if (titleText.trim().equals("")) {
            valid = false;
            title.setError(getString(R.string.required));
        }
        if (salaryText.trim().equals("")) {
            valid = false;
            salary.setError(getString(R.string.required));
        }
        if (requiredExperienceYearsText.trim().equals("")) {
            valid = false;
            requiredExperienceYears.setError(getString(R.string.required));
        }

        if (educationLevel == 0) {
            valid = false;
            Toast.makeText(AddJobActivity.this, "Please Select Education Level ", Toast.LENGTH_LONG).show();
        }

        return valid;

    }

}