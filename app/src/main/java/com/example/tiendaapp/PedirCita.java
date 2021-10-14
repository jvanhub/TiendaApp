package com.example.tiendaapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PedirCita extends AppCompatActivity {
    private Button pedirFecha, pedirHora, confirmar;
    private EditText fecha,hora;
    private int dia,mes,anyo;

    private String fechadata="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_cita);

        pedirFecha = (Button) findViewById(R.id.button_fecha);
        pedirHora = (Button) findViewById(R.id.button_hora);
        confirmar = (Button) findViewById(R.id.button_confirmar);
        fecha = (EditText) findViewById(R.id.editTextTextFecha);

        fecha.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
               final Calendar calendario = Calendar.getInstance();
               dia =calendario.get(Calendar.DAY_OF_MONTH);
               mes = calendario.get(Calendar.MONTH);
               anyo = calendario.get(Calendar.YEAR);

                DatePickerDialog datePikerDialog = new DatePickerDialog( PedirCita.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }
                ,dia,mes,anyo);
                datePikerDialog.show();
            }
        });
        pedirFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
