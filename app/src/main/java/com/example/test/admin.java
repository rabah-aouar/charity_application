package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import maes.tech.intentanim.CustomIntent;

import static androidx.core.content.ContextCompat.startActivity;

public class admin {
    protected String email ;
    protected String id;
    protected String type;

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public admin(String id  , String email, String password  , String type) {
        this.email = email;
        this.password = password;
        this.id = id;
        this.type = type;


    }
    public admin( String email, String password  , String type) {
        this.email = email;
        this.password = password;


    }
    public void ajoute_admin(final Context context , final FirebaseAuth mAuth){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context , "تم اضافة مسؤول بنجاح" , Toast.LENGTH_LONG).show();
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    String userId = "";
                    if (firebaseUser != null) {
                        userId = firebaseUser.getUid();
                    }
                    admin ajout_admin = new admin(userId , email ,password,"admin");
                    FirebaseDatabase.getInstance().getReference("users").child(userId).setValue(ajout_admin);


                }else {
                    Toast.makeText(context , task.getException().toString() , Toast.LENGTH_LONG).show();
                }

            }
        }) ;

    }
}
