package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileWriter;
import java.io.IOException;

public class tp_isi extends AppCompatActivity {
    private EditText code_inscription,nom,prenom,sexe,date_nai,lieu_nais,adress,wilaya,num_telephone,emaill,annee_bac;
    private Button cree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp_isi);
        try {
            FileWriter mywriter =new FileWriter("etudiants.txt");
            mywriter.write("hhhhhhhhh");
        } catch (IOException e) {
            e.printStackTrace();
        }
        code_inscription=findViewById(R.id.code_inscription);
        nom=findViewById(R.id.nom);
        prenom=findViewById(R.id.prenom);
        sexe=findViewById(R.id.sexe);
        date_nai=findViewById(R.id.date_nai);
        lieu_nais=findViewById(R.id.lieu_nais);
        adress=findViewById(R.id.adress);
        wilaya=findViewById(R.id.wilaya);
        num_telephone=findViewById(R.id.num_telephone);
        annee_bac=findViewById(R.id.annee_bac);
        emaill=findViewById(R.id.email);
        cree=findViewById(R.id.cree);

        cree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mcode_inscription=code_inscription.getText().toString();
                String mnom=nom.getText().toString();
                String mprenom=prenom.getText().toString();
                String msexe=sexe.getText().toString();
                String mdate_nai=date_nai.getText().toString();
                String mlieu_nais=lieu_nais.getText().toString();
                String madress=adress.getText().toString();
                String mwilaya=wilaya.getText().toString();
                String mnum_telephone=num_telephone.getText().toString();
                String memail= emaill.getText().toString();
                String mannee_bac=annee_bac.getText().toString();
                if(mcode_inscription.isEmpty()||mnom.isEmpty()||mprenom.isEmpty()||
                        msexe.isEmpty()||mdate_nai.isEmpty()||mlieu_nais.isEmpty()||
                        madress.isEmpty()||mwilaya.isEmpty()||mannee_bac.isEmpty()||
                        memail.isEmpty())
                {
                    Toast.makeText(tp_isi.this, "un champ est vide", Toast.LENGTH_LONG);

                }
                else if (!verifier_email(memail)){Toast.makeText(tp_isi.this, "invalid email", Toast.LENGTH_LONG);}
                else {
                    String identifiant=mcode_inscription+mannee_bac;
                    if(verifier_identifient(identifiant))
                    {
                        ajouter_au_fichier(identifiant,mcode_inscription,mnom,mprenom,msexe,mdate_nai,mlieu_nais,madress,mwilaya,mnum_telephone,memail,mannee_bac);
                    }
                }
            }
        });

    }

    private void ajouter_au_fichier(String identifiant, String mcode_inscription, String mnom, String mprenom, String msexe, String mdate_nai, String mlieu_nais, String madress, String mwilaya, String mnum_telephone, String memail, String mannee_bac) {
        try {
            FileWriter mywriter =new FileWriter("etudiants.txt");
            mywriter.write(identifiant+","+mcode_inscription+","+mnom+","+mprenom+","+msexe+","+mdate_nai+","+mlieu_nais+","+madress+","+mwilaya+","+mnum_telephone+","+memail+","+mannee_bac);
        }
        catch (IOException e)
        {
            Toast.makeText(tp_isi.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean verifier_identifient(String identifiant) {
        return true;
    }

    public  boolean verifier_email(String email) {
        if( (occurence(email,'@')==1)
                &&email.contains("."))
        {
            int postion_arobase=email.indexOf("@");
            if (        // la longeur de pseudo inferieur a 64
                    postion_arobase<63  &&
                            // le premier charactere dans le pseudo n'est pas un char spéciale (@ inclu)
                            (
                                    (email.charAt(0)>='a'&&email.charAt(0)<='z') ||
                                            (email.charAt(0)>='A'&&email.charAt(0)<='Z')||
                                            ( email.charAt(0)<='9')
                            )&&
                            // le dernier charactere dans le pseudo n'est pas un char spéciale
                            (
                                    (email.charAt(postion_arobase-1)>='a'&&email.charAt(postion_arobase-1)<='z') ||
                                            (email.charAt(postion_arobase-1)>='A'&&email.charAt(postion_arobase-1)<='Z')||
                                            ( email.charAt(postion_arobase-1)<='9')
                            )
                            &&
                            // "." existe dans la partie apres @
                            email.substring(postion_arobase+1,email.length()-1).indexOf(".")!=-1 &&

                            // "." est separe au @ au moins d'un charactére
                            email.substring(postion_arobase+1,email.length()-1).indexOf(".")!=0 &&

                            // "." c'est pas le dernier charactere
                            email.charAt(email.length()-1)!='.'


            ) {
                for(int i=0; i<email.length()-1;i++)
                {
                    if(email.charAt(i)=='.'&&email.charAt(i+1)=='.')
                    {
                        return false;
                    }
                }
                return  true;
            }

            return false;
        }
        return false;
    }
    public  int occurence(String email, char c)
    {
        int s=0;
        for (int i=0; i<email.length();i++)
        {
            if(email.charAt(i)==c)
            {
                s++;
            }
        }

        return s;
    }
}