package com.example.tiendaapp;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditFragment2 extends Fragment {

    private Button btVolver, btConfir1;
    private EditText etEail, etEmailNuev, etEmailnuevConf, etEmailPass;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    private String nombreBBDD, ap1BBDD, ap2BBDD, nTelfBBDD, emailBBDD;
    private String email, emailConf, emailNuevo, emailPass;
    private FirebaseUser user;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit2, container, false);
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btConfir1 = view.findViewById(R.id.buttonModConfir1);
        btVolver = view.findViewById(R.id.buttonModVolver);

        etEail = view.findViewById(R.id.editTextTextMailMod);
        etEmailNuev = view.findViewById(R.id.editTextTextMailNuevo);
        etEmailnuevConf = view.findViewById(R.id.editTextTextMailNuevoCon);
        etEmailPass = view.findViewById(R.id.editTextTextMailContraseña);
        extraerDatosBBDD();

        btConfir1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEail.getText().toString();
                emailNuevo = etEmailNuev.getText().toString();
                emailConf = etEmailnuevConf.getText().toString();
                emailPass = etEmailPass.getText().toString();
                Matcher mather = pattern.matcher(emailNuevo);

                if (email.isEmpty() || emailNuevo.isEmpty() || emailConf.isEmpty() || emailPass.isEmpty()) {
                    Toast.makeText(view.getContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                } else if (mather.find() == false) {
                    Toast.makeText(view.getContext(), "Email no valido", Toast.LENGTH_SHORT).show();
                } else if (!emailNuevo.equals(emailConf)) {
                    Toast.makeText(view.getContext(), "Los emails no coinciden", Toast.LENGTH_SHORT).show();
                } else {
                    modificarEmailBBDD();
                }
            }
        });

        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), Bienvenida.class));
            }
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
                etEail.setText(emailBBDD);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(view.getContext(), "Error al conectar en la BBDD. ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void modificarEmailBBDD() {
        if (email.equals(emailBBDD)) {
            AuthCredential credential = EmailAuthProvider.getCredential(email, emailPass);

            // Prompt the user to re-provide their sign-in credentials
            mAuth.getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    mAuth.getCurrentUser().updateEmail(etEmailNuev.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("nombres", nombreBBDD);
                                map.put("apellidos", ap1BBDD);
                                map.put("apellidos2", ap2BBDD);
                                map.put("n_telefonos", nTelfBBDD);
                                map.put("emails", etEmailNuev.getText().toString());
                                mDatabase.child("Usuarios").child(mAuth.getUid()).setValue(map);
                                startActivity(new Intent(view.getContext(), Perfil.class));
                                Toast.makeText(view.getContext(), "Se ha modificado el email", Toast.LENGTH_LONG).show();
                                user = mAuth.getCurrentUser();
                                user.sendEmailVerification();
                                Toast.makeText(view.getContext(), "Se ha enviado la verificación a su nuevo email", Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(view.getContext(), "error", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(view.getContext(), "Contraseña incorrecta.", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(view.getContext(), "El email es incorrecto.", Toast.LENGTH_LONG).show();
        }
    }
}