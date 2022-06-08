package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import maes.tech.intentanim.CustomIntent;

public class home extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button logout;
    private EditText nom;
    private EditText prenom;
    private EditText num_phone;
    private Spinner spinner;
    private AutoCompleteTextView grp;
    private String blood_tp;
    private TextView grp1;
    private String is_selected="false";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        CustomIntent.customType(home.this, "up-to-bottom");
        nom=findViewById(R.id.nom);
        prenom=findViewById(R.id.prenom);
        num_phone=findViewById(R.id.num_phone);
        spinner=findViewById(R.id.spinner);
        //grp1=findViewById(R.id.grp1);
        grp=findViewById(R.id.grp);
        String[] contries=getResources().getStringArray(R.array.contries);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(home.this,android.R.layout.simple_list_item_1,contries);
        grp.setAdapter(adapter1);
        grp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                is_selected="true";
                String item =parent.getItemAtPosition(position).toString();;
                Toast.makeText(home.this,item+"",Toast.LENGTH_SHORT).show();
                grp1.setText(item);
            }
        });

        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.blood_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        grp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               /* is_selected="false";
                Toast.makeText(home.this,is_selected+"",Toast.LENGTH_SHORT).show();*/

            }
        });



        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(home.this, hhhhhh.class));
                CustomIntent.customType(home.this, "left-to-right");//animate the transaction between activities {left-to-right;fadein-to-fadeout;yp-to-battom}
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        blood_tp=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
