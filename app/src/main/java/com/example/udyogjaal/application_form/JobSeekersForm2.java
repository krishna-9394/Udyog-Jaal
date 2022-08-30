package com.example.udyogjaal.application_form;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.udyogjaal.R;
import com.example.udyogjaal.activities.DisplayArea;
import com.example.udyogjaal.utilities.Constants;
import com.example.udyogjaal.utilities.PreferenceManager;
import com.example.udyogjaal.utilities.Providers;
import com.example.udyogjaal.utilities.Seekers;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JobSeekersForm2 extends AppCompatActivity {
    private EditText experience;
    private Button submit;
    private ArrayList<String> field_name = new ArrayList<String>(7);
    private ArrayList<Boolean> field_status = new ArrayList<Boolean>(7);
    private ArrayList<String> job_category_name = new ArrayList<String>(3);
    private ArrayList<Boolean> job_category_status = new ArrayList<Boolean>(3);
    private ArrayList<String> languages_known_name = new ArrayList<String>(6);
    private ArrayList<Boolean> languages_known_status = new ArrayList<Boolean>(6);
    private ArrayList<String> skills_name = new ArrayList<String>(5);
    private ArrayList<Boolean> skills_status = new ArrayList<Boolean>(5);
    private FirebaseDatabase providerDB;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferenceManager=new PreferenceManager(getApplicationContext());
        providerDB = FirebaseDatabase.getInstance();
        super.onCreate(savedInstanceState);
        if(preferenceManager.getBoolean(Constants.KEY_IS_SEEKER2_DONE) || preferenceManager.getBoolean(Constants.KEY_IS_SELECTION_DONE)){
            startActivity(new Intent(JobSeekersForm2.this, DisplayArea.class));
        }
        setContentView(R.layout.activity_job_seekers_form2);
        initializing();
        listeners();
    }
    private void listeners() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateCredentials()){
                    submitting();
                }
            }
        });
    }
    public void checkBoxStatus() {
        // setting up the status for field status 0-7
        final CheckBox electrical =   (CheckBox)findViewById(R.id.seekers_electrical);
        final CheckBox hardware =   (CheckBox)findViewById(R.id.seekers_hardware);
        final CheckBox paint =   (CheckBox)findViewById(R.id.seekers_paint);
        final CheckBox provisional =   (CheckBox)findViewById(R.id.seekers_provisional);
        final CheckBox fancy =   (CheckBox)findViewById(R.id.seekers_fancy);
        final CheckBox textile =   (CheckBox)findViewById(R.id.seekers_textile);
        final CheckBox glass =   (CheckBox)findViewById(R.id.seekers_glass);
        field_status.set(0,electrical.isChecked());
        field_status.set(1,hardware.isChecked());
        field_status.set(2,paint.isChecked());
        field_status.set(3,provisional.isChecked());
        field_status.set(4,fancy.isChecked());
        field_status.set(5,textile.isChecked());
        field_status.set(6,glass.isChecked());
        // setting up the status for job category  0-2
        final CheckBox wholesale =   (CheckBox)findViewById(R.id.seekers_wholesale);
        final CheckBox retail =   (CheckBox)findViewById(R.id.seekers_retail);
        final CheckBox supplier =   (CheckBox)findViewById(R.id.seekers_supplier);
        job_category_status.set(0,wholesale.isChecked());
        job_category_status.set(1,retail.isChecked());
        job_category_status.set(2,supplier.isChecked());
        // setting up the status for languages known  0-5
        final CheckBox pro_hindi =   (CheckBox)findViewById(R.id.seekers_hindi);
        final CheckBox pro_english =   (CheckBox)findViewById(R.id.seekers_english);
        final CheckBox pro_kannada =   (CheckBox)findViewById(R.id.seekers_kannada);
        final CheckBox pro_telgu =   (CheckBox)findViewById(R.id.seekers_telgu);
        final CheckBox pro_malyalam =   (CheckBox)findViewById(R.id.seekers_malyalam);
        final CheckBox pro_tamil =   (CheckBox)findViewById(R.id.seekers_tamil);
        languages_known_status.set(0,pro_hindi.isChecked());
        languages_known_status.set(1,pro_english.isChecked());
        languages_known_status.set(2,pro_kannada.isChecked());
        languages_known_status.set(3,pro_telgu.isChecked());
        languages_known_status.set(4,pro_malyalam.isChecked());
        languages_known_status.set(5,pro_tamil.isChecked());
        // setting up the status for skills known 0-2
        final CheckBox driving2 =   (CheckBox)findViewById(R.id.seekers_driving2);
        final CheckBox driving4 =   (CheckBox)findViewById(R.id.seekers_driving4);
        final CheckBox billing =   (CheckBox)findViewById(R.id.seekers_billing);
        final CheckBox map =   (CheckBox)findViewById(R.id.seekers_map);
        final CheckBox locality =   (CheckBox)findViewById(R.id.seekers_locality);
        skills_status.set(0,driving2.isChecked());
        skills_status.set(1,driving4.isChecked());
        skills_status.set(2,billing.isChecked());
        skills_status.set(2,map.isChecked());
        skills_status.set(2,locality.isChecked());
    }
    private void submitting() {
        checkBoxStatus();
        Seekers temp;
        DatabaseReference def = providerDB.getReference("Job Seekers Details");
        def.child(preferenceManager.getString(Constants.KEY_USER_ID))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Seekers seeker = snapshot.getValue(Seekers.class);
                        seeker.setExperience(experience.getText().toString());
                        seeker.setField_name(field_name);
                        seeker.setField_status(field_status);
                        seeker.setJob_category_name(job_category_name);
                        seeker.setJob_category_status(job_category_status);
                        seeker.setLanguages_known_name(languages_known_name);
                        seeker.setLanguages_known_status(languages_known_status);
                        seeker.setSkill_name(skills_name);
                        seeker.setSkill_status(skills_status);
                        seeker.setImage_url(preferenceManager.getString(Constants.KEY_IMAGE_URL));
                        pushing(seeker);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                })
                ;
