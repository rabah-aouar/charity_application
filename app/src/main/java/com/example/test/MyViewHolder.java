package com.example.test;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

     class MyViewHolder extends RecyclerView.ViewHolder {
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

    }

