package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText etEmail,etPassword , ed_username ,ed_Sign_passwordConf , ettelephone , etlocation;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        ed_username = findViewById(R.id.ed_username);
        ed_Sign_passwordConf = findViewById(R.id.ed_Sign_passwordConf);
        ettelephone = findViewById(R.id.ettelephone);
        etlocation = findViewById(R.id.etlocation);



        database = FirebaseDatabase.getInstance();


    }
    public void SignUp(View view) {
        if (ed_username.getText().toString().equals("")){
            emptyError(ed_username , "من فضلك املأ الخانة");
        } else if (etEmail.getText().toString().equals("")){
            emptyError(etEmail , "من فضلك املأ الخانة");
        }else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
            emptyError(etEmail,"الاميل خاطئ");
        } else if (ettelephone.getText().toString().equals("")){
            emptyError(ettelephone , "من فضلك املأ الخانة");
        }else if (etlocation.getText().toString().equals("")){
            emptyError(etlocation , "من فضلك املأ الخانة");
        }else if (etPassword.getText().toString().equals("")){
            emptyError(etPassword , "من فضلك املأ الخانة");
        } else if (etPassword.getText().toString().length()<6 ){
            emptyError(etPassword , "يدب 6احرف او أكثر");
        } else if (ed_Sign_passwordConf.getText().toString().equals("")){
            emptyError(ed_Sign_passwordConf , "من فضلك املأ الخانة");
        }else if (!ed_Sign_passwordConf.getText().toString().equals(etPassword.getText().toString())){
            emptyError(ed_Sign_passwordConf , "كلمة السر غير متطابقة");
        }else if (!etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()) {
            user_normale user = new user_normale( etEmail.getText().toString(),ettelephone.getText().toString(),etPassword.getText().toString() ,
                    etlocation.getText().toString() , ed_username.getText().toString(),"user" , "false" );
            user.Cree_compte(SignupActivity.this,mAuth);


        }

    }
    private void emptyError(EditText editText , String error){
        editText.setError(error);
        editText.requestFocus();
    }

}