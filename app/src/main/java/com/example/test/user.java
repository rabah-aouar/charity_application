package com.example.test;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.PrimitiveIterator;
import android.content.Intent;

import maes.tech.intentanim.CustomIntent;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.startActivity;


class User {
    protected String id;
    protected String type;
    protected String email ;
    protected String numero_tlph;
    protected String password;
    protected String ville;



    protected String disable;
    protected User(){};

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String id, String email, String numero_tlph, String password, String ville, String type, String disable) {
        this.id = id;
        this.email = email;
        this.numero_tlph = numero_tlph;
        this.password = password;
        this.ville = ville;
        this.type = type;
        this.disable = disable;
    }

    public User(String email, String numero_tlph, String password, String ville, String type, String disable) {

        this.email = email;
        this.numero_tlph = numero_tlph;
        this.password = password;
        this.ville = ville;
        this.type = type;
        this.disable = disable;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumero_tlph() {
        return numero_tlph;
    }

    public void setNumero_tlph(String numero_tlph) {
        this.numero_tlph = numero_tlph;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisable() {
        return disable;
    }

    public void setDisable(String disable) {
        this.disable = disable;
    }



    public void login_compte(final Context context , final FirebaseAuth mAuth){
 
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    String userId = "";

                    if (firebaseUser != null) {
                        userId = firebaseUser.getUid();
                    }
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String value = snapshot.child("type").getValue().toString();
                            if (value.equals("association")){
                                String situation = snapshot.child("situation").getValue().toString();
                                switch (situation){
                                    case "attend":
                                        Toast.makeText(context , "من فضلك انتضر حتى يتم قبولك",Toast.LENGTH_LONG).show();
                                        afficheAlert(context, "حسابك قيد الانتضار" , "attend");
                                        break;
                                    case "refuse":
                                        Toast.makeText(context , "نعتظر لم يتم قبوووولك",Toast.LENGTH_LONG).show();
                                        afficheAlert(context , "قد تم رفض حسابك" , "refuse");
                                        break;
                                    default:
                                        Toast.makeText(context , "تم الدخول بنجاح " , Toast.LENGTH_LONG).show();
                                        Intent NextLevel = new Intent(context , Pageprincipale.class);
                                        startActivity(context,NextLevel,null);
                                        CustomIntent.customType(context , "left-to-right");
                                        break;
                                }
                            }else {
                                Toast.makeText(context , "تم الدخول بنجاح " , Toast.LENGTH_LONG).show();
                                Intent NextLevel = new Intent(context , Pageprincipale.class);
                                startActivity(context,NextLevel,null);
                                CustomIntent.customType(context , "left-to-right");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    Toast.makeText(context , task.getException().toString() , Toast.LENGTH_LONG).show();
                }
            }
        }) ;
    }
    public void log_out(final Context context ){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(context , "استودعك الله " , Toast.LENGTH_LONG).show();
        Intent NextLevel = new Intent(context , LoginActivity.class);
        startActivity(context,NextLevel,null);
        CustomIntent.customType(context , "left-to-right");
    }

    public void type_user(final FirebaseAuth mAuth){
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String userId = "";
        if (firebaseUser != null) {
            userId = firebaseUser.getUid();
        }
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.child("type").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void afficheAlert(final Context context  , String text , final String type){
        final Dialog dialog2;
        dialog2 = new Dialog(context);
        dialog2.setContentView( R.layout.alertdialog_refuse);
        dialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textXml = dialog2.findViewById(R.id.textView3);
        textXml.setText(text);

        dialog2.findViewById(R.id.btn_quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
                switch (type){
                    case "attend":
                        Intent NextLevelA = new Intent(context , LoginActivity.class);
                        startActivity(context,NextLevelA,null);
                        CustomIntent.customType(context , "left-to-right");
                        break;
                    case "refuse":
                        Intent NextLevelR = new Intent(context , LoginActivity.class);
                        startActivity(context,NextLevelR,null);
                        CustomIntent.customType(context , "left-to-right");
                        //Supp
                        Toast.makeText(context  ,"",Toast.LENGTH_LONG).show();
                        break;

                }

            }

        });


        dialog2.setCancelable(false);
        dialog2.show();


    }
}
