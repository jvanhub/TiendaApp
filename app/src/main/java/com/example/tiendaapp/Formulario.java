package com.example.tiendaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta clase es la encargada de dar funcionalidad al activity_formulario.
 */
public class Formulario extends AppCompatActivity {
    private EditText et_nombre, et_ape1, et_ape2, et_n_telf, et_email, et_contrasenya1, et_contrasenya2;
    private String nombre, apellido1, apellido2, n_tefl, email, contrasenya, contrasenya2 = "";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
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

        /**
         * Evento que accede a la clase y activity de "Login" al pulsar en el bot??n "Confirmar",
         * realizando las diferentes condiciones y accediendo al m??todo "registrar()".
         */
        boton.setOnClickListener(v -> {
            nombre = et_nombre.getText().toString();
            apellido1 = et_ape1.getText().toString();
            apellido2 = et_ape2.getText().toString();
            n_tefl = et_n_telf.getText().toString();
            email = et_email.getText().toString();
            contrasenya = et_contrasenya1.getText().toString();
            contrasenya2 = et_contrasenya2.getText().toString();

            //Validaci??n Email.
            String r_email = email;
            Matcher mather = pattern.matcher(r_email);

            if (nombre.isEmpty() || apellido1.isEmpty() || apellido2.isEmpty() ||
                    n_tefl.isEmpty() || email.isEmpty() || contrasenya.isEmpty() ||
                    contrasenya2.isEmpty()) {
                Toast.makeText(Formulario.this, "Complete todos los campos", Toast.LENGTH_LONG).show();
            } else if (n_tefl.length() < 9 || n_tefl.length() > 9) {
                Toast.makeText(Formulario.this, "N??mero de telefono incorrecto", Toast.LENGTH_SHORT).show();
            } else if (mather.find() == false) {
                Toast.makeText(Formulario.this, "El email ingresado no es valido.", Toast.LENGTH_SHORT).show();
            } else if (contrasenya.length() < 6) {
                Toast.makeText(Formulario.this, "Debes introducir una contrase??a m??nimo de 6 caract??res.", Toast.LENGTH_LONG).show();
            } else if (contrasenya.equals(contrasenya2)) {
                registrar();
            } else {
                Toast.makeText(Formulario.this, "Contrase??a 1 y 2 son diferentes.", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * M??todo encargado de recoger, los datos introducidos por el usuario e insertarlos en la BBDD.
     */
    public void registrar() {
        mAuth.createUserWithEmailAndPassword(email, contrasenya).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Map<String, Object> map = new HashMap<>();
                map.put("nombres", nombre);
                map.put("apellidos", apellido1);
                map.put("apellidos2", apellido2);
                map.put("n_telefonos", n_tefl);
                map.put("emails", email);

                /* Remplazo para evitar que pueda producir un NullPointerException.
                String id = task.getResult().getUser().getUid();*/
                String id = Objects.requireNonNull(task.getResult().getUser()).getUid();

                mDatabase.child("Usuarios").child(id).setValue(map);

                //Correo de verificaci??n.
                user = mAuth.getCurrentUser();
                user.sendEmailVerification();
                startActivity(new Intent(Formulario.this, Login.class));
                finish();
            } else {
                Toast.makeText(Formulario.this, "No se pudo registrar el usuario.", Toast.LENGTH_LONG).show();
            }
        });
    }
}