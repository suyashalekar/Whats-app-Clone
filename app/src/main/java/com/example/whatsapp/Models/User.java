package com.example.whatsapp.Models;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.whatsapp.MainActivity;
import com.example.whatsapp.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class User {

    String profilepic,userName,mail,password,userId,lastMessage,phone_no;

    public User(String profilepic, String userName, String mail, String password, String userId, String lastMessage) {
        this.profilepic = profilepic;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }
    public User(String userName , String phone_no, String mail, String password) {
        this.phone_no =phone_no;
        this.userName = userName;
        this.mail = mail;
        this.password = password;

    }

    public  User(){}

//    public User(String userName, String mail, String password) {
//        this.userName = userName;
//        this.mail = mail;
//        this.password = password;
//    }


    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

}
