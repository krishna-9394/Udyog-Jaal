package com.example.udyogjaal.application_form;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.udyogjaal.R;

import java.util.ArrayList;

public class JobProvidersForm1 extends AppCompatActivity {
    private EditText enterprise_name, field, monthly_salary;
    private EditText job_category, available_post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_providers_form1);
    }
}