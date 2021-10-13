package com.example.tiendaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class Bienvenido extends AppCompatActivity {

    private Button cerrar_sesion;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

      /*  mAuth = FirebaseAuth.getInstance();
        cerrar_sesion = (Button) findViewById(R.id.buttonCerrarSession);

        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(Bienvenido.this, MainActivity.class));
                finish();
            }
        });*/
    }
}