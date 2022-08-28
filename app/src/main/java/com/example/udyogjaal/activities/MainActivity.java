package com.example.udyogjaal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.udyogjaal.R;
import com.example.udyogjaal.application_form.JobProvidersForm1;
import com.example.udyogjaal.application_form.JobSeekersForm1;
import com.example.udyogjaal.utilities.Constants;
import com.example.udyogjaal.utilities.PreferenceManager;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {
    private ImageView profile, back;
    private TextView name;
    private ConstraintLayout seeker, provider, guest;
    private PreferenceManager preferenceManager;
    private FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferenceManager = new PreferenceManager(getApplicationContext());
        super.onCreate(savedInstanceState);
        switch(preferenceManager.getString(Constants.KEY_USER_TYPE)){
            case "seeker":
                startActivity(new Intent(MainActivity.this,JobSeekersForm1.class));
                break;
            case "provider":
                startActivity(new Intent(MainActivity.this,JobProvidersForm1.class));
                break;
            case "guest":
                startActivity(new Intent(MainActivity.this,DisplayArea.class));
                break;
            default:
                break;
        }
        setContentView(R.layout.activity_main);
        initialization();
        loadingUserDetails();
        listener();
    }
    private void initialization() {
        provider = findViewById(R.id.layout1);
        seeker = findViewById(R.id.layout2);
        guest = findViewById(R.id.layout3);
        profile = findViewById(R.id.imageProfile);
        back = findViewById(R.id.imageBack);
        name = findViewById(R.id.nameView);
    }
    private void loadingUserDetails() {
        name.setText(preferenceManager.getString(Constants.KEY_NAME));
        storage = FirebaseStorage.getInstance();
        StorageReference mImageRef = storage.getReference();
        Log.v( "picturing", ""+preferenceManager.getString(Constants.KEY_IMAGE_URL));
        Glide.with(MainActivity.this).load(preferenceManager.getString(Constants.KEY_IMAGE_URL)).into(profile);
    }
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void listener() {
        back.setOnClickListener(view -> {
            preferenceManager.clear();
            startActivity(new Intent(MainActivity.this, SignIn_Page.class));
            showToast("signing out...");
        });
        seeker.setOnClickListener(view ->{
            preferenceManager.putString(Constants.KEY_USER_TYPE,"seeker");
            startActivity(new Intent(getApplicationContext(), JobSeekersForm1.class));
        });
        provider.setOnClickListener(view ->{
            preferenceManager.putString(Constants.KEY_USER_TYPE, "provider");
            startActivity(new Intent(getApplicationContext(), JobProvidersForm1.class));
        });
        guest.setOnClickListener(view ->{
            preferenceManager.putString(Constants.KEY_USER_TYPE, "guest");
            showToast("currently this feature is unavailable...");
        });
    }
}