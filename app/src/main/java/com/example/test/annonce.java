package com.example.test;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

abstract public class annonce {
    protected String id_announce ;
    protected String id_user;
    protected String date ;
    protected String user_name    ;
    protected String numero_tlph;
    protected String ville ;
    public annonce (){};
    public annonce(String id_announce, String id_user, String date, String user_name, String numero_tlph, String ville) {
        this.id_announce = id_announce;
        this.id_user = id_user;
        this.date = date;
        this.user_name = user_name;
        this.numero_tlph = numero_tlph;
        this.ville = ville;
    }
    public static void supprimer_annonce(Context context,String key)
    {
        FirebaseDatabase.getInstance().getReference().child("annonces").child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "تم حذف المنشور", Toast.LENGTH_SHORT).show();
                } else {
                    String err = task.getException().toString();
                    Toast.makeText(context, "لم يتم حذف المنشور" + err, Toast.LENGTH_SHORT).show();
                }
            }
        });;
    };
    abstract public void ajouter_annonce(annonce_mdcn cn, Dialog dialog , ProgressDialog loader, Context context,DatabaseReference reference);

}
