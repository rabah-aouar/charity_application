package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register_1 extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button register;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_1);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);
        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String txt_email=email.getText().toString();
                 String txt_password=password.getText().toString();
                 if (TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_email) )
                 {
                     Toast.makeText(register_1.this, "empty text", Toast.LENGTH_LONG).show();

                 }
                 else if (txt_password.length() <6)
                    {
                        Toast.makeText(register_1.this, "password must more then 6 character", Toast.LENGTH_LONG).show();}
                    else {
                     register_user(txt_email,txt_password);
                     Toast.makeText(register_1.this, "hello", Toast.LENGTH_LONG).show();
                        }
                 }
        });

    }

    private void register_user(String txt_email, String txt_password)
        {
            auth.createUserWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(register_1.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(register_1.this, "registration succeful", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(register_1.this,home.class));
                        finish();
                    }
                }
            });
         }
}