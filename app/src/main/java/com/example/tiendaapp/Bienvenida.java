package com.example.tiendaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Bienvenida extends AppCompatActivity {

    //
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

        mAuth = FirebaseAuth.getInstance();
        Button pedir_cita = (Button) findViewById(R.id.button_pedir);
        Button ver_cita = (Button) findViewById(R.id.button_ver);
        Button cerrar_sesion = (Button) findViewById(R.id.buttonCerrarSession);

        /*Para dejarlo mejor lo convertimos:
        public void onClick(View v) {
        startActivity(new Intent(Bienvenida.this,PedirCita.class));
        } a lambda.*/
        pedir_cita.setOnClickListener(v -> startActivity(new Intent(Bienvenida.this, Servicios.class)));

        /*  Para dejarlo mejor lo convertimos a lambda:
            ver_cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Bienvenida.this, Citas.class));
            }});*/
        ver_cita.setOnClickListener(v -> startActivity(new Intent(Bienvenida.this, Citas.class)));

        /*Para dejarlo mejor lo convertimos a lambda:
            cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(Bienvenida.this, MainActivity.class));
                finish();
            }});*/
        cerrar_sesion.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(Bienvenida.this, Login.class));
            finish();
        });
    }
}