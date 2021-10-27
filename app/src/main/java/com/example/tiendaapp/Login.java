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
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    //Variabes de los activitys
    private EditText et_email;
    private EditText et_pass;

    //Variables de recogida de datos.
    private String email = "";
    private String pass = "";

    //Instancia de la clase FirebaseAuth.
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        et_email = (EditText) findViewById(R.id.editTextEmail1);
        et_pass = (EditText) findViewById(R.id.editTextTextPass);
        Button entrar = (Button) findViewById(R.id.buttonEntrar);
        Button registro = (Button) findViewById(R.id.buttonRegistro);

        //Método OnClick del button Entrar.
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vista) {
                email = et_email.getText().toString();
                pass = et_pass.getText().toString();
                if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Login.this, "Completa los campos", Toast.LENGTH_LONG).show();
                } else {
                    login();
                }
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Formulario.class));
            }
        });
    }
    public void login() {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mUser = mAuth.getCurrentUser();
                if (task.isSuccessful()) {
                    if(mUser.isEmailVerified()){
                        startActivity(new Intent(Login.this, Bienvenida.class));
                    }else {
                        Toast.makeText(Login.this, "Verifique su email, en el email de verificación que se envió al registrarse.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Login.this, "No se pudo iniciar sesion, compruebe los datos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*
    * && mUser.isEmailVerified()
    *Toast.makeText(Login.this, "Verifique su email, en el email de verificación que se envió al registrarse.", Toast.LENGTH_LONG).show();

     * */
}
