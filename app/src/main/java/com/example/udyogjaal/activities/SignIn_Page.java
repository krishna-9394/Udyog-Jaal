package com.example.udyogjaal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udyogjaal.R;
import com.example.udyogjaal.utilities.Constants;
import com.example.udyogjaal.utilities.PreferenceManager;
import com.example.udyogjaal.utilities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class SignIn_Page extends AppCompatActivity {

    private EditText email,password;
    private TextView signupHyperLink,forgotPasswordHyperLink;
    private Button login;
    private ProgressBar progressBar;
    private FirebaseDatabase loginDB;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());

        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            Intent intent = new Intent(SignIn_Page.this, MainActivity.class);
            startActivity(intent);
            finish();
        }  // checking out weather the person has already signed in or not? using preferences manager
        setContentView(R.layout.activity_sign_in_page);
        initialization();  // initialising the view Items
        setListeners(); //various operations list
    }
    private void initialization() {
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        signupHyperLink = findViewById(R.id.signUp_option);
        forgotPasswordHyperLink = findViewById(R.id.forgot_password_option);
        login = findViewById(R.id.Sign_in_button);
        progressBar = findViewById(R.id.progress_bar);
    }  // initialization of views and viewGroups
    private void setListeners() {
        login.setOnClickListener(view -> {
            if (validateCredentials()) Login();
        });
        signupHyperLink.setOnClickListener(view -> {
            startActivity(new Intent(SignIn_Page.this, SignUp_Page.class));
        });
        forgotPasswordHyperLink.setOnClickListener(view -> {
            showToast("work in Progress");
        }); // currently not completed
    }  // initializing signUp link, forgotPassword and submit button
    private void Login() {
        loading(true);
        loginDB = FirebaseDatabase.getInstance();
        DatabaseReference ref = loginDB.getReference().child("users");
        ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            boolean is_matching = false;
                            for(DataSnapshot snap : snapshot.getChildren()){
                                is_matching = true;
                                User user = snapshot.getValue(User.class);
                                if(user.getEmail().equals(email.getText().toString().trim()) &&
                                        user.getPassword().equals(password.getText().toString().trim())){
                                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                                    preferenceManager.putString(Constants.KEY_NAME, user.getName());
                                    preferenceManager.putString(Constants.KEY_IMAGE, user.getImage_url());

                                    // retrieving the image and passing it to the other activity using intent
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    break;
                                }
                            }
                            if(!is_matching) {
                                showToast("invalid credentials...");
                                loading(false);
                                showToast("unable to load...");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        loading(false);
    }  //checked it again
    private void loading(boolean isLoading) {
        if(isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            login.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            login.setVisibility(View.VISIBLE);
        }
    } // checked it again
    private boolean validateCredentials() {
        if(TextUtils.isEmpty(email.getText().toString())) {
            email.setError("email is required");
            showToast("email is required...");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            email.setError("invalid email");
            showToast("invalid email...");
            return false;
        }
        else if(TextUtils.isEmpty(password.getText().toString())){
            password.setError("password is required");
            showToast("password is required...");
            return false;
        }
        else return true;
    } // validating Login credentials
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}