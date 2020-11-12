package com.example.e_wejob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_wejob.models.Candidate;
import com.example.e_wejob.models.Company;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences = getSharedPreferences("e_job", MODE_PRIVATE);


    RequestQueue requestQueue;

    String JsonURL = "https://dry-everglades-05566.herokuapp.com/api/LoginApi";

    Button btnLogin;

    EditText email, password;

    TextView registerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        registerLink = findViewById(R.id.registerLink);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();
                Login(emailValue, passwordValue);
            }
        });
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
//                finish();
            }
        });


    }

    public void Login(String email, String password) {


        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(LoginActivity.this);


        // Creating the JsonArrayRequest class called arrayreq, passing the required parameters
        //JsonURL is the URL to be fetched from
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, JsonURL,
                // The second parameter Listener overrides the method onResponse() and passes
                //JSONArray as a parameter

                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("respone", response.toString());
                        try {
                            // Retrieves first JSON object in outer array

                            //gets each JSON object within the JSON array
                            String type = response.getString("type");
                            JSONObject jsonObject = response.getJSONObject("data");

                            // Retrieves the string labeled "colorName" and "hexValue",
                            // and converts them into javascript objects
                            int id = jsonObject.getInt("id");

                            String email = jsonObject.getString("email");
                            String tel = jsonObject.getString("tel");


                            if (type.equals("Company")) {
                                String cName = jsonObject.getString("cName");

                                Company company = new Company(id, cName, tel, "Company");
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                                myEdit.putInt("company_id", id);
                                myEdit.putString("cName", cName);
                                myEdit.putString("company_tel", tel);
                                myEdit.putString("type", "Company");
                                myEdit.apply();
                                Intent intent = new Intent(LoginActivity.this, CompanyMainActivity.class);

                                startActivity(intent);
                            } else {
                                String name = jsonObject.getString("name");
                                String experienceYears = jsonObject.getString("experienceYears");

                                Candidate company = new Candidate(id, name, tel, experienceYears, email, null);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                                myEdit.putInt("candidate_id", id);
                                myEdit.putString("name", name);
                                myEdit.putString("candidate_tel", tel);
                                myEdit.putString("type", "Candidate");
                                myEdit.putString("experienceYears", experienceYears);
                                myEdit.apply();
                                Intent intent = new Intent(LoginActivity.this, CandidateMainActivity.class);

                                startActivity(intent);
                            }

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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }

            ;
        };
        // Adds the JSON array request "arrayreq" to the request queue
        requestQueue.add(request);

    }
}