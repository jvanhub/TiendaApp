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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditFragment extends Fragment {
    private Button btConfir, btVolver;
    private EditText etNombre, etAp1, etAp2, etTelf, etEail, etEmailConf;
    private TextView etInfo;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private String nombreBBDD, ap1BBDD, ap2BBDD, nTelfBBDD, emailBBDD;
    private String nombre, ap1, ap2, nTelf, email,emailConf;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_edit, container, false);

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        nombre = etNombre.getText().toString();
        ap1 = etAp1.getText().toString();
        ap2 = etAp2.getText().toString();
        nTelf = etTelf.getText().toString();
        email = etEail.getText().toString();
        emailConf = etEmailConf.getText().toString();
        Matcher mather = pattern.matcher(email);

        if(nombre.isEmpty()||ap1.isEmpty()||ap2.isEmpty()||nTelf.isEmpty()||email.isEmpty()||emailConf.isEmpty()){
            Toast.makeText(view.getContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }else if(nTelf.length() <9 || nTelf.length() >9){
            Toast.makeText(view.getContext(), "Número de telefono incorrecto", Toast.LENGTH_SHORT).show();
        }else if(mather.find()==false){
            Toast.makeText(view.getContext(), "Email no valido", Toast.LENGTH_SHORT).show();
        }else if (!email.equals(emailConf)){
            Toast.makeText(view.getContext(), "Los emails no coinciden", Toast.LENGTH_SHORT).show();
        }else{
            modificarDatosBBDD();
            Toast.makeText(view.getContext(), "Ok", Toast.LENGTH_SHORT).show();
        }

        return view;
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
                etNombre.setText(nombreBBDD);
                etAp1.setText(ap1BBDD);
                etAp2.setText(ap2BBDD);
                etTelf.setText(nTelfBBDD);
                etEail.setText(emailBBDD);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void modificarDatosBBDD(){
        Map<String, Object> map = new HashMap<>();
        map.put("nombres", nombre);
        map.put("apellidos", ap1);
        map.put("apellidos2", ap2);
        map.put("n_telefonos", nTelf);
        map.put("emails", email);
        mDatabase.child("Usuarios").child(mAuth.getUid()).setValue(map);

    }
}