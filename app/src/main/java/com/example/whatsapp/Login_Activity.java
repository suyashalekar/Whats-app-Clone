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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login_Activity extends AppCompatActivity {

    private Button login, facebook;
    private EditText email,password,phoneOn;
    private FirebaseAuth auth;
    private TextView loginRegister;
    private ProgressDialog  progressDialog;

   // private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
      //  getSupportActionBar().hide();
        /**
         *  Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
         *  Decalration part
         */

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.login_login_button);
        phoneOn = findViewById(R.id.login_phoneno);
        facebook =findViewById(R.id.login_fb_button);
        loginRegister =findViewById(R.id.login_register_textView);
        progressDialog  =new ProgressDialog(this);



        /**
         * Google Auth
         */
//        // Configure Google Sign In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();

       /// googleSignInClient = GoogleSignIn.getClient(this,gso);


        loginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this,RegisterActivity.class));
            }
        });



        /**
         * Phone Intent
         */

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login_Activity.this, "Login with Facebook", Toast.LENGTH_SHORT).show();
            }
        });


        /**
         * Login setONclict listnere
         */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txemail = email.getText().toString();
                String txpassword = password.getText().toString();
                String txphone = phoneOn.getText().toString();

                if(TextUtils.isEmpty(txemail)||TextUtils.isEmpty(txpassword)){
                    Toast.makeText(Login_Activity.this, "Enter email or password", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    LoginMe(txemail,txpassword);
                }
            }
        });


    }


    private void LoginMe(String email, String password) {

        progressDialog.setTitle("Log in ");
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Login_Activity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(Login_Activity.this, "Success", Toast.LENGTH_SHORT).show();
                    Log.d("suyash","Success");
                    Intent intent = new Intent(Login_Activity.this,MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else{
                    progressDialog.dismiss();
                    String message = task.getException().toString();
                    Log.d("suyash",message);
                    Toast.makeText(Login_Activity.this, message, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Login_Activity.this, "Failed", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
//
//    int RC_SIGN_IN = 65;
//    private void signIn() {
//        Intent signInIntent = googleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                Log.d("suyash", "firebaseAuthWithGoogle:" + account.getId());
//                firebaseAuthWithGoogle(account.getIdToken());
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.w("suyash", "Google sign in failed", e);
//                // ...
//            }
//        }
//    }
//    private void firebaseAuthWithGoogle(String idToken) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("suyash", "signInWithCredential:success");
//                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//                            User users = new User();
//                            users.setUserId(firebaseUser.getUid());
//                            users.setUserName(firebaseUser.getDisplayName());
//                            users.setProfilepic(firebaseUser.getPhotoUrl().toString());
//
//                            realTimeDatabase.getReference().child("User").child(firebaseUser.getUid()).setValue(users);
//
//                            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
//                            // updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("suyash", "signInWithCredential:failure", task.getException());
//                            // Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            // updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//    }

    @Override
    protected void onStart() {
        super.onStart();
       // if(FirebaseAuth.getInstance().getCurrentUser()!= null){
          //  startActivity(new Intent(Login_Activity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        //}
    }
}