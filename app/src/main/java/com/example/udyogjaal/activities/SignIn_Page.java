package com.example.udyogjaal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udyogjaal.R;
import com.example.udyogjaal.utilities.Constants;
import com.example.udyogjaal.utilities.PreferenceManager;
import com.example.udyogjaal.utilities.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        email = findViewById(R.id.sign_in_email_input);
        password = findViewById(R.id.sign_in_password_input);
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
        DatabaseReference ref = loginDB.getReference().child("user");
        ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean is_matching = false;
                            for(DataSnapshot snap : snapshot.getChildren()){
                                User user = snap.getValue(User.class);
                                Log.v("logging", user.toString());
                                String email_string = email.getText().toString();
                                String password_string = password.getText().toString();
                                if(user.getEmail().equals(email_string) && user.getPassword().equals(password_string)){
                                    is_matching = true;
                                    preferenceManager.putString(Constants.KEY_USER_ID, snap.getKey());
                                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                                    preferenceManager.putString(Constants.KEY_NAME, user.getName());
                                    preferenceManager.putString(Constants.KEY_IMAGE_URL, user.getImage_url());
                                    // retrieving the image and passing it to the other activity using intent
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    break;
                                }

                        }
                            if(!is_matching) {
                            showToast("invalid credentials...");
                            loading(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        showToast("Database error : " + error);
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
    }  //showing the toast message
}