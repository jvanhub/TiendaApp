package com.example.tiendaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Perfil extends AppCompatActivity {
    private Button btConfir, btVolver;
    private EditText etNombre, etAp1, etAp2, etTelf, etEail, etEmailConf;
    private TextView etInfo;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btConfir = (Button) findViewById(R.id.buttonModConfir);
        btVolver = (Button) findViewById(R.id.buttonModVolver);

        etNombre = (EditText) findViewById(R.id.editTextModNombre);
        etAp1 = (EditText) findViewById(R.id.editTextApe1);
        etAp2 = (EditText) findViewById(R.id.editTextModApe2);
        etTelf = (EditText) findViewById(R.id.editTextModPhone);
        etEail = (EditText) findViewById(R.id.editTextTextMailMod);
        etEmailConf = (EditText) findViewById(R.id.editTextTextMailModCon);

        btConfir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (){

                }
            }
        });

        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Perfil.this, Bienvenida.class));
            }
        });
    }


}