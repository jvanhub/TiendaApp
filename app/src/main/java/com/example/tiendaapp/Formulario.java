package com.example.tiendaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Formulario extends AppCompatActivity {

    private EditText et_nombre, et_ape1, et_ape2, et_n_telf, et_email, et_contrasenya1, et_contrasenya2;

    private String nombre = "";
    private String apellido1 = "";
    private String apellido2 = "";
    private String n_tefl = "";
    private String email = "";
    private String contrasenya = "";
    private String contrasenya2 = "";

    //Creación objeto Firebase:
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        et_nombre = (EditText) findViewById(R.id.editTextNombre);
        et_ape1 = (EditText) findViewById(R.id.editTextApe1);
        et_ape2 = (EditText) findViewById(R.id.editTextApe2);
        et_n_telf = (EditText) findViewById(R.id.editTextTelf);
        et_email = (EditText) findViewById(R.id.editTextEmail2);
        et_contrasenya1 = (EditText) findViewById(R.id.editTextPass1);
        et_contrasenya2 = (EditText) findViewById(R.id.editTextPass2);
        Button boton = (Button) findViewById(R.id.buttonRegistro2);

        /*Lo paso a lambda:
        "boton.setOnClickListener(new View.OnClickListener()"
        */
        boton.setOnClickListener(v -> {
            nombre = et_nombre.getText().toString();
            apellido1 = et_ape1.getText().toString();
            apellido2 = et_ape2.getText().toString();
            n_tefl = et_n_telf.getText().toString();
            email = et_email.getText().toString();
            contrasenya = et_contrasenya1.getText().toString();
            contrasenya2 = et_contrasenya2.getText().toString();

            if (nombre.isEmpty() || apellido1.isEmpty() || apellido2.isEmpty() ||
                    n_tefl.isEmpty() || email.isEmpty() || contrasenya.isEmpty() ||
                    contrasenya2.isEmpty()) {
                Toast.makeText(Formulario.this, "Completa todos los campos", Toast.LENGTH_LONG).show();

            } else if (contrasenya.length() < 6) {
                Toast.makeText(Formulario.this, "Debes introducir una contraseña mínimo de 6 caractéres.", Toast.LENGTH_LONG).show();
            } else if (contrasenya.equals(contrasenya2)) {
                registrar();
            } else {
                Toast.makeText(Formulario.this, "Contraseña 1 y 2 son diferentes.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void registrar() {
        /* Lo paso a lambda:
        mAuth.createUserWithEmailAndPassword(email,contrasenya).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        */
        mAuth.createUserWithEmailAndPassword(email, contrasenya).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                Map<String, Object> map = new HashMap<>();
                map.put("nombres", nombre);
                map.put("apellidos", apellido1);
                map.put("apellidos2", apellido2);
                map.put("n_telefonos", n_tefl);
                map.put("emails", email);
                map.put("contraseñas", contrasenya);

                /* Remplazo para evitar que pueda producir un NullPointerException.
                String id = task.getResult().getUser().getUid();*/
                String id = Objects.requireNonNull(task.getResult().getUser()).getUid();

                mDatabase.child("Usuarios").child(id).setValue(map);
                startActivity(new Intent(Formulario.this, SplashScreenMain.class));
            } else {
                Toast.makeText(Formulario.this, "No se pudo registrar el usuario.", Toast.LENGTH_LONG).show();
            }
        });
    }
}