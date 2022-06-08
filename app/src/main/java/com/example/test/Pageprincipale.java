package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Pageprincipale extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pageprincipale);
        mAuth = FirebaseAuth.getInstance();
    }

    public void logOut(View view) {
        User user = new User();
        user.log_out(Pageprincipale.this);

    }
}