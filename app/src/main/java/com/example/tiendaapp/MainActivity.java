package com.example.tiendaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText numTelf;
    private EditText pass;
    private Button entrar;
    private Button registrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numTelf = findViewById(R.id.editTextNumber);
        pass = findViewById(R.id.editTextTextPass);

    }
    public void entrar(View view){

    }
    public void registrar(View view){
        startActivity(new Intent(MainActivity.this,Formulario.class));
    }
}