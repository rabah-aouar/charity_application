package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class medicament_annonce extends AppCompatActivity {
    private ImageButton add;
    private EditText search_bar;
    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private ProgressDialog loader;
    private String m_mdn1;
    private String key;
    private String date_p;
    private int quantite;
    ArrayList<String> list=new ArrayList<>(); //la liste des medicaments
    ArrayAdapter<String> adapter1;   //autocomplet adapter
    private String item_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament_annonce);
        add = this.findViewById(R.id.addd);
        search_bar = findViewById(R.id.search_bar);
        reference = FirebaseDatabase.getInstance().getReference().child("annonces");
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(medicament_annonce.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        loader = new ProgressDialog(this);// make object loader
        //click to add a medicament
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_ajouter_medecament();
            }
        });
       load_data("");
        //search in the medicaments that existe
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null) {
                    load_data(s.toString().toUpperCase());
                } else {
                    load_data("");
                }
            }
        });
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("medecaments").child("-M_TfTfrYVtU4tAmhHRM");
        adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        // get list of medicaments
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {

                    list.add(snapshot1.getValue().toString());
                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void dialog_ajouter_medecament() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(medicament_annonce.this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View myview = inflater.inflate(R.layout.ecc, null);
        mydialog.setView(myview);
        AlertDialog dialog = mydialog.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//make the background of alertdialog transparent
        dialog.show();
        //initialiser views
        final AutoCompleteTextView nom_medecament = myview.findViewById(R.id.nom_medecament);
        final EditText quantite = myview.findViewById(R.id.quantite);
        final EditText Date_p = myview.findViewById(R.id.Date_p);
         final Button ajouter = myview.findViewById(R.id.add_medecament);
        ImageView dimiss = myview.findViewById(R.id.imageView);
        nom_medecament.setAdapter(adapter1);
        nom_medecament.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_mdn1 = nom_medecament.getText().toString();
            }
        });



        //sortir de dialog
        dimiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        }); //dismiss the dialog


        //ajouter le medecament dans la base de donne
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m_mdn = m_mdn1;
                String mquantite = quantite.getText().toString();
                String mdate_p = Date_p.getText().toString();
                String id;
                if (TextUtils.isEmpty(m_mdn)) {
                    Toast.makeText(getApplicationContext(), "من فضلك اختر احد الادوية من القائمة", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mquantite)) {
                    Toast.makeText(medicament_annonce.this, "من فضلك حدد الكمية", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mdate_p)) {
                    Toast.makeText(medicament_annonce.this, "من فضلك ادخل تاريخ نهاية الصلاحية", Toast.LENGTH_SHORT).show();
                } else {
                    loader.setMessage("جاري اضافة الدواء");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    id = reference.push().getKey();
                    annonce_mdcn cn = new annonce_mdcn(id, m_mdn, Integer.parseInt(mquantite), mdate_p,"2");
                    cn.ajouter_annonce(cn, dialog, loader, medicament_annonce.this,reference);

                }
            }
        });
    }

    //recyclerview
    public void add_new_medecament(String nom){
        FirebaseDatabase.getInstance().getReference().child("medecaments").child("-M_TfTfrYVtU4tAmhHRM").push().setValue(nom);
    }
        private void load_data (String text)//load data from firebase to the recyclerview
        {
            Query query=reference.orderByChild("nom_medecament").startAt(text).endAt(text+"\uf8ff");
            FirebaseRecyclerOptions<annonce_mdcn> options = new FirebaseRecyclerOptions.Builder<annonce_mdcn>()
                    .setQuery(query, annonce_mdcn.class)
                    .build();
            FirebaseRecyclerAdapter<annonce_mdcn, MyViewHolder> adapter = new FirebaseRecyclerAdapter<annonce_mdcn, MyViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull final annonce_mdcn model) {
                    holder.setnom_medecament("اسم الدواء :" + model.getNom_medecament());
                    holder.setquantite("الكمية :" + String.valueOf(model.getQuantite()));
                    holder.setDate_p("تاريخ نهاية الصلاحية : " + model.getDate_expiration());
                    holder.setnum_phone("رقم الهاتف : 0561142190 ");
                    holder.setusername("اسم المستخدم : rabah");
                    holder.setdate_pub("22/05/2022");
                    holder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            key = getRef(position).getKey(); //get the id of the
                            m_mdn1=model.getNom_medecament();
                            quantite=model.getQuantite();
                            date_p=model.getDate_expiration();
                            //item_user_id=model.getid_user().toString();
                            //equals user.type or current user id == annonce.user_id

                                    update_annonce();

                        }
                    });
                }

                @NonNull
                @Override
                public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design, parent, false);
                    return new MyViewHolder(view);
                }
            };

            recyclerView.setAdapter(adapter);
            adapter.startListening();
        }
    private void update_annonce() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(medicament_annonce.this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.update_delete, null);
        mydialog.setView(view);
        AlertDialog dialog = mydialog.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//make the background of alertdialog transparent
        dialog.show();


            Button delButton = view.findViewById(R.id.supprimer);
            Button updateButton = view.findViewById(R.id.modifier);
            ImageView dimiss = view.findViewById(R.id.imageView);
            dimiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            }); //dismiss the dialog

            delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    annonce.supprimer_annonce(medicament_annonce.this, key);
                    dialog.dismiss();
                }
            });

        }

        //recycler view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            View mView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                mView = itemView;
            }
            public void setnom_medecament(String nom_medecament) {
                TextView nom_medecament_item = mView.findViewById(R.id.nom_medecament_item);
                nom_medecament_item.setText(nom_medecament);
            }

            public void setquantite(String quantite) {
                TextView quantite_item = mView.findViewById(R.id.quantite_item);
                quantite_item.setText(quantite);
            }

            public void setDate_p(String Date_p) {
                TextView Date_p_item = mView.findViewById(R.id.Date_p_item);
                Date_p_item.setText(Date_p);
            }
            public void setnum_phone(String num)
            {
                TextView Date_p_item = mView.findViewById(R.id.num_phone);
                Date_p_item.setText(num);
            }
            public void setusername(String username)
            {
                TextView Date_p_item = mView.findViewById(R.id.username);
                Date_p_item.setText(username);
            }
            public void setdate_pub(String Date_pub)
            {
                TextView Date_p_item = mView.findViewById(R.id.Date_pub);
                Date_p_item.setText(Date_pub);
            }

        } //viewwholder class

    }



