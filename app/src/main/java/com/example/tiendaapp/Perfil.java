package com.example.tiendaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class Perfil extends AppCompatActivity {
    private Button btConfir, btVolver;
    private EditText etNombre, etAp1, etAp2, etTelf, etEail, etEmailConf;
    private TextView etInfo;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private String nombreBBDD, ap1BBDD, ap2BBDD, nTelfBBDD, emailBBDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btConfir = (Button) findViewById(R.id.buttonModConfir);
        btVolver = (Button) findViewById(R.id.buttonModVolver);

        etNombre = (EditText) findViewById(R.id.editTextModNombre);
        etAp1 = (EditText) findViewById(R.id.editTextModApe1);
        etAp2 = (EditText) findViewById(R.id.editTextModApe2);
        etTelf = (EditText) findViewById(R.id.editTextModPhone);
        etEail = (EditText) findViewById(R.id.editTextTextMailMod);
        etEmailConf = (EditText) findViewById(R.id.editTextTextMailModCon);

        extraerDatosBBDD();

        btConfir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Perfil.this, Bienvenida.class));
            }
        });
    }

    public void extraerDatosBBDD() {

        mDatabase.child("Usuarios").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nombreBBDD = snapshot.child("nombres").getValue().toString();
                ap1BBDD = snapshot.child("apellidos").getValue().toString();
                ap2BBDD = snapshot.child("apellidos2").getValue().toString();
                nTelfBBDD = snapshot.child("n_telefonos").getValue().toString();
                emailBBDD = snapshot.child("emails").getValue().toString();
                Toast.makeText(Perfil.this, snapshot.toString(), Toast.LENGTH_LONG).show();
                enviarDatosEt();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Perfil.this, "Error BBDD", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void enviarDatosEt() {
        etNombre.setText(nombreBBDD);
        etAp1.setText(ap1BBDD);
        etAp2.setText(ap2BBDD);
        etTelf.setText(nTelfBBDD);
        etEail.setText(emailBBDD);

    }

}