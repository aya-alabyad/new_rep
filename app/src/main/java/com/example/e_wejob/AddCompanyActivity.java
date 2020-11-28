package com.example.e_wejob;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AddCompanyActivity extends AppCompatActivity {
    EditText email, tel, password, cName;
    Button btnAddCompany;
    String emailText, telText, passwordText, cNameText;

    SharedPreferences sharedPreferences;

    Button btnLogout;

    RequestQueue requestQueue;
    String jsonUrl = "http://dry-everglades-05566.herokuapp.com/api/createCompanyApi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        sharedPreferences = getSharedPreferences("e_job", MODE_PRIVATE);

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.clear();
                myEdit.apply();
                Intent i = new Intent(AddCompanyActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnAddCompany = findViewById(R.id.btnAddCompany);

        email = findViewById(R.id.email);
        tel = findViewById(R.id.tel);
        password = findViewById(R.id.password);
        cName = findViewById(R.id.cName);

        btnAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validationMethod()) return;
                if (isNetworkAvailable())
                    createCompany(emailText, telText, passwordText, cNameText);
                else
                    Toast.makeText(AddCompanyActivity.this, "No Network Connection Found", Toast.LENGTH_LONG).show();

            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void createCompany(String email, String telephone, String password, String cName) {


        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(AddCompanyActivity.this);

        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
            params.put("password", password);
            params.put("name", cName);
            params.put("tel", telephone);
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

                            Toast.makeText(AddCompanyActivity.this, msg, Toast.LENGTH_LONG).show();
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
        emailText = email.getText().toString();
        telText = tel.getText().toString();
        passwordText = password.getText().toString();
        cNameText = cName.getText().toString();

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

        if (cNameText.trim().equals("")) {
            valid = false;
            cName.setError(getString(R.string.required));
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