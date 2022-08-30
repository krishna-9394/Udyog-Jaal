package com.example.udyogjaal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.udyogjaal.R;
import com.example.udyogjaal.utilities.Constants;
import com.example.udyogjaal.utilities.Providers;
import com.example.udyogjaal.utilities.ProvidersCustomAdapter;
import com.example.udyogjaal.utilities.ProvidersProfile;
import com.example.udyogjaal.utilities.SeekersCustomAdapter;
import com.example.udyogjaal.utilities.PreferenceManager;
import com.example.udyogjaal.utilities.Seekers;
import com.example.udyogjaal.utilities.SeekersProfile;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DisplayArea extends AppCompatActivity {
    private TextView nameView;
    private ImageView logOut;
    PreferenceManager manager;
    CircularProgressIndicator progress_circular;
    private SeekersCustomAdapter adapter;
    RecyclerView root;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        manager = new PreferenceManager(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_area);
        root = findViewById(R.id.recyclerView);
        nameView = findViewById(R.id.nameView);
        logOut = findViewById(R.id.imageBack);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.clear();
                startActivity(new Intent(DisplayArea.this, SignIn_Page.class));
            }
        });
        String userType = manager.getString(Constants.KEY_USER_TYPE);
        if(userType.compareTo("seeker")==0) {
            vacancyLoader();
        }else if(userType.compareTo("provider") == 0){
            usersListLoader();
        }
        else {
            // hello to guest
        }
    }
    private void usersListLoader() {
        root.setHasFixedSize(true);
        root.setLayoutManager(new LinearLayoutManager(this));
        nameView.setText("Users List");
        ArrayList<SeekersProfile> arr =new ArrayList<>();
        SeekersCustomAdapter adapter = new SeekersCustomAdapter(getApplicationContext(),arr);
        root.setAdapter(adapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Job Seekers Details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arr.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    SeekersProfile seekersProfile = new SeekersProfile();
                    Seekers detail = data.getValue(Seekers.class);
                    seekersProfile.setName(detail.getName());
                    seekersProfile.setExperience(detail.getExperience());
                    seekersProfile.setField_status(detail.getField_status());
                    seekersProfile.setField_name(detail.getField_name());
                    seekersProfile.setImageUrl(detail.getImage_url());
                    arr.add(seekersProfile);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void vacancyLoader() {
        root.setHasFixedSize(true);
        root.setLayoutManager(new LinearLayoutManager(this));
        nameView.setText("Vacancy List");
        ArrayList<ProvidersProfile> arr =new ArrayList<>();
        ProvidersCustomAdapter adapter = new ProvidersCustomAdapter(getApplicationContext(),arr);
        root.setAdapter(adapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Job Providers Details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arr.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    ProvidersProfile providersProfile = new ProvidersProfile();
                    Providers providers = data.getValue(Providers.class);
                    providersProfile.setName(providers.getEnterprise_name());
                    providersProfile.setContact_number(providers.getContact_number());
                    providersProfile.setField_status(providers.getField_status());
                    providersProfile.setField_name(providers.getField_name());
                    arr.add(providersProfile);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
