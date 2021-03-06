package com.example.tiendaapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Esta clase es la encargada de cargar los datos en el fragment_perfil.
 */
public class PerfilFragment extends Fragment {

    private Button btVolver;
    private TextView tvNombre, tvAp, tvTelf, tvEail;
    private String nombreBBDD, ap1BBDD, ap2BBDD, nTelfBBDD, emailBBDD;
    FirebaseAuth mmAuth;
    DatabaseReference mmDatabase;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        mmAuth = FirebaseAuth.getInstance();
        mmDatabase = FirebaseDatabase.getInstance().getReference();

        btVolver = view.findViewById(R.id.buttonVolverPer);
        tvNombre = view.findViewById(R.id.textViewNom2);
        tvAp = view.findViewById(R.id.textViewApex2);
        tvTelf = view.findViewById(R.id.textViewTelf2);
        tvEail = view.findViewById(R.id.textViewEmail2);

        /**
         * Recoge los datos de la BBDD para mostrarlos.
         */
        mmDatabase.child("Usuarios").child(mmAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nombreBBDD = snapshot.child("nombres").getValue().toString();
                ap1BBDD = snapshot.child("apellidos").getValue().toString();
                ap2BBDD = snapshot.child("apellidos2").getValue().toString();
                nTelfBBDD = snapshot.child("n_telefonos").getValue().toString();
                emailBBDD = snapshot.child("emails").getValue().toString();
                tvNombre.setText(nombreBBDD);
                tvAp.setText(ap1BBDD + " " + ap2BBDD);
                tvTelf.setText(nTelfBBDD);
                tvEail.setText(emailBBDD);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        /**
         * Evento que accede a la clase y activity de "Bienvenida" al pulsar el bot??n "Vovler".
         */
        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), Bienvenida.class));
            }
        });
        return view;
    }
}