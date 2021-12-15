package com.example.tiendaapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta clase es la encargada de dar funcionalidad al fragmen_edit.
 */
public class EditFragment extends Fragment {
    private Button btConfir, btVolver;
    private EditText etNombre, etAp1, etAp2, etTelf;
    private String nombreBBDD, ap1BBDD, ap2BBDD, nTelfBBDD, emailBBDD, nombre, ap1, ap2, nTelf;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_edit, container, false);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btConfir = view.findViewById(R.id.buttonModConfir);
        btVolver = view.findViewById(R.id.buttonModVolver);
        etNombre = view.findViewById(R.id.editTextModNombre);
        etAp1 = view.findViewById(R.id.editTextModApe1);
        etAp2 = view.findViewById(R.id.editTextModApe2);
        etTelf = view.findViewById(R.id.editTextModPhone);
        extraerDatosBBDD();

        /**
         * Evento encargado de acceder al método de "modificarDatosBBDD" que se encarga
         * de modificar los datos personales del usuario en la BBDD y hacer las diferentes
         * comprobaciones.
         */
        btConfir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = etNombre.getText().toString();
                ap1 = etAp1.getText().toString();
                ap2 = etAp2.getText().toString();
                nTelf = etTelf.getText().toString();
                if (nombre.isEmpty() || ap1.isEmpty() || ap2.isEmpty() || nTelf.isEmpty()) {
                    Toast.makeText(view.getContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                } else if (nTelf.length() < 9 || nTelf.length() > 9) {
                    Toast.makeText(view.getContext(), "Número de telefono incorrecto", Toast.LENGTH_SHORT).show();
                } else {
                    modificarDatosBBDD();
                }
            }
        });

        /**
         * Método encargado de crear e introducir los datos en cada elemento.
         */
        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), Bienvenida.class));
            }
        });
        return view;
    }

    /**
     * Método encargado de recoger los datos del usuario, de la BBDD.
     */
    public void extraerDatosBBDD() {
        mDatabase.child("Usuarios").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nombreBBDD = snapshot.child("nombres").getValue().toString();
                ap1BBDD = snapshot.child("apellidos").getValue().toString();
                ap2BBDD = snapshot.child("apellidos2").getValue().toString();
                nTelfBBDD = snapshot.child("n_telefonos").getValue().toString();
                emailBBDD = snapshot.child("emails").getValue().toString();
                etNombre.setText(nombreBBDD);
                etAp1.setText(ap1BBDD);
                etAp2.setText(ap2BBDD);
                etTelf.setText(nTelfBBDD);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    /**
     * Método encargado de modificar los datos del usuario, de la BBDD.
     */
    public void modificarDatosBBDD() {
        Map<String, Object> map = new HashMap<>();
        map.put("nombres", nombre);
        map.put("apellidos", ap1);
        map.put("apellidos2", ap2);
        map.put("n_telefonos", nTelf);
        map.put("emails", emailBBDD);
        mDatabase.child("Usuarios").child(mAuth.getUid()).setValue(map);
        startActivity(new Intent(view.getContext(), Perfil.class));
        Toast.makeText(view.getContext(), "Se ha completado el cambio", Toast.LENGTH_LONG).show();
    }
}