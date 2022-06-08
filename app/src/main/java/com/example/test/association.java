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

public class association extends User{
    private String nom_complet;
    private String adress;
    private String situation;

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getSituation() {
        return situation;
    }

    public association() {}

    public association( String email, String numero_tlph, String password, String ville, String type, String disable, String nom_complet, String adress) {
        super( email, numero_tlph, password, ville, type, disable);
        this.nom_complet = nom_complet;
        this.adress = adress;

    }

    public association(String id, String email, String numero_tlph, String password, String ville, String type, String disable, String nom_complet, String adress,String situation) {
        super(id, email, numero_tlph, password, ville, type, disable);
        this.nom_complet = nom_complet;
        this.adress = adress;
        this.situation = situation;
    }

    public String getNom_complet() {
        return nom_complet;
    }

    public void setNom_complet(String nom_complet) {
        this.nom_complet = nom_complet;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void Cree_compte(final Context context , final FirebaseAuth mAuth){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context , "تم التسجيل بنجاح" , Toast.LENGTH_LONG).show();
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    String userId = "";
                    if (firebaseUser != null) {
                        userId = firebaseUser.getUid();
                    }
                    association user_normale = new association(userId , email , numero_tlph,password,ville,"association","false",nom_complet,adress , "attend");
                    FirebaseDatabase.getInstance().getReference("users").child(userId).setValue(user_normale);
                    FirebaseDatabase.getInstance().getReference("association").child(userId).setValue(user_normale);
                    User user  = new User();
                    user.afficheAlert(context, "حسابك قيد الانتضار","attend");


                }else {
                    Toast.makeText(context , task.getException().toString() , Toast.LENGTH_LONG).show();
                }

            }
        }) ;

    }

}
