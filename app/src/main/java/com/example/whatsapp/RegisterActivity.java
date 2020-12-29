package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsapp.Models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {


    private EditText name,email,password ,phoneNo;
    private Button register,googleButton,fbButton;
    private TextView registerLoginTextview;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase realTimeDatabase;
    private String CurrentId;
    private HashMap<String,Object> addData;
    private ProgressDialog progressDialog;
    private GoogleSignInClient googleSignInClient;

    private void inizilize() {

        phoneNo=findViewById(R.id.register_phoneon);
        name =findViewById(R.id.register_name);
        email =findViewById(R.id.register_email);
        password =findViewById(R.id.register_password);
        register =findViewById(R.id.register_register_button);
        registerLoginTextview = findViewById(R.id.register_login_textView);
        googleButton = findViewById(R.id.register_google_button);
        fbButton = findViewById(R.id.register_fb_button);

        firebaseAuth = FirebaseAuth.getInstance();
        realTimeDatabase = FirebaseDatabase.getInstance();
        addData = new HashMap<>();
        progressDialog  =new ProgressDialog(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
      // getSupportActionBar().hide();
        inizilize();

        /**
         * Google Auth
         */
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

   googleSignInClient = GoogleSignIn.getClient(this,gso);

        /**
         * OnClick
         */

        registerLoginTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,Login_Activity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tx_name = name.getText().toString();
                String tx_phoneno =phoneNo.getText().toString();
                String tx_email = email.getText().toString();
                String tx_password = password.getText().toString();

                if(checkIsEmpty(tx_name,tx_phoneno,tx_email,tx_password)){
                   registerMe(tx_name,tx_phoneno,tx_email,tx_password);
                }
            }
        });

        /**
         * Google button ONClick listener
         */

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


    }



    /**
     *
     * @param s_name
     * @param s_phoneno
     * @param s_email
     * @param s_password
     */

    private void registerMe(String s_name, String s_phoneno, String s_email, String s_password) {

        progressDialog.setTitle("Creating new account");
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(s_email,s_password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                   progressDialog.dismiss();
                     //CurrentId = firebaseAuth.getCurrentUser().getUid();
                    // User user = new User(s_name,s_phoneno,s_email,s_password);
                    //realTimeDatabase.getReference().child("User").child(CurrentId).setValue(user)
                  addIntoDatabase(s_name,s_phoneno,s_email,s_password);

                   Log.d("suyash","successful");

                }


                else{
                    progressDialog.dismiss();
                    String message = task.getException().toString();
                    Log.d("suyash",message);
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void addIntoDatabase(String s_name, String s_phoneno, String s_email, String s_password) {
        CurrentId = firebaseAuth.getCurrentUser().getUid();
        addData.put("user_name",s_name);
        addData.put("phone",s_phoneno);
        addData.put("email",s_email);
        addData.put("password",s_password);

        realTimeDatabase.getReference().child("User").child(CurrentId).setValue(addData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Account Created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }
                else
                {
                    String message = task.getException().toString();
                    Log.d("suyash",message);
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


int RC_SIGN_IN = 65;
    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("suyash", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("suyash", "Google sign in failed", e);
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("suyash", "signInWithCredential:success");
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            User users = new User();
                            users.setUserId(firebaseUser.getUid());
                            users.setUserName(firebaseUser.getDisplayName());
                            users.setProfilepic(firebaseUser.getPhotoUrl().toString());

                            realTimeDatabase.getReference().child("User").child(firebaseUser.getUid()).setValue(users);

                            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("suyash", "signInWithCredential:failure", task.getException());
                           // Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                           // updateUI(null);
                        }

                        // ...
                    }
                });
    }


    private Boolean checkIsEmpty(String name, String phoneno, String email, String password) {

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please fill the  credential", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(phoneno)){
            Toast.makeText(this, "Please fill the phone credential", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please fill the Password credential also Password must be of Min(6) digits", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please fill the Email credential", Toast.LENGTH_SHORT).show();
            return false;
        }
        return  true;
    }
}
