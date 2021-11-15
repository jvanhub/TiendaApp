package com.example.tiendaapp;

import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.TAG;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditFragment extends Fragment {
    private Button btConfir, btVolver, btConfir1;
    private EditText etNombre, etAp1, etAp2, etTelf, etEail, etEmailNuev, etEmailnuevConf,etEmailPass;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private String nombreBBDD, ap1BBDD, ap2BBDD, nTelfBBDD, emailBBDD;
    private String nombre, ap1, ap2, nTelf, email,emailConf, emailNuevo, emailPass;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_edit, container, false);

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btConfir = view.findViewById(R.id.buttonModConfir);
        btConfir1 = view.findViewById(R.id.buttonModConfir1);
        btVolver = view.findViewById(R.id.buttonModVolver);

        etNombre = view.findViewById(R.id.editTextModNombre);
        etAp1 = view.findViewById(R.id.editTextModApe1);
        etAp2 = view.findViewById(R.id.editTextModApe2);
        etTelf = view.findViewById(R.id.editTextModPhone);
        etEail = view.findViewById(R.id.editTextTextMailMod);
        etEmailNuev = view.findViewById(R.id.editTextTextMailNuevo);
        etEmailnuevConf = view.findViewById(R.id.editTextTextMailNuevoCon);
        etEmailPass = view.findViewById(R.id.editTextTextMailContraseña);
        extraerDatosBBDD();

        btConfir1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombre = etNombre.getText().toString();
                ap1 = etAp1.getText().toString();
                ap2 = etAp2.getText().toString();
                nTelf = etTelf.getText().toString();

                if(nombre.isEmpty()||ap1.isEmpty()||ap2.isEmpty()||nTelf.isEmpty()){
                    Toast.makeText(view.getContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }else if(nTelf.length() <9 || nTelf.length() >9){
                    Toast.makeText(view.getContext(), "Número de telefono incorrecto", Toast.LENGTH_SHORT).show();
                }else{
                    modificarDatosBBDD();
                }
            }
        });

        btConfir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEail.getText().toString();
                emailNuevo = etEmailNuev.getText().toString();
                emailConf = etEmailnuevConf.getText().toString();
                emailPass = etEmailPass.getText().toString();
                Matcher mather = pattern.matcher(emailNuevo);

                if (email.isEmpty()||emailNuevo.isEmpty()||emailConf.isEmpty()||emailPass.isEmpty()){
                    Toast.makeText(view.getContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                } else if(mather.find()==false){
                    Toast.makeText(view.getContext(), "Email no valido", Toast.LENGTH_SHORT).show();
                } else if (!emailNuevo.equals(emailConf)){
                    Toast.makeText(view.getContext(), "Los emails no coinciden", Toast.LENGTH_SHORT).show();
                } else{
                    //modificarEmailBBDD();
                }
            }
        });

        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), Bienvenida.class));            }
        });
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

    /*public void modificarEmailBBDD(){
        if()) {
            AuthCredential credential = EmailAuthProvider
                    .getCredential(email, emailPass);

            // Prompt the user to re-provide their sign-in credentials
            mAuth.getCurrentUser().reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "User re-authenticated.");
                        }
                    });

            mAuth.getCurrentUser().updateEmail("ivaneme.diaz@gmail.com")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(view.getContext(), "cambio", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            Map<String, Object> map = new HashMap<>();
            map.put("nombres", nombre);
            map.put("apellidos", ap1);
            map.put("apellidos2", ap2);
            map.put("n_telefonos", nTelf);
            map.put("emails", email);
            mDatabase.child("Usuarios").child(mAuth.getUid()).setValue(map);
            startActivity(new Intent(view.getContext(), Perfil.class));
            Toast.makeText(view.getContext(), "Se ha completado el cambio", Toast.LENGTH_LONG).show();

        }
    }*/
    public void modificarDatosBBDD(){

            Map<String, Object> map = new HashMap<>();
            map.put("nombres", nombre);
            map.put("apellidos", ap1);
            map.put("apellidos2", ap2);
            map.put("n_telefonos", nTelf);
            map.put("emails", email);
            mDatabase.child("Usuarios").child(mAuth.getUid()).setValue(map);
            startActivity(new Intent(view.getContext(), Perfil.class));
            Toast.makeText(view.getContext(), "Se ha completado el cambio", Toast.LENGTH_LONG).show();

    }
}