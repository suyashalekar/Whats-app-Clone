package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Update_Activity extends AppCompatActivity {
   private EditText userName,bio;
   private Button upadate;
    private FirebaseDatabase firebaseDatabase ;
    private FirebaseAuth firebaseAuth;
    private  String cuttentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_);

        Log.d("suyash","inUpdate");

        userName = findViewById(R.id.update_userName);

        bio = findViewById(R.id.update_bio);

        upadate = findViewById(R.id.update_button);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase= FirebaseDatabase.getInstance();

        cuttentUser   =  firebaseAuth.getCurrentUser().getUid();

        upadate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_username = userName.getText().toString();
                String s_bio = bio.getText().toString();

                if(!TextUtils.isEmpty(s_username)){
                    addUserName(s_username);
                }
               if(!TextUtils.isEmpty(s_bio)) {
                   addBio(s_bio);
               }


            }
        });

    }

    private void addBio(String s_bio) {

            firebaseDatabase.getReference().child("User").child(cuttentUser).child("Bio").setValue(s_bio).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Update_Activity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        sendUserToSettingActivity();
                    }
                }
            });

        }

    private void sendUserToSettingActivity() {
        startActivity(new Intent(Update_Activity.this,Setting_Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    private void addUserName(String s_username) {

        firebaseDatabase.getReference().child("User").child(cuttentUser).child("user_name").setValue(s_username).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Update_Activity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    sendUserToSettingActivity();
                }
            }
        });
    }

    }

