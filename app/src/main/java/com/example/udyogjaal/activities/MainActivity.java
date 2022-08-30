package com.example.udyogjaal.activities;

import androidx.annotation.NonNull;
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
import com.example.udyogjaal.utilities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {
    private ImageView profile, back;
    private TextView name;
    private ConstraintLayout seeker, provider, guest;
    private PreferenceManager preferenceManager;
    private FirebaseStorage storage;
    private FirebaseDatabase DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferenceManager = new PreferenceManager(getApplicationContext());
        DB = FirebaseDatabase.getInstance();
        if(preferenceManager.getBoolean(Constants.KEY_IS_SELECTION_DONE)){
            switch (preferenceManager.getString(Constants.KEY_USER_TYPE)){
                case "seeker":
                    startActivity(new Intent(getApplicationContext(), JobSeekersForm1.class));
                    break;
                case "provider":
                    startActivity(new Intent(getApplicationContext(), JobProvidersForm1.class));
                    break;
                default:
                    showToast("user the user type...");
            }
        }
        super.onCreate(savedInstanceState);
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
            DB.getReference("user").child(preferenceManager.getString(Constants.KEY_USER_ID))
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User user = snapshot.getValue(User.class);
                                    preferenceManager.putString(Constants.KEY_USER_TYPE,"seeker");
                                    user.setUserType(preferenceManager.getString(Constants.KEY_USER_TYPE));
                                    update(user);
                                    preferenceManager.putBoolean(Constants.KEY_IS_SELECTION_DONE,true);
                                    startActivity(new Intent(getApplicationContext(), JobSeekersForm1.class));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
        });
        provider.setOnClickListener(view ->{
            preferenceManager.putString(Constants.KEY_USER_TYPE, "provider");
            DB.getReference("user").child(preferenceManager.getString(Constants.KEY_USER_ID))
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            preferenceManager.putString(Constants.KEY_USER_TYPE,"provider");
                            user.setUserType(preferenceManager.getString(Constants.KEY_USER_TYPE));
                            update(user);
                            preferenceManager.putBoolean(Constants.KEY_IS_SELECTION_DONE,true);
                            startActivity(new Intent(getApplicationContext(), JobProvidersForm1.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        });
        guest.setOnClickListener(view ->{
            preferenceManager.putString(Constants.KEY_USER_TYPE, "guest");
            DB.getReference("user").child(preferenceManager.getString(Constants.KEY_USER_ID))
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            preferenceManager.putString(Constants.KEY_USER_TYPE,"guest");
                            user.setUserType(preferenceManager.getString(Constants.KEY_USER_TYPE));
                            update(user);
                            preferenceManager.putBoolean(Constants.KEY_IS_SELECTION_DONE,true);
                            showToast("currently this feature is unavailable...");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

        });
    }

    private void update(User user) {
        DB.getReference("user").child(preferenceManager.getString(Constants.KEY_USER_ID)).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        return;
                    }
                });
        return;
    }

}