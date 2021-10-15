package com.example.tiendaapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PedirCita extends AppCompatActivity {
    private RadioButton hora9, hora10, hora11, hora12 ,hora15 ,hora16, hora17, hora18, hora19;
    private RadioGroup rg;
    private Button confirmar;

    private TextView fecha,hora;
    private String fechaCompletaTv="";
    private String horaCita="";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_cita);

        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        rg = (RadioGroup) findViewById(R.id.radioGroup);
        fecha = (TextView) findViewById(R.id.editTextTextFecha);
        confirmar = (Button) findViewById(R.id.button_confirmar);
        hora9 = (RadioButton) findViewById(R.id.radiobutton9_00);
        hora10 = (RadioButton) findViewById(R.id.radiobutton10_00);
        hora11 = (RadioButton) findViewById(R.id.radiobutton11_00);
        hora12 = (RadioButton) findViewById(R.id.radiobutton12_00);
        hora15 = (RadioButton) findViewById(R.id.radiobutton15_00);
        hora16 = (RadioButton) findViewById(R.id.radiobutton16_00);
        hora17 = (RadioButton) findViewById(R.id.radiobutton17_00);
        hora18 = (RadioButton) findViewById(R.id.radiobutton18_00);
        hora19 = (RadioButton) findViewById(R.id.radiobutton19_00);

        fecha.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
               Calendar calendario = Calendar.getInstance();
               int dia =calendario.get(Calendar.DAY_OF_MONTH);
               int mes = calendario.get(Calendar.MONTH);
               int anyo = calendario.get(Calendar.YEAR);

                DatePickerDialog datePikerDialog = new DatePickerDialog( PedirCita.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //String fechaCita=year + "/" + (month + 1) + "/" + dayOfMonth;
                        fechaCompletaTv =dayOfMonth + "/" + (month + 1) + "/" + year;
                        fecha.setText(fechaCompletaTv);
                    }
                }
                ,anyo,mes,dia);
                datePikerDialog.show();
            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioId = rg.getCheckedRadioButtonId();
                RadioButton selectedbutton = findViewById(radioId);
                String horaCita=selectedbutton.getText().toString();
                String id = mAuth.getCurrentUser().getUid();

                Toast.makeText(PedirCita.this,String.valueOf(id),Toast.LENGTH_LONG).show();
                Map<String, Object> map = new HashMap<>();
                map.put("fecha",fechaCompletaTv);
                map.put("hora",horaCita);
                map.put("uId",id);

                mDatabase.child("Reservas").push().setValue(map);
                extraerValores();
            }

            //
            public void extraerValores(){
                mDatabase.child("Reservas").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for ( final DataSnapshot snapshot: dataSnapshot.getChildren()){
                            mDatabase.child("Reservas").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String fecha=snapshot.child("fecha").getValue().toString();
                                    String hora=snapshot.child("hora").getValue().toString();
                                    Log.d("horaImaginaria",hora);
                                    /* if (fecha.equals(fechaCompletaTv) && hora.equals(horaCita) ){
                                    }*/
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PedirCita.this,"Error BBDD",Toast.LENGTH_LONG).show();
                    }
                });
            }

        });
    }
}