package com.example.test;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.loader.content.Loader;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class annonce_mdcn extends annonce {
    private String nom_medecament;
    private int quantite;
    private String date_expiration;

    public annonce_mdcn(){}

    @Override
    public void ajouter_annonce(annonce_mdcn cn, Dialog dialog , ProgressDialog loader, Context context,DatabaseReference reference) {
            String id;
            reference = FirebaseDatabase.getInstance().getReference().child("annonces");
            id = reference.push().getKey();
            reference.child(id).setValue(cn).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "تمت اضافة الدواء جزاك الله خيرا", Toast.LENGTH_SHORT).show();
                        loader.dismiss();
                        dialog.dismiss();
                    }
                }
            });
        }

    public annonce_mdcn(String id, String nom_medecament, int quantite, String mdate_p, String date_expiration) {
        this.nom_medecament = nom_medecament;
        this.quantite = quantite;
        this.date_expiration = date_expiration;
    }

    public annonce_mdcn(String id_announce, String id_user, String date, String user_name, String numero_tlph, String ville, String nom_medecament, int quantite, String date_expiration) {
        super(id_announce, id_user, date, user_name, numero_tlph, ville);
        this.nom_medecament = nom_medecament;
        this.quantite = quantite;
        this.date_expiration = date_expiration;
    }


    public String getNom_medecament() {
        return nom_medecament;
    }

    public void setNom_medecament(String nom_medecament) {
        this.nom_medecament = nom_medecament;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDate_expiration() {
        return date_expiration;
    }

    public void setDate_expiration(String date_expiration) {
        this.date_expiration = date_expiration;
    }
    public String getid_user()
    {
        return this.id_user;
    }
    public void setid_user(String id_user)
    {
        this.id_user=id_user;
    }
    public String getid_announce()
    {
        return this.id_announce;
    }

    public void setid_announce(String id_announce)
    {
        this.id_announce=id_announce;
    }

    public String getdate()
    {
        return this.date;
    };
    public void setdate(String date)
    {
        this.date=date;
    };
    public String getnumero_tlph()
    {
        return this.numero_tlph;
    }

    public void setnumero_tlph(String numero_tlph)
    {
        this.numero_tlph=numero_tlph;
    }

    public String getville()
    {
        return this.ville;
    }

    public void setville(String ville)
    {
        this.ville=ville;
    }


}
