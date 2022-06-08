package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class telephone extends AppCompatActivity {
    private Button btn_phone;
    private EditText num;
    private static final String TAG = "telephone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephone);

        btn_phone=findViewById(R.id.button3);
        num=findViewById(R.id.phone);

    }
    public void btnTele(View view) {
        String phone_number=num.getText().toString();

        if (phone_number.isEmpty())
            Toast.makeText(this, "entrer ton numero", Toast.LENGTH_SHORT).show();
        else{
            PhoneAuthProvider.getInstance().verifyPhoneNumber(phone_number, 60, TimeUnit.SECONDS, telephone.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    signInUser(phoneAuthCredential);

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Log.d(TAG, "on verifier votre num"+e.getLocalizedMessage());

                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);

                    Dialog dialog=new Dialog(telephone.this);
                    dialog.setContentView(R.layout.verfication);
                    EditText verification=findViewById(R.id.confirmation);
                    Button btn_verification=findViewById(R.id.verf);
                    btn_verification.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String verification_code=verification.getText().toString();

                            if (verification_code.isEmpty()) return;

                           PhoneAuthCredential credential= PhoneAuthProvider.getCredential(s, verification_code);
                            signInUser(credential);
                        }


                    });
                    dialog.show();
                }
            });


        }

    }
    private void signInUser(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()){
                  startActivity(new Intent(telephone.this,home.class));
                  finish();
              }else {
                  Log.d(TAG, "on complet"+task.getException().getLocalizedMessage());
              }
            }
        });
    }

    }