package com.example.tiendaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Esta clase es la encargada de dar funcionalidad al activity_perfil.
 */
public class Perfil extends AppCompatActivity {

    private Button btPerfil, btEditar, btEditar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        btPerfil = (Button) findViewById(R.id.buttonPerfil);
        btEditar = (Button) findViewById(R.id.buttonEdit);
        btEditar1 = (Button) findViewById(R.id.buttonEdit2);

        /**
         * Evento para acceder a la clase "PerfilFragment" al pulsar el botón "Resumen".
         */
        btPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new PerfilFragment());
            }
        });

        /**
         * Evento para acceder a la clase "EditFragment" al pulsar el botón "Editar Perfil".
         */
        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new EditFragment());
            }
        });

        /**
         * Evento para acceder a la clase "EditFragment2" al pulsar el botón "Editar Email".
         */
        btEditar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new EditFragment2());
            }
        });
    }

    /**
     * Método encargado de sustituir los Fragment.
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contenedorFragments, fragment);
        ft.commit();
    }
}