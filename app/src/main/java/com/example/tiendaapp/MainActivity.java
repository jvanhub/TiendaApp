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

public class MainActivity extends AppCompatActivity {
    private EditText et_email;
    private EditText et_pass;
    private Button entrar;
    private Button registrar;

    private String email="";
    private String pass="";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        et_email= (EditText) findViewById(R.id.editTextEmail1);
        et_pass = (EditText) findViewById(R.id.editTextTextPass);
        entrar = (Button) findViewById(R.id.buttonEntrar);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vista) {
                email = et_email.getText().toString();
                pass = et_pass.getText().toString();

                if(email.isEmpty() || pass.isEmpty()){
                    Toast.makeText(MainActivity.this, "Completa los campos", Toast.LENGTH_LONG).show();
                }else{
                    login();
                }
            }
        });
    }
    public void login(){
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, Bienvenido.class));

                }else{
                    Toast.makeText(MainActivity.this,"No se pudo iniciar sesion, compruebe los datos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void registrar(View view){
        startActivity(new Intent(MainActivity.this,Formulario.class));
    }
}