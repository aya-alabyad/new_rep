package com.example.e_wejob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DiplomaActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    String jsonUrl = "https://dry-everglades-05566.herokuapp.com/api/RegisterApi";

    RequestQueue requestQueue;
    Button btnSignup;

    Switch switchBacaloria, switchUniversity, switchMaster, switchPhd;
    EditText bacaloriaType, universityType, masterType, phdType;
    String bacaloriaText, universityText, masterText, phdText;

    EditText experienceYears;
    boolean isBacaloria, isUniversity, isMaster, isPhd;

    String email, password, tel, name, type;

    String experienceYearsValue, diplomaType = "", diplomaTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diploma);
        sharedPreferences = getSharedPreferences("e_job", MODE_PRIVATE);

        Intent i = getIntent();
        email = i.getStringExtra("email");
        password = i.getStringExtra("password");
        tel = i.getStringExtra("tel");
        name = i.getStringExtra("name");
        type = "Candidate";

        btnSignup = findViewById(R.id.btnLogin);
        bacaloriaType = findViewById(R.id.bacaloriaType);
        universityType = findViewById(R.id.diplomaType);
        masterType = findViewById(R.id.masterType);
        phdType = findViewById(R.id.phdType);

        experienceYears = findViewById(R.id.experienceYears);
        switchBacaloria = findViewById(R.id.switchBacaloria);
        LinearLayout cardBacaloria = findViewById(R.id.cardBachelor);
        switchBacaloria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isBacaloria = true;
                    cardBacaloria.setVisibility(View.VISIBLE);
                } else {
                    isBacaloria = false;
                    cardBacaloria.setVisibility(View.GONE);
                }

            }
        });

        switchUniversity = findViewById(R.id.switchDiploma);
        LinearLayout cardUniversity = findViewById(R.id.cardDiploma);
        switchUniversity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isUniversity = true;
                    cardUniversity.setVisibility(View.VISIBLE);
                } else {
                    isUniversity = false;
                    cardUniversity.setVisibility(View.GONE);
                }

            }
        });

        switchMaster = findViewById(R.id.switchMaster);
        LinearLayout cardMaster = findViewById(R.id.cardMaster);
        switchMaster.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isMaster = true;
                    cardMaster.setVisibility(View.VISIBLE);
                } else {
                    isMaster = false;
                    cardMaster.setVisibility(View.GONE);
                }

            }
        });

        switchPhd = findViewById(R.id.switchPhd);
        LinearLayout cardPhd = findViewById(R.id.cardPhd);
        switchPhd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isPhd = true;
                    cardPhd.setVisibility(View.VISIBLE);
                } else {
                    isPhd = false;
                    cardPhd.setVisibility(View.GONE);
                }

            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validationMethod()) return;

                if (isBacaloria) {
                    diplomaType += "bachelor,";
                    diplomaTitle += bacaloriaText + ",";
                }
                if (isUniversity) {
                    diplomaType += "diploma,";
                    diplomaTitle += universityText + ",";
                }
                if (isMaster) {
                    diplomaType += "master,";
                    diplomaTitle += masterText + ",";
                }
                if (isMaster) {
                    diplomaType += "phD,";
                    diplomaTitle += phdText + ",";
                }
                if (diplomaType.endsWith(",")) {
                    diplomaType = diplomaType.substring(0, diplomaType.length() - 1);
                }
                if (diplomaTitle.endsWith(",")) {
                    diplomaTitle = diplomaTitle.substring(0, diplomaTitle.length() - 1);
                }
                Register(email, tel, password, name, experienceYearsValue, diplomaType, diplomaTitle);
            }
        });
    }

    public boolean validationMethod() {
        boolean valid = true;
        bacaloriaText = bacaloriaType.getText().toString();
        universityText = universityType.getText().toString();
        masterText = masterType.getText().toString();
        phdText = phdType.getText().toString();
        experienceYearsValue = experienceYears.getText().toString();

        if (!isBacaloria && !isUniversity && !isMaster && !isPhd) {
            Toast.makeText(DiplomaActivity.this, "Please Select One Certificate at Least", Toast.LENGTH_LONG).show();
            valid = false;
        }
        if (isBacaloria)
            if (bacaloriaText.trim().equals("")) {
                valid = false;
                bacaloriaType.setError(getString(R.string.required));
            }

        if (isUniversity)
            if (universityText.trim().equals("")) {
                valid = false;
                universityType.setError(getString(R.string.required));
            }

        if (isMaster)
            if (masterText.trim().equals("")) {
                valid = false;
                masterType.setError(getString(R.string.required));
            }
        if (isPhd)
            if (phdText.trim().equals("")) {
                valid = false;
                phdType.setError(getString(R.string.required));
            }

        if (experienceYearsValue.trim().equals("")) {
            valid = false;
            experienceYears.setError(getString(R.string.required));
        }

        return valid;
    }


    public void Register(String email, String telephone, String password, String fullName,
                         String experienceYears, String diplomaType, String diplomaTitle) {


        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(DiplomaActivity.this);

        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
            params.put("password", password);
            params.put("name", fullName);
            params.put("tel", telephone);
            params.put("type", "Candidate");

            params.put("experienceYears", experienceYears);
            params.put("diplomaType", diplomaType);
            params.put("diplomaTitle", diplomaTitle);
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
                            if (msg.contains("aleardy")) {
                                Toast.makeText(DiplomaActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            // Retrieves first JSON object in outer array

                            //gets each JSON object within the JSON array
                            JSONObject jsonObject = response.getJSONObject("data");
                            String type = jsonObject.getString("type");
                            int id = jsonObject.getInt("id");
                            String email = jsonObject.getString("email");
                            String name = jsonObject.getString("name");

                            SharedPreferences.Editor myEdit = sharedPreferences.edit();

                            myEdit.putInt("id", id);
                            myEdit.putString("name", name);
                            myEdit.putString("email", email);
                            //myEdit.putString("candidate_tel", tel);
                            myEdit.putString("type", "Candidate");
                            myEdit.putBoolean("is_login", true);
                            myEdit.apply();
                            Intent intent = new Intent(DiplomaActivity.this, CandidateMainActivity.class);

                            startActivity(intent);

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

}