package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class actualite extends AppCompatActivity {
    private RecyclerView recyclerView_mdcn;
    private RecyclerView recyclerView_sang;
    private RecyclerView recyclerView_autre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualite);
        recyclerView_mdcn=findViewById(R.id.recycler_view_mdcn);

        recyclerView_sang=findViewById(R.id.recycler_view_sang);
        recyclerView_autre=findViewById(R.id.recycler_view_autre);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(actualite.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView_mdcn.setLayoutManager(linearLayoutManager);
        recyclerView_sang.setLayoutManager(linearLayoutManager);
        recyclerView_autre.setLayoutManager(linearLayoutManager);
      //  load_data1();
     //   load_data2();
      //  loadData3();

    }



}