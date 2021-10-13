package com.example.tiendaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Formulario extends AppCompatActivity {

    private EditText et_nombre, et_ape1, et_ape2, et_n_telf, et_email, et_contraseña1, et_contraseña2;
    private Button boton;

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
        et_email = (EditText) findViewById(R.id.editTextEmail);
        et_contraseña1 = (EditText) findViewById(R.id.editTextPass1);
        et_contraseña2 = (EditText) findViewById(R.id.editTextPass2);
        boton = (Button) findViewById(R.id.buttonRegistro2);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = et_nombre.getText().toString();
                apellido1 = et_ape1.getText().toString();
                apellido2 = et_ape2.getText().toString();
                n_tefl = et_n_telf.getText().toString();
                email = et_email.getText().toString();
                contrasenya = et_contraseña1.getText().toString();
                contrasenya2 = et_contraseña2.getText().toString();

                if (nombre.isEmpty() || apellido1.isEmpty() || apellido2.isEmpty() ||
                        n_tefl.isEmpty() || email.isEmpty() || contrasenya.isEmpty() ||
                        contrasenya2.isEmpty()) {
                    Toast.makeText(Formulario.this, "Completa todos los campos", Toast.LENGTH_LONG).show();

                } else if(contrasenya.length() < 6){
                    Toast.makeText(Formulario.this,"Debes introducir una contraseña mínimo de 6 caractéres.", Toast.LENGTH_LONG).show();
                } else if(contrasenya.equals(contrasenya2)){
                    registrar();
                }else{
                    Toast.makeText(Formulario.this,"Contraseña 1 y 2 son diferentes.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void registrar(){
        mAuth.createUserWithEmailAndPassword(email,contrasenya).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("nombres",nombre);
                    map.put("apellidos",apellido1);
                    map.put("apellidos2",apellido2);
                    map.put("n_telefonos",n_tefl);
                    map.put("emails",email);
                    map.put("contraseñas",contrasenya);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Usuarios").push().setValue(map);
                    startActivity(new Intent(Formulario.this, MainActivity.class));

                    /*addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(Formulario.this, MainActivity.class));
                            }else{
                                Toast.makeText(Formulario.this, "Error", Toast.LENGTH_LONG).show();
                            }
                        }
                    });*/
                }else{
                    Toast.makeText(Formulario.this, "No se pudo registrar el usuario.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}