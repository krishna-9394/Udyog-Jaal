package com.example.udyogjaal.application_form;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.udyogjaal.R;
import com.example.udyogjaal.utilities.Constants;
import com.example.udyogjaal.utilities.PreferenceManager;
import com.example.udyogjaal.utilities.Providers;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class JobProvidersForm1 extends AppCompatActivity {
    private EditText enterprise_name, monthly_salary;
    private Button proceed;
    ArrayList<Integer> field_name = new ArrayList<Integer>(7);
    ArrayList<Boolean> field_status = new ArrayList<Boolean>(7);
    ArrayList<Integer> job_category_name = new ArrayList<Integer>(3);
    ArrayList<Boolean> job_category_status = new ArrayList<Boolean>(3);
    ArrayList<Integer> languages_known_name = new ArrayList<Integer>(6);
    ArrayList<Boolean> languages_known_status = new ArrayList<Boolean>(6);
    ArrayList<Integer> skills_name = new ArrayList<Integer>(3);
    ArrayList<Boolean> skills_status = new ArrayList<Boolean>(3);

    private FirebaseDatabase providerDB;
    private FirebaseStorage storage;
    private StorageReference ref;
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_providers_form1);
        preferenceManager=new PreferenceManager(getApplicationContext());
        providerDB = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        initializing();
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateCredentials()){
                    proceeding();
                }
            }
        });
    }

    public void checkBoxStatus() {
        // setting up the status for field status 0-7
        final CheckBox electrical =   (CheckBox)findViewById(R.id.electrical);
        final CheckBox hardware =   (CheckBox)findViewById(R.id.hardware);
        final CheckBox paint =   (CheckBox)findViewById(R.id.paint);
        final CheckBox provisional =   (CheckBox)findViewById(R.id.provisional);
        final CheckBox fancy =   (CheckBox)findViewById(R.id.fancy);
        final CheckBox textile =   (CheckBox)findViewById(R.id.textile);
        final CheckBox glass =   (CheckBox)findViewById(R.id.glass);
        field_status.set(0,electrical.isChecked());
        field_status.set(1,hardware.isChecked());
        field_status.set(2,paint.isChecked());
        field_status.set(3,provisional.isChecked());
        field_status.set(4,fancy.isChecked());
        field_status.set(5,textile.isChecked());
        field_status.set(6,glass.isChecked());
        // setting up the status for job category  0-2
        final CheckBox wholesale =   (CheckBox)findViewById(R.id.wholesale);
        final CheckBox retail =   (CheckBox)findViewById(R.id.retail);
        final CheckBox supplier =   (CheckBox)findViewById(R.id.supplier);
        job_category_status.set(0,wholesale.isChecked());
        job_category_status.set(1,retail.isChecked());
        job_category_status.set(2,supplier.isChecked());
        // setting up the status for languages known  0-5
        final CheckBox pro_hindi =   (CheckBox)findViewById(R.id.pro_hindi);
        final CheckBox pro_english =   (CheckBox)findViewById(R.id.pro_english);
        final CheckBox pro_kannada =   (CheckBox)findViewById(R.id.pro_kannada);
        final CheckBox pro_telgu =   (CheckBox)findViewById(R.id.pro_telgu);
        final CheckBox pro_malyalam =   (CheckBox)findViewById(R.id.pro_malyalam);
        final CheckBox pro_tamil =   (CheckBox)findViewById(R.id.pro_tamil);
        languages_known_status.set(0,pro_hindi.isChecked());
        languages_known_status.set(1,pro_english.isChecked());
        languages_known_status.set(2,pro_kannada.isChecked());
        languages_known_status.set(3,pro_telgu.isChecked());
        languages_known_status.set(4,pro_malyalam.isChecked());
        languages_known_status.set(5,pro_tamil.isChecked());
        // setting up the status for skills known 0-2
        final CheckBox driving2 =   (CheckBox)findViewById(R.id.driving2);
        final CheckBox driving4 =   (CheckBox)findViewById(R.id.driving4);
        final CheckBox billing =   (CheckBox)findViewById(R.id.billing);
        skills_status.set(0,driving2.isChecked());
        skills_status.set(1,driving4.isChecked());
        skills_status.set(2,billing.isChecked());
    }
    private void proceeding() {

        checkBoxStatus();

        Providers provider =new Providers( enterprise_name.getText().toString(), monthly_salary.getText().toString(), field_status, job_category_status, languages_known_status, skills_status);
        DatabaseReference def = providerDB.getReference();
        def.child("Job Provider").child(preferenceManager.getString(Constants.KEY_USER_ID)).setValue(provider)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        preferenceManager.putBoolean(Constants.KEY_IS_PROVIDER1_DONE, true);
                        startActivity(new Intent(JobProvidersForm1.this, JobProvidersForm2.class));
                    }
                });
//        def.child("Job Seekers details").child(preferenceManager.getString(Constants.KEY_USER_ID)).setValue(provide);
    }
    private void initializing() {
        enterprise_name = findViewById(R.id.company_name_input);
        monthly_salary = findViewById(R.id.company_salary_input);
        proceed = findViewById(R.id.providers_proceed);
        // field names and status
        field_name.add(R.string.electrical);
        field_name.add(R.string.hardware);
        field_name.add(R.string.paint_and_sanitary);
        field_name.add(R.string.provisional_store);
        field_name.add(R.string.fancy_store);
        field_name.add(R.string.textile);
        field_name.add(R.string.glass);
        field_status.add(false);
        field_status.add(false);
        field_status.add(false);
        field_status.add(false);
        field_status.add(false);
        field_status.add(false);
        field_status.add(false);
        // job category name and status
        job_category_name.add(R.string.wholesale);
        job_category_name.add(R.string.retail);
        job_category_name.add(R.string.supplier);
        job_category_status.add(false);
        job_category_status.add(false);
        job_category_status.add(false);
        // languages known name and status
        languages_known_name.add(R.string.hindi);
        languages_known_name.add(R.string.english);
        languages_known_name.add(R.string.kannada);
        languages_known_name.add(R.string.telgu);
        languages_known_name.add(R.string.malyalam);
        languages_known_name.add(R.string.tamil);
        languages_known_status.add(false);
        languages_known_status.add(false);
        languages_known_status.add(false);
        languages_known_status.add(false);
        languages_known_status.add(false);
        languages_known_status.add(false);
        // skill names and status
        skills_name.add(R.string.driving2);
        skills_name.add(R.string.driving4);
        skills_name.add(R.string.billing);
        skills_status.add(false);
        skills_status.add(false);
        skills_status.add(false);
        // setListener

    }
    private boolean validateCredentials() {
        String enterpriseName = enterprise_name.getText().toString();
        String monthlySalary = monthly_salary.getText().toString();
        if(TextUtils.isEmpty(enterpriseName) || TextUtils.isEmpty(monthlySalary)){
            showToast("field is empty");
            return false;
        }
        else return true;
    }
    private void showToast(String message){
        Toast.makeText(this, message+"", Toast.LENGTH_SHORT).show();
    }
}