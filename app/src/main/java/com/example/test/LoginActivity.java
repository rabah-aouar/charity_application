package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import maes.tech.intentanim.CustomIntent;

public class LoginActivity extends AppCompatActivity implements TextWatcher {
    EditText etEmailLogin,etPasswordLogin;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmailLogin = findViewById(R.id.etEmailLogin);
        mAuth = FirebaseAuth.getInstance();
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        etPasswordLogin.addTextChangedListener(this);
    }

    public void creeCompete(View view) {
        startActivity(new Intent(LoginActivity.this , type_compteActivity.class));
        CustomIntent.customType(LoginActivity.this , "left-to-right");
    }

    public void LoginIn(View view) {
        if (etEmailLogin.getText().toString().equals("")){
            emptyError(etEmailLogin , "من فضلك املأ الخانة");
        }else if (!Patterns.EMAIL_ADDRESS.matcher(etEmailLogin.getText().toString()).matches()){
            emptyError(etEmailLogin,"الاميل خاطئ");
        }else
        if (etPasswordLogin.getText().toString().equals("")){
            emptyError(etPasswordLogin , "من فضلك املأ الخانة");
        }else
        if (!etEmailLogin.getText().toString().isEmpty() && !etPasswordLogin.getText().toString().isEmpty()) {

            User user = new User(etEmailLogin.getText().toString() , etPasswordLogin.getText().toString());
            user.login_compte(LoginActivity.this,mAuth);


        } else {
            Toast.makeText(LoginActivity.this , "Empty" , Toast.LENGTH_LONG).show();
        }

    }
    private void emptyError(EditText editText , String error){
        editText.setError(error);
        editText.requestFocus();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        etPasswordLogin.setGravity(View.SCROLL_INDICATOR_LEFT);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}