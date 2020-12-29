package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Setting_Activity extends AppCompatActivity {

    private  Button  udateButton;
    private TextView userName,status;
    private CircleImageView circleImageView;
    private FirebaseDatabase firebaseDatabase ;
    private FirebaseAuth firebaseAuth;
    String cuttentUser;

    /**
     * ON Create Methode
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_);
        initialized();

        verifiedUserName();

        udateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Setting_Activity.this,Update_Activity.class));
            }
        });

    }

    private void verifiedUserName() {

        cuttentUser= firebaseAuth.getCurrentUser().getUid();
        firebaseDatabase.getReference().child("User").child(cuttentUser).
                addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              updateUserName(snapshot);
              updatestatus(snapshot);
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });

    }

    private void updatestatus(DataSnapshot snapshot) {
        if(snapshot.child("Bio").exists()){
            status.setText(snapshot.child("Bio").getValue(String.class));
        }
        else{
            Log.d("suyash","Enter Your bio Name");
        }

    }

    private void updateUserName(DataSnapshot snapshot) {
        if(snapshot.child("user_name").exists()){
            userName.setText(snapshot.child("user_name").getValue(String.class));
        }
        else
        {
            Log.d("suyash","Enter Your User Name");
        }

    }

    private void initialized() {
        udateButton =findViewById(R.id.setting_Update_button);
        userName =findViewById(R.id.setting_User_textView);
        status =findViewById(R.id.setting_status_textView);
        circleImageView =findViewById(R.id.setting_profileImage);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();

    }

}