//        def.child("Job Seekers details").child(preferenceManager.getString(Constants.KEY_USER_ID)).setValue(provide);
    }

    private void pushing(Seekers seeker) {
        DatabaseReference def = providerDB.getReference("Job Seekers Details");
        def.child(preferenceManager.getString(Constants.KEY_USER_ID)).setValue(seeker)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        preferenceManager.putBoolean(Constants.KEY_IS_SEEKER2_DONE, true);
                        showToast("submit");
                        startActivity(new Intent(JobSeekersForm2.this, DisplayArea.class));
                    }
                });
    }

    private void initializing() {
        experience = findViewById(R.id.seekers_experience_input);
        submit = findViewById(R.id.seekers_submit);
        // field names and status
        field_name.add("electrical");
        field_name.add("hardware");
        field_name.add("paint_and_sanitary");
        field_name.add("provisional_store");
        field_name.add("fancy_store");
        field_name.add("textile");
        field_name.add("glass");
        field_status.add(false);
        field_status.add(false);
        field_status.add(false);
        field_status.add(false);
        field_status.add(false);
        field_status.add(false);
        field_status.add(false);
        // job category name and status
        job_category_name.add("wholesale");
        job_category_name.add("retail");
        job_category_name.add("supplier");
        job_category_status.add(false);
        job_category_status.add(false);
        job_category_status.add(false);
        // languages known name and status
        languages_known_name.add("hindi");
        languages_known_name.add("english");
        languages_known_name.add("kannada");
        languages_known_name.add("telgu");
        languages_known_name.add("malyalam");
        languages_known_name.add("tamil");
        languages_known_status.add(false);
        languages_known_status.add(false);
        languages_known_status.add(false);
        languages_known_status.add(false);
        languages_known_status.add(false);
        languages_known_status.add(false);
        // skill names and status
        skills_name.add("driving2");
        skills_name.add("driving4");
        skills_name.add("billing");
        skills_name.add("using_google_map");
        skills_name.add("good_knowledge_about_locality");
        skills_status.add(false);
        skills_status.add(false);
        skills_status.add(false);
        skills_status.add(false);
        skills_status.add(false);
    }
    private boolean validateCredentials() {
        if(TextUtils.isEmpty(experience.getText().toString())){
            showToast("field is empty");
            return false;
        }
        else return true;
    }
    private void showToast(String message){
        Toast.makeText(this, message+"", Toast.LENGTH_SHORT).show();
    }
}