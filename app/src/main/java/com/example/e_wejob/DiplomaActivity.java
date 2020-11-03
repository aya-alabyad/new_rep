package com.example.e_wejob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DiplomaActivity extends AppCompatActivity {

    Button btnSignup;

    Switch switchBacaloria, switchUniversity, switchMaster, switchPhd;
    EditText bacaloriaType, universityType, masterType, phdType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diploma);

        btnSignup = findViewById(R.id.btnSignUp);
        bacaloriaType = findViewById(R.id.bacaloriaType);
        universityType = findViewById(R.id.universityType);
        masterType = findViewById(R.id.masterType);
        phdType = findViewById(R.id.phdType);


        switchBacaloria = findViewById(R.id.switchBacaloria);
        LinearLayout cardBacaloria = findViewById(R.id.cardBacaloria);
        switchBacaloria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    cardBacaloria.setVisibility(View.VISIBLE);
                else
                    cardBacaloria.setVisibility(View.GONE);

            }
        });

        switchUniversity = findViewById(R.id.switchUniversity);
        LinearLayout cardUniversity = findViewById(R.id.cardUniversity);
        switchUniversity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    cardUniversity.setVisibility(View.VISIBLE);
                else
                    cardUniversity.setVisibility(View.GONE);

            }
        });

        switchMaster = findViewById(R.id.switchMaster);
        LinearLayout cardMaster = findViewById(R.id.cardMaster);
        switchMaster.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    cardMaster.setVisibility(View.VISIBLE);
                else
                    cardMaster.setVisibility(View.GONE);

            }
        });

        switchPhd = findViewById(R.id.switchPhd);
        LinearLayout cardPhd = findViewById(R.id.cardPhd);
        switchPhd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    cardPhd.setVisibility(View.VISIBLE);
                else
                    cardPhd.setVisibility(View.GONE);

            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validationMethod()) return;
                Intent intent = new Intent(DiplomaActivity.this, AddJobActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean validationMethod() {
        boolean valid = true;
        String bacaloriaText, universityText, masterText, phdText;
        bacaloriaText = bacaloriaType.getText().toString();
        universityText = universityType.getText().toString();
        masterText = masterType.getText().toString();
        phdText = phdType.getText().toString();

        if (!switchBacaloria.isChecked() && !switchUniversity.isChecked() && !switchMaster.isChecked() && !switchPhd.isChecked()) {
            Toast.makeText(DiplomaActivity.this, "Please Select One Certificate at Least", Toast.LENGTH_LONG).show();
            valid = false;
        }
        if (switchBacaloria.isChecked())
            if (bacaloriaText.trim().equals("")) {
                valid = false;
                bacaloriaType.setError(getString(R.string.required));
            }

        if (switchUniversity.isChecked())
            if (universityText.trim().equals("")) {
                valid = false;
                universityType.setError(getString(R.string.required));
            }

        if (switchMaster.isChecked())
            if (masterText.trim().equals("")) {
                valid = false;
                masterType.setError(getString(R.string.required));
            }
        if (switchPhd.isChecked())
            if (phdText.trim().equals("")) {
                valid = false;
                phdType.setError(getString(R.string.required));
            }
      /*  if (registerType == 0) {
            valid = false;
            Toast.makeText(RegisterActivity.this, "Please Select Type ", Toast.LENGTH_LONG).show();
        }
*/
        return valid;

    }
}