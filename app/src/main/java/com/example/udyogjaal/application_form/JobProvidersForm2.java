package com.example.udyogjaal.application_form;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.udyogjaal.R;
import com.example.udyogjaal.activities.DisplayArea;
import com.example.udyogjaal.utilities.Constants;
import com.example.udyogjaal.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class JobProvidersForm2 extends AppCompatActivity {
    private EditText manager_name, contact_number, address, state;
    private Button submit;
    private FirebaseDatabase providerDB;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferenceManager=new PreferenceManager(getApplicationContext());
        providerDB = FirebaseDatabase.getInstance();
        super.onCreate(savedInstanceState);
        if(preferenceManager.getBoolean(Constants.KEY_IS_PROVIDER2_DONE)){
            startActivity(new Intent(JobProvidersForm2.this, DisplayArea.class));
        }
        setContentView(R.layout.activity_job_providers_form2);
        initializing();
    }

    private void initializing() {
        manager_name = findViewById(R.id.manager_name_input);
        contact_number = findViewById(R.id.contact_number_input);
        address = findViewById(R.id.address_input);
        state = findViewById(R.id.state_input);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateCredentials()) submit();
            }
        });
    }

    private void submit() {
        DatabaseReference def = providerDB.getReference();
        HashMap<String,String> details = new HashMap<>();
        details.put("manager name", manager_name.getText().toString());
        details.put("contact number", contact_number.getText().toString());
        details.put("address", address.getText().toString());
        details.put("state", state.getText().toString());
        def.child("Job Providers Details").child(preferenceManager.getString(Constants.KEY_USER_ID)).setValue(details)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        preferenceManager.putBoolean(Constants.KEY_IS_PROVIDER2_DONE, true);
                        startActivity(new Intent(JobProvidersForm2.this, DisplayArea.class));
                    }
                });
    }

    private boolean validateCredentials() {
        if(TextUtils.isEmpty(manager_name.getText().toString())){
            showToast("manger name field is empty");
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
        else if(TextUtils.isEmpty(state.getText().toString())){
            showToast("state field is empty");
            return false;
        }
        else return true;
    }
    private void showToast(String message){
        Toast.makeText(this, message+"", Toast.LENGTH_SHORT).show();
    }
}