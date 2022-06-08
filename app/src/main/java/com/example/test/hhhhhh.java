package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class hhhhhh extends AppCompatActivity {
    private Button login;
    private Button register;
    private Button entre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);

        entre=findViewById(R.id.entre);
        register.setOnClickListener(v -> {
            startActivity(new Intent(hhhhhh.this,register_1.class));// from this activity to the other
            finish();
        });
        login.setOnClickListener(v -> {
            startActivity(new Intent(hhhhhh.this,login.class));
            finish();
        });
        entre.setOnClickListener(v -> startActivity(new Intent(hhhhhh.this,home.class)));
    }
}
