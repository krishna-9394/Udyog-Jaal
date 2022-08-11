package com.example.udyogjaal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SignIn_Page extends AppCompatActivity {

    private EditText email,password;
    private TextView signupHyperLink,forgotPasswordHyperLink;
    private Button login;
    private ProgressBar progressBar;
    private byte[] imageByte;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // checking out weather the person has already signed in or not? using preferences manager
        preferenceManager = new PreferenceManager(getApplicationContext());
        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            StorageReference mImageRef = FirebaseStorage.getInstance().getReference(preferenceManager.getString(Constants.KEY_IMAGE));
            final long ONE_MEGABYTE = 1024 * 1024;
            mImageRef.getBytes(ONE_MEGABYTE)
                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            imageByte = bytes;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("profile", imageByte);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_sign_in_page);

        // initialising the view Items
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        signupHyperLink = findViewById(R.id.signUp_option);
        forgotPasswordHyperLink = findViewById(R.id.forgot_password_option);
        login = findViewById(R.id.Sign_in_button);
        progressBar = findViewById(R.id.progress_bar);

        //various operations list
        setListeners();
    }
    private void setListeners() {
        login.setOnClickListener(view -> {
            if (validateCredentials()) Login();
        });
        signupHyperLink.setOnClickListener(view -> {
            startActivity(new Intent(SignIn_Page.this, SignUp_Page.class));
        });
        forgotPasswordHyperLink.setOnClickListener(view -> {
            showToast("work in Progress");
        });
    }
    private void Login() {
        loading(true);
        FirebaseFirestore loginDB = FirebaseFirestore.getInstance();
        loginDB.collection(Constants.KEY_USER_COLLECTION)
                .whereEqualTo(Constants.KEY_EMAIL,email.getText().toString().trim())
                .whereEqualTo(Constants.KEY_PASSWORD,password.getText().toString().trim())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult()!=null && task.getResult().getDocuments().size()>0){
                            DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                            preferenceManager.putString(Constants.KEY_USER_ID,documentSnapshot.getId());
                            preferenceManager.putString(Constants.KEY_NAME,documentSnapshot.getString(Constants.KEY_NAME));
                            preferenceManager.putString(Constants.KEY_IMAGE,documentSnapshot.getString(Constants.KEY_IMAGE));
                            // retrieving the image and passing it to the other activity using intent
                            StorageReference mImageRef = FirebaseStorage.getInstance().getReference(preferenceManager.getString(Constants.KEY_IMAGE));
                            final long ONE_MEGABYTE = 1024 * 1024;
                            mImageRef.getBytes(ONE_MEGABYTE)
                                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                        @Override
                                        public void onSuccess(byte[] bytes) {
                                            imageByte = bytes;
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                        }
                                    });
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("profile", imageByte);
                            startActivity(intent);
                        }
                        else{
                            loading(false);
                            showToast("unable to load...");
                        }
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
    // validating Login credentials
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
    } // checked it again
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}