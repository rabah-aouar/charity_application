package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import maes.tech.intentanim.CustomIntent;

public class type_compteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_compte);

     findViewById(R.id.user_account).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(type_compteActivity.this , SignupActivity.class));
             CustomIntent.customType(type_compteActivity.this, "left-to-right");
         }
     });
       findViewById(R.id.association_account).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(type_compteActivity.this , AssociationActivity.class));
               CustomIntent.customType(type_compteActivity.this, "left-to-right");
           }
       });;


    }
}