package com.example.e_wejob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    EditText email, tel, password, confirm, fullName, cName;
    Spinner type;
    Button btnSignup;

    int registerType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnSignup = findViewById(R.id.btnSignUp);

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
                Intent intent = new Intent(RegisterActivity.this, DiplomaActivity.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validationMethod()) return;
                Intent intent = new Intent(RegisterActivity.this, AddJobActivity.class);
                startActivity(intent);
            }
        });

    }

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
        }
        if (telText.trim().equals("")) {
            valid = false;
            tel.setError(getString(R.string.required));
        }
        if (passwordText.trim().equals("")) {
            valid = false;
            password.setError(getString(R.string.required));
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
}