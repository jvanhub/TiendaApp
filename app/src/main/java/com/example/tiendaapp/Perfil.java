package com.example.tiendaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {
    private Button btConfir, btVolver;
    private EditText etNombre, etAp1, etAp2, etTelf, etEail, etEmailConf;
    private TextView etInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        btConfir = (Button) findViewById(R.id.buttonModConfir);
        btVolver = (Button) findViewById(R.id.buttonModVolver);

        etNombre = (EditText) findViewById(R.id.editTextModNombre);
        etAp1 = (EditText) findViewById(R.id.editTextApe1);
        etAp2 = (EditText) findViewById(R.id.editTextModApe2);
        etTelf = (EditText) findViewById(R.id.editTextModPhone);
        etEail = (EditText) findViewById(R.id.editTextTextMailMod);
        etEmailConf = (EditText) findViewById(R.id.editTextTextMailModCon);
    }

}