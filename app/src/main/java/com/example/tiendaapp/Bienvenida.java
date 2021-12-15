package com.example.tiendaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Esta clase es la encargada de dar funcionalidad al activity_bienvenida.
 */
public class Bienvenida extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

        mAuth = FirebaseAuth.getInstance();
        Button pedir_cita = (Button) findViewById(R.id.button_pedir);
        Button ver_cita = (Button) findViewById(R.id.button_ver);
        Button cerrar_sesion = (Button) findViewById(R.id.buttonCerrarSession);
        Button perfil = (Button) findViewById(R.id.button_perfil);

        /**
         * Evento para acceder a la clase y activity de "Perfil" al pulsar el botón "Perfil".
         */
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Bienvenida.this, Perfil.class));
            }
        });

        /**
         * Evento para acceder a la clase y activity de "Pedir Cita" al pulsar el botón "Pedir cita".
         */
        pedir_cita.setOnClickListener(v -> startActivity(new Intent(Bienvenida.this, Servicios.class)));

        /**
         * Evento para acceder a la clase y activity de "Ver cita" al pulsar el botón "Ver citas".
         */
        ver_cita.setOnClickListener(v -> startActivity(new Intent(Bienvenida.this, Citas.class)));

        /**
         * Evento para acceder a la clase y activity de "Login" al pulsar el botón "Cerrar sesión" que además cierra la sesión
         * en el servidor mediante el método signOut.
         */

        cerrar_sesion.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(Bienvenida.this, Login.class));
            finish();
        });
    }
}