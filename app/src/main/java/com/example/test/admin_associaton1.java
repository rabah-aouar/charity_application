package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Barrier;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class admin_associaton1 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_associaton1);

        reference = FirebaseDatabase.getInstance().getReference().child("association");
        recyclerView = findViewById(R.id.recycler_view2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(admin_associaton1.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        load_data("");
    }


    private void load_data (String text)//load data from firebase to the recyclerview
    {
        FirebaseRecyclerOptions<association> options = new FirebaseRecyclerOptions.Builder<association>()
                .setQuery(reference,association.class)
                .build();

            FirebaseRecyclerAdapter<association, admin_associaton1.MyViewHolder1> adapter = new FirebaseRecyclerAdapter<association, admin_associaton1.MyViewHolder1>(options) {
            @Override
            protected void onBindViewHolder(@NonNull admin_associaton1.MyViewHolder1 holder, final int position, @NonNull final association model) {
                holder.setemail("البريد الاكتروني : "+model.getEmail());
                holder.setville("الولاية:" + model.getVille());
                holder.setnum_phone("رقم الهاتف : "+model.getNumero_tlph());
                holder.setnom_complet("الاسم الكامل :  "+model.getNom_complet());
                holder.setdate_pub("10/11/2022");
                holder.setaddress("العنوان  : "+model.getAdress());
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        key = getRef(position).getKey();
                        approver_association();

                    }
                });
            }

            @NonNull
            @Override
            public admin_associaton1.MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design_association, parent, false);
                return new MyViewHolder1(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void approver_association() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(admin_associaton1.this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.approvation_association, null);
        mydialog.setView(view);
        AlertDialog dialog = mydialog.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//make the background of alertdialog transparent
        dialog.show();

        Button accept=view.findViewById(R.id.accept);
        Button supprimer=view.findViewById(R.id.supprimer);
        ImageView dimiss = view.findViewById(R.id.imageView);
        dimiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        }); //dismiss the dialog


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(key).child("situation").setValue("accepted");
            }
        });

        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(admin_associaton1.this, "Task deleted successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            String err = task.getException().toString();
                            Toast.makeText(admin_associaton1.this, "Failed to delete task " + err, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.dismiss();

            }
        });
    }

    public static class MyViewHolder1 extends RecyclerView.ViewHolder {
        View mView;

        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setemail(String email) {
            TextView nom_medecament_item = mView.findViewById(R.id.email);
            nom_medecament_item.setText(email);
        }

        public void setnum_phone(String num)
        {
            TextView Date_p_item = mView.findViewById(R.id.num_phone);
            Date_p_item.setText(num);
        }

        public void setdate_pub(String Date_pub)
        {
            TextView Date_pub_item = mView.findViewById(R.id.Date_pub);
            Date_pub_item.setText(Date_pub);
        }
        public void setaddress(String address)
        {
            TextView address_item = mView.findViewById(R.id.address);
            address_item.setText(address);
        }
        public void setnom_complet(String nom_complet)
        {
            TextView nom_complet_item = mView.findViewById(R.id.nom_complet);
            nom_complet_item.setText(nom_complet);
        }
        public void setville (String ville)
        {
            TextView ville_item = mView.findViewById(R.id.ville);
            ville_item.setText(ville);
        }
    }

}