package com.example.udyogjaal.application_form;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.udyogjaal.R;
import com.example.udyogjaal.activities.DisplayArea;
import com.example.udyogjaal.utilities.Constants;
import com.example.udyogjaal.utilities.PreferenceManager;
import com.example.udyogjaal.utilities.Seekers;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class JobSeekersForm1 extends AppCompatActivity {
    private EditText full_name, contact_number, address;
    private Button submit;
    private RadioGroup rg;
    private String sex;
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
        listeners();
    }
    private void listeners() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButton:
                        sex = "male";
                        break;
                    case R.id.radioButton2:
                        sex = "female";
                        break;
                    case R.id.radioButton3:
                        sex = "other";
                        break;
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(validateCredentials()) proceeding();
            }
        });
    }
    private void proceeding() {
        Seekers seekers = new Seekers();
        seekers.setName(full_name.getText().toString());
        seekers.setContact_number(contact_number.getText().toString());
        seekers.setAddress(address.getText().toString());
        seekers.setSex(sex);
        DatabaseReference def = providerDB.getReference("Job Seekers Details");
        def.child(preferenceManager.getString(Constants.KEY_USER_ID)).setValue(seekers)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        preferenceManager.putBoolean(Constants.KEY_IS_SEEKER1_DONE, true);
                        startActivity(new Intent(JobSeekersForm1.this, JobSeekersForm2.class));
                    }
                });
    }
    private boolean validateCredentials() {
        if(TextUtils.isEmpty(full_name.getText().toString())){
            showToast("name field is empty");
            return false;
        }
        else if(TextUtils.isEmpty(contact_number.getText().toString())){
            showToast("contact number field is empty");
            return false;
        }
        else if(TextUtils.isEmpty(address.getText().toString())){
            showToast("address field is empty");
            return false;
        }
        else return true;
    }
    private void showToast(String message){
        Toast.makeText(this, message+"", Toast.LENGTH_SHORT).show();
    }
    private void initializing() {
        full_name = findViewById(R.id.full_name_input);
        contact_number = findViewById(R.id.phone_number_input);
        address = findViewById(R.id.address_input);
        submit = findViewById(R.id.seekers_proceed);
        rg = (RadioGroup) findViewById(R.id.gender_radio);
    }
}