package com.example.e_wejob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.example.e_wejob.models.Candidate;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    EditText email, tel, password, confirm, fullName, cName;
    Spinner type;
    Button btnSignup;
    RequestQueue requestQueue;
    String jsonUrl = "https://dry-everglades-05566.herokuapp.com/api/RegisterApi";
    //variable to detect registeration type 0 no select ,1 for company ,2 for Candidate
    int registerType = 0;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = findViewById(R.id.progress1);
        sharedPreferences = getSharedPreferences("e_job", MODE_PRIVATE);

        btnSignup = findViewById(R.id.btnLogin);

        email = findViewById(R.id.email);
        tel = findViewById(R.id.tel);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm);
        fullName = findViewById(R.id.fullName);
        cName = findViewById(R.id.cName);

        type = findViewById(R.id.type);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextInputLayout cNameLayout = findViewById(R.id.cNameLayout);
                MaterialCardView candidateCard = findViewById(R.id.candidateCard);
                if (position == 1) {
                    registerType = 1;
                    cNameLayout.setVisibility(View.VISIBLE);
                    candidateCard.setVisibility(View.GONE);
                }
                if (position == 2) {
                    registerType = 2;
                    cNameLayout.setVisibility(View.GONE);
                    candidateCard.setVisibility(View.VISIBLE);
                    btnSignup.setVisibility(View.GONE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validationMethod()) return;

                String emailText, telText, passwordText, fullNameText;
                emailText = email.getText().toString();
                telText = tel.getText().toString();
                passwordText = password.getText().toString();
                fullNameText = fullName.getText().toString();
                Intent intent = new Intent(RegisterActivity.this, DiplomaActivity.class);
                intent.putExtra("email", emailText);
                intent.putExtra("tel", telText);
                intent.putExtra("password", passwordText);
                intent.putExtra("name", fullNameText);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validationMethod()) return;

                String emailText, telText, passwordText, confirmText, cNameText, fullNameText;
                emailText = email.getText().toString();
                telText = tel.getText().toString();
                passwordText = password.getText().toString();
                confirmText = confirm.getText().toString();
                cNameText = cName.getText().toString();
                if (registerType == 1) {
                    progressBar.setVisibility(View.VISIBLE);
                    Register(emailText, telText, passwordText, "Company", cNameText);
                }
                if (registerType == 2) {
                   /* fullNameText = fullName.getText().toString();
                    String name = jsonObject.getString("name");
                    String experienceYears = jsonObject.getString("experienceYears");

                    Candidate company = new Candidate(id, name, tel, experienceYears, email, null);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();

                    myEdit.putInt("candidate_id", id);
                    myEdit.putString("name", name);
                    myEdit.putString("candidate_tel", telText);
                    myEdit.putString("type", "Candidate");
                    myEdit.putString("experienceYears", experienceYears);
                    myEdit.apply();
                    Intent intent = new Intent(RegisterActivity.this, CandidateMainActivity.class);

                    startActivity(intent);*/

                }
            }
        });

    }

    public void Register(String email, String telephone, String password, String type, String cName) {


        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(RegisterActivity.this);

        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
            params.put("password", password);
            params.put("name", cName);
            params.put("tel", telephone);
            params.put("type", "Company");
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
                            // Retrieves first JSON object in outer array

                            //gets each JSON object within the JSON array
                            JSONObject jsonObject = response.getJSONObject("data");
                            String type = jsonObject.getString("type");

                            // Retrieves the string labeled "colorName" and "hexValue",
                            // and converts them into javascript objects
                            int id = jsonObject.getInt("id");

                            String email = jsonObject.getString("email");
                            // String tel = jsonObject.getString("tel");


                            if (type.equals("Company")) {
                                String cName = jsonObject.getString("name");

                                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                                myEdit.putInt("company_id", id);
                                myEdit.putString("cName", cName);
                                myEdit.putString("company_tel", telephone);
                                myEdit.putString("type", "Company");
                                myEdit.putBoolean("is_login", true);

                                myEdit.apply();
                                Intent intent = new Intent(RegisterActivity.this, CompanyMainActivity.class);

                                startActivity(intent);
                                finish();
                            } else {
                                String name = jsonObject.getString("name");
                                // String experienceYears = jsonObject.getString("experienceYears");

                                Candidate company = new Candidate(id, name, "", "", email, null);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                                myEdit.putInt("candidate_id", id);
                                myEdit.putString("name", name);
                                //myEdit.putString("candidate_tel", tel);
                                myEdit.putString("type", "Candidate");
                                myEdit.putBoolean("is_login", true);

                                // myEdit.putString("experienceYears", experienceYears);
                                myEdit.apply();
                                Intent intent = new Intent(RegisterActivity.this, CandidateMainActivity.class);

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

    // validationMethod return true or false
    public boolean validationMethod() {
        boolean valid = true;
        String emailText, telText, passwordText, confirmText, cNameText, fullNameText;
        emailText = email.getText().toString();
        telText = tel.getText().toString();
        passwordText = password.getText().toString();
        confirmText = confirm.getText().toString();
        cNameText = cName.getText().toString();
        fullNameText = fullName.getText().toString();
        if (emailText.trim().equals("")) {
            valid = false;
            email.setError(getString(R.string.required));
        } else {
            Boolean res = isValidEmail(emailText);
            if (!res) {
                valid = false;
                email.setError(getString(R.string.err_email));
            }
        }
        if (telText.trim().equals("")) {
            valid = false;
            tel.setError(getString(R.string.required));
        } else {
            Boolean res = isValidPhone(telText);
            if (!res) {
                valid = false;
                tel.setError(getString(R.string.err_phone));
            }
        }
        if (passwordText.trim().equals("")) {
            valid = false;
            password.setError(getString(R.string.required));
        }
        if (passwordText.trim().length() < 8) {
            valid = false;
            password.setError(getString(R.string.err_pass));
        }
        if (confirmText.trim().equals("")) {
            valid = false;
            confirm.setError(getString(R.string.required));
        }
        if (!passwordText.equals(confirmText)) {
            valid = false;
            confirm.setError(getString(R.string.pass_not_match));
        }
        if (registerType == 1) {
            if (cNameText.trim().equals("")) {
                valid = false;
                cName.setError(getString(R.string.required));
            }
        }
        if (registerType == 2) {
            if (fullNameText.trim().equals("")) {
                valid = false;
                fullName.setError(getString(R.string.required));
            }
        }
        if (registerType == 0) {
            valid = false;
            Toast.makeText(RegisterActivity.this, "Please Select Type ", Toast.LENGTH_LONG).show();
        }

        return valid;

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidPhone(String target) {
        if (target.length() != 10 || !target.startsWith("09")) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
//        return (!TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches());
    }
}