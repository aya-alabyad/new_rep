package com.example.e_wejob;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddCompanyActivity extends AppCompatActivity {
    EditText email, tel, password, cName;
    Button btnAddCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        btnAddCompany = findViewById(R.id.btnAddCompany);

        email = findViewById(R.id.email);
        tel = findViewById(R.id.tel);
        password = findViewById(R.id.password);
        cName = findViewById(R.id.cName);

        btnAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validationMethod()) return;
                Intent intent = new Intent(AddCompanyActivity.this, JobGalleryActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean validationMethod() {
        boolean valid = true;
        String emailText, telText, passwordText, cNameText;
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