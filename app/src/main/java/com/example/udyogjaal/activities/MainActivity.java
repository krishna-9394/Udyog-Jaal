package com.example.udyogjaal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udyogjaal.R;
import com.example.udyogjaal.application_form.JobProvidersForm1;
import com.example.udyogjaal.application_form.JobSeekersForm1;
import com.example.udyogjaal.utilities.Constants;
import com.example.udyogjaal.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.FirebaseStorage;

public class MainActivity extends AppCompatActivity {
    private ImageView profile, back;
    private TextView name;
    private ConstraintLayout seeker, provider, guest;
    private PreferenceManager preferenceManager;
    private byte[] imageByte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceManager = new PreferenceManager(getApplicationContext());
        imageByte = getIntent().getByteArrayExtra("profile");

        provider = findViewById(R.id.layout1);
        seeker = findViewById(R.id.layout2);
        guest = findViewById(R.id.layout3);
        profile = findViewById(R.id.imageProfile);
        back = findViewById(R.id.imageBack);
        name = findViewById(R.id.nameView);

        loadingUserDetails();
        setlistener();
    }
    private void loadingUserDetails() {
        name.setText(preferenceManager.getString(Constants.KEY_NAME));
        Bitmap bm = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        profile.setImageBitmap(bm);
    }
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void setlistener() {
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