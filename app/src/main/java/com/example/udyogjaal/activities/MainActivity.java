package com.example.udyogjaal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.udyogjaal.R;
import com.example.udyogjaal.application_form.JobProvidersForm1;
import com.example.udyogjaal.application_form.JobSeekersForm1;
import com.example.udyogjaal.utilities.Constants;
import com.example.udyogjaal.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private ImageView profile, back;
    private TextView name;
    private ConstraintLayout seeker, provider, guest;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceManager = new PreferenceManager(getApplicationContext());
        initialization();
        loadingUserDetails();
        setlistener();
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
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference mImageRef = storage.getReference();
//        Glide.with(this /* context */)
//                .load(mImageRef)
//                .into(profile);
        mImageRef.child("images/").child(Constants.KEY_IMAGE).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        profile.setImageURI(uri);
                        Toast.makeText(MainActivity.this, "loaded..", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Failed..", Toast.LENGTH_SHORT).show();
                    }
                });
//        final long ONE_MEGABYTE = 1024 * 1024;
//        mImageRef.getBytes(ONE_MEGABYTE)
//                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                    @Override
//                    public void onSuccess(byte[] bytes) {
//                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                        DisplayMetrics dm = new DisplayMetrics();
//                        getWindowManager().getDefaultDisplay().getMetrics(dm);
//                        profile.setMinimumHeight(dm.heightPixels);
//                        profile.setMinimumWidth(dm.widthPixels);
//                        profile.setImageBitmap(bm);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle any errors
//                    }
//                });
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