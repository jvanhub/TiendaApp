package com.example.tiendaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Bienvenida extends AppCompatActivity {
    //
    private Button cerrar_sesion;
    private Button pedir_cita;
    private Button ver_cite;

    //
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

        //
        mAuth = FirebaseAuth.getInstance();

        pedir_cita = (Button) findViewById(R.id.button_pedir);
        ver_cite = (Button) findViewById(R.id.button_ver);

        cerrar_sesion = (Button) findViewById(R.id.buttonCerrarSession);

        pedir_cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Bienvenida.this,PedirCita.class));

            }
        });

        /*ver_cite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Bienvenida.this, .class));

            }
        });*/

        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(Bienvenida.this, MainActivity.class));
                finish();
            }
        });
    }
}