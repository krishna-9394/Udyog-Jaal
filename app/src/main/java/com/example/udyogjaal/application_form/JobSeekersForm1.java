package com.example.udyogjaal.application_form;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.udyogjaal.R;
import com.example.udyogjaal.activities.DisplayArea;
import com.example.udyogjaal.utilities.Constants;
import com.example.udyogjaal.utilities.PreferenceManager;
import com.google.firebase.database.FirebaseDatabase;

public class JobSeekersForm1 extends AppCompatActivity {
    private EditText full_name, contact_number, address;
    private Button submit;
    private RadioButton male, female, other;
    private FirebaseDatabase providerDB;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager=new PreferenceManager(getApplicationContext());
        providerDB = FirebaseDatabase.getInstance();
        if(preferenceManager.getBoolean(Constants.KEY_IS_SEEKER1_DONE)){
            startActivity(new Intent(JobSeekersForm1.this, JobSeekersForm2.class));
        }
        setContentView(R.layout.activity_job_seekers_form1);
        initializing();

        RadioGroup rg = (RadioGroup) findViewById(R.id.gender_radio);
        final String value =
                ((RadioButton)findViewById(rg.getCheckedRadioButtonId()))
                        .getText().toString();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(getBaseContext(), value, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializing() {
        full_name = findViewById(R.id.full_name_input);
        contact_number = findViewById(R.id.phone_number_input);
        address = findViewById(R.id.address_input);
        submit = findViewById(R.id.seekers_proceed);
    }
}