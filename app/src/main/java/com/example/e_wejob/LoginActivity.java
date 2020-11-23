package com.example.e_wejob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;
    String JsonURL = "https://dry-everglades-05566.herokuapp.com/api/LoginApi";
    Button btnLogin;
    EditText email, password;
    TextView registerLink;
    // شريط التقدم
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        registerLink = findViewById(R.id.registerLink);
        progressBar = findViewById(R.id.progress1);
        sharedPreferences = getSharedPreferences("e_job", MODE_PRIVATE);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();
                // التحقق من إدخال الايميل
                if (emailValue.trim().equals("")) {
                    email.setError(getString(R.string.required));
                    return;
                }
                // التحقق من إدخال كلمة المرور
                if (passwordValue.trim().equals("")) {
                    password.setError(getString(R.string.required));
                    return;
                }
                //إظهار شريط التقدم
                progressBar.setVisibility(View.VISIBLE);
                Login(emailValue, passwordValue);
            }
        });
        //الانتقال إلى صفحة تسجيل الاشتراك عند الضغط على الرابط
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
        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
            params.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Creating the JsonArrayRequest class called arrayreq, passing the required parameters
        //JsonURL is the URL to be fetched from
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, JsonURL, params,
                // The second parameter Listener overrides the method onResponse() and passes
                //JSONArray as a parameter

                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response", response.toString());
                        try {
                            String msg = response.getString("message");
                            if (msg.contains("No")) {
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

                            if (email.equals("admin@admin.com")) {
                                String name = jsonObject.getString("name");

                                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                                myEdit.putInt("id", id);
                                myEdit.putString("admin_name", name);
                                myEdit.putBoolean("is_login", true);
                                myEdit.apply();
                                Intent intent = new Intent(LoginActivity.this, AdminMainActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                            if (type.equals("Company")) {
                                String cName = jsonObject.getString("cName");

                                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                                myEdit.putInt("id", id);
                                myEdit.putString("cName", cName);
                                myEdit.putString("company_tel", tel);
                                myEdit.putString("type", "Company");
                                myEdit.putBoolean("is_login", true);
                                myEdit.apply();
                                Intent intent = new Intent(LoginActivity.this, CompanyMainActivity.class);

                                startActivity(intent);
                                finish();

                            } else {
                                String name = jsonObject.getString("name");
                                String experienceYears = jsonObject.getString("experienceYears");

                                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                                myEdit.putInt("id", id);
                                myEdit.putString("name", name);
                                myEdit.putString("candidate_tel", tel);
                                myEdit.putString("type", "Candidate");
                                myEdit.putString("experienceYears", experienceYears);
                                myEdit.putBoolean("is_login", true);

                                myEdit.apply();
                                Intent intent = new Intent(LoginActivity.this, CandidateMainActivity.class);

                                startActivity(intent);
                                finish();
                            }

                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);

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
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Email or Password is Incorrect", Toast.LENGTH_LONG).show();
                    }
                }
        ) {

        };
        // Adds the JSON array request "arrayreq" to the request queue
        requestQueue.add(request);

    }
}