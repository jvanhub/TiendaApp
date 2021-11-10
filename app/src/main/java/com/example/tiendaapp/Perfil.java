package com.example.tiendaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Perfil extends AppCompatActivity {
    private Button btConfir, btVolver;
    private EditText etNombre, etAp1, etAp2, etTelf, etEail, etEmailConf;
    private TextView etInfo;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private String nombreBBDD, ap1BBDD, ap2BBDD, nTelfBBDD, emailBBDD;
    private String nombre, ap1, ap2, nTelf, email,emailConf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btConfir = (Button) findViewById(R.id.buttonModConfir);
        btVolver = (Button) findViewById(R.id.buttonModVolver);

        etNombre = (EditText) findViewById(R.id.editTextModNombre);
        etAp1 = (EditText) findViewById(R.id.editTextModApe1);
        etAp2 = (EditText) findViewById(R.id.editTextModApe2);
        etTelf = (EditText) findViewById(R.id.editTextModPhone);
        etEail = (EditText) findViewById(R.id.editTextTextMailMod);
        etEmailConf = (EditText) findViewById(R.id.editTextTextMailModCon);

        extraerDatosBBDD();

        btConfir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = etNombre.getText().toString();
                ap1 = etAp1.getText().toString();
                ap2 = etAp2.getText().toString();
                nTelf = etTelf.getText().toString();
                email = etEail.getText().toString();
                emailConf = etEmailConf.getText().toString();
                Matcher mather = pattern.matcher(email);

                if(nombre.isEmpty()||ap1.isEmpty()||ap2.isEmpty()||nTelf.isEmpty()||email.isEmpty()||emailConf.isEmpty()){
                    Toast.makeText(Perfil.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }else if(nTelf.length() <9 || nTelf.length() >9){
                    Toast.makeText(Perfil.this, "Número de telefono incorrecto", Toast.LENGTH_SHORT).show();
                }else if(mather.find()==false){
                    Toast.makeText(Perfil.this, "Email no valido", Toast.LENGTH_SHORT).show();
                }else if (!email.equals(emailConf)){
                    Toast.makeText(Perfil.this, "Los emails no coinciden", Toast.LENGTH_SHORT).show();
                }else{
                    modificarDatosBBDD();
                    Toast.makeText(Perfil.this, "Ok", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Perfil.this, Bienvenida.class));
            }
        });
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
                Toast.makeText(Perfil.this, "Error BBDD", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void modificarDatosBBDD(){
        Map <String, Object> map = new HashMap<>();
                map.put("nombres", etNombre.getText().toString());
                map.put("apellidos", etAp1.getText()).toString();
                map.put("apellidos2", etAp2.getText().toString());
                map.put("n_telefonos", etTelf.getText().toString());
                mDatabase.child("Usuarios").child(mAuth.getUid()).setValue(map);
                startActivity(new Intent(Perfil.this, Citas.class));
    }
}