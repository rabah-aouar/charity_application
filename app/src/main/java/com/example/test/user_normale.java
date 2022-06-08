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

public class user_normale extends User {

   private String username;

   public user_normale(String id, String email, String numero_tlph, String password, String ville, String type, String disable, String username) {
      super(id, email, numero_tlph, password, ville, type, disable);
      this.username = username;
   }

   public user_normale(){};
   public user_normale( String email, String numero_tlph, String password, String ville, String username, String type, String disable) {
      super( email, numero_tlph, password, ville,type,disable);
      this.username = username;
   }



   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getDisable() {
      return disable;
   }

   public void setDisable(String disable){
      this.disable = disable;
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
               Toast.makeText(context , userId , Toast.LENGTH_LONG).show();
               user_normale user_normale = new user_normale(userId , email , numero_tlph,password,ville,"user","false",username);
               FirebaseDatabase.getInstance().getReference("users").child(userId).setValue(user_normale);
               Intent NextLevel = new Intent(context , Pageprincipale.class);
               startActivity(context,NextLevel,null);
               CustomIntent.customType(context, "left-to-right");

            }else {
               Toast.makeText(context , task.getException().toString() , Toast.LENGTH_LONG).show();
            }

         }
      }) ;

   }




}
