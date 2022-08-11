package com.example.udyogjaal.activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class SignUp_Page extends AppCompatActivity {
    private EditText name,email,password,confirmPassword;
    private TextView loginHyperLink;
    private Button SignUp;
    private FloatingActionButton addImage;
    private ProgressBar progressBar;
    private RoundedImageView userProfile;

    private Uri imageUri;
    private String path;
    private byte[] imageByte;

    private FirebaseFirestore loginDB;
    private FirebaseStorage storage;
    private StorageReference ref;

    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        preferenceManager=new PreferenceManager(getApplicationContext());

        loginDB = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        // initialising the view Items
        initializing();
        Listener();
    }

    private void initializing() {
        name = findViewById(R.id.name_input);
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        confirmPassword = findViewById(R.id.confirm_password_input);
        loginHyperLink = findViewById(R.id.login_option);
        SignUp = findViewById(R.id.Sign_up_button);
        progressBar = findViewById(R.id.progress_bar);
        addImage = (FloatingActionButton) findViewById(R.id.add_image);
        userProfile = (RoundedImageView) findViewById(R.id.signUp_logo);
        path = "images/" + UUID.randomUUID().toString();
        Log.v("message","initialization completed");

    }
    // Toast message maker
    public void showToast(String message){
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }
    // Set the Listeners such as forgotPassword, SignUp, and addImage;
    private void Listener() {

        loginHyperLink.setOnClickListener(view -> { startActivity(new Intent(SignUp_Page.this, SignIn_Page.class)); });  //checked once
        SignUp.setOnClickListener(view -> { if(validateCredentials()) SignUp(); });
        addImage.setOnClickListener(view -> { chooseImage(); });
    }

    private void chooseImage() {
        //  taking permission for storage
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener(){

                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        // after getting the permission choosing image part 1

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(SignUp_Page.this, "permission denied..", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
    // intergral part of choosing
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK){
            imageUri = data.getData();
            userProfile.setImageURI(imageUri);
            upload();
        }
    }

    private void upload() {
        if (imageUri != null) {
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60 , stream);
                imageByte = stream.toByteArray();
                 ref = storage.getReference().child(path);
                // adding listeners on upload
                // or failure of image
                ref.putBytes(imageByte)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        try {
                                            Bitmap bit = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                // Image uploaded successfully
                                // Dismiss dialog
                                progressDialog.dismiss();
                                showToast("uploaded...");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                // Error, Image not uploaded
                                progressDialog.dismiss();
                                showToast(" " + "Failed " + e.getMessage());
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            // Progress Listener for loading
                            // percentage on the dialog box
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //consisting of uploading of image firebase storage and uploading it to preference manager
    private void SignUp() {
        loading(true);
        HashMap<String,Object> map = new HashMap<>();
        map.put(Constants.KEY_NAME,name.getText().toString().trim());
        map.put(Constants.KEY_EMAIL,email.getText().toString().trim());
        map.put(Constants.KEY_PASSWORD,password.getText().toString().trim());
        map.put(Constants.KEY_IMAGE, path);
        loginDB.collection(Constants.KEY_USER_COLLECTION).add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            loading(false);
                            // adding data to permanent memory after the login
                            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                            preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
                            preferenceManager.putString(Constants.KEY_NAME, name.getText().toString());
                            preferenceManager.putString(Constants.KEY_IMAGE, path);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("profile", imageByte);
                            startActivity(intent);
                        }
                    })
                .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showToast(e.getMessage());
                        }
                    });
    }
    private void loading(boolean isLoading) {
        if(isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            SignUp.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            SignUp.setVisibility(View.VISIBLE);
        }
    }
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
        }else if(TextUtils.isEmpty(confirmPassword.getText().toString())){
            confirmPassword.setError("password is required");
            showToast("password is required...");
            return false;
        }else if(!confirmPassword.getText().toString().equals(password.getText().toString().trim())){
            confirmPassword.setError("password is not matching");
            showToast("password is not matching...");
            return false;
        }
        else return true;
    }
}