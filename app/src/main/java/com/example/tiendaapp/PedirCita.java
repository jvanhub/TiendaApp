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
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PedirCita extends AppCompatActivity {

    private RadioButton hora9, hora10, hora11, hora12 ,hora15 ,hora16, hora17, hora18, hora19;
    private RadioGroup rg;
    private Button confirmar;
    private TextView fecha;
    private String fechaCompletaTv="";
    private String horaCita="";
    private String fechaBBDD="";
    private String horaBBDD="";

    ArrayList <RadioButton> arrayRadioButtons = new ArrayList<>();
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

        //Añade al array los referencias del RadioButton.
        arrayRadioButtons.add(hora9);
        arrayRadioButtons.add(hora10);
        arrayRadioButtons.add(hora11);
        arrayRadioButtons.add(hora12);
        arrayRadioButtons.add(hora15);
        arrayRadioButtons.add(hora16);
        arrayRadioButtons.add(hora17);
        arrayRadioButtons.add(hora18);
        arrayRadioButtons.add(hora19);

        //Método para que cuando pulsamos sobre el TextView realice una acción.
        fecha.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

               Calendar calendario = Calendar.getInstance();
               int dia =calendario.get(Calendar.DAY_OF_MONTH);
               int mes = calendario.get(Calendar.MONTH);
               int anyo = calendario.get(Calendar.YEAR);

               //Cuadro del calendario.
                DatePickerDialog datePikerDialog = new DatePickerDialog( PedirCita.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fechaCompletaTv =dayOfMonth + "/" + (month + 1) + "/" + year;
                        fecha.setText(fechaCompletaTv);
                        extraerValores();
                    }
                }
                ,anyo,mes,dia);

                //Fecha mínima, para evitar citas de dias anteriores al actual.
                datePikerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePikerDialog.show();
            }
        });

        //Método para que cuando pulsamos sobre el button realice una acción.
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = rg.getCheckedRadioButtonId();
                RadioButton selectedbutton = findViewById(radioId);

                if (fechaCompletaTv.equals("")){
                    Toast.makeText(PedirCita.this,"SELECCIONE FECHA",Toast.LENGTH_LONG).show();
                }else if (rg.getCheckedRadioButtonId()==-1){
                    Toast.makeText(PedirCita.this,"SELECCIONE HORA",Toast.LENGTH_LONG).show();
                }else{
                    horaCita=selectedbutton.getText().toString();
                    String id = mAuth.getCurrentUser().getUid();

                    Map<String, Object> map = new HashMap<>();
                    map.put("fecha",fechaCompletaTv);
                    map.put("hora",horaCita);
                    map.put("uId",id);
                    //mDatabase.child("Reservas").child(id).setValue(map);
                    mDatabase.child("Reservas").push().setValue(map);
                    startActivity(new Intent(PedirCita.this, Bienvenida.class));
                    Toast.makeText(PedirCita.this,"CITA CONFIRMADA",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Método que realiza la consulta en la base de datos para compararlos datos.
    public void extraerValores(){
        mDatabase.child("Reservas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for ( final DataSnapshot snapshot: dataSnapshot.getChildren()){
                    mDatabase.child("Reservas").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            fechaBBDD=snapshot.child("fecha").getValue().toString();
                            horaBBDD=snapshot.child("hora").getValue().toString();
                            comparador();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(PedirCita.this,"Error BBDD",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                retornarEstado();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PedirCita.this,"Error BBDD",Toast.LENGTH_LONG).show();
            }
        });
    }

    //Método que recorre el array y compara los datos en la base de datos para desactivar radioButtons.
    public void comparador(){
        String textoRB;
        RadioButton selectedbutton;

        for (int i=0;i<arrayRadioButtons.size();i++){
            selectedbutton = findViewById(arrayRadioButtons.get(i).getId());
            textoRB = selectedbutton.getText().toString();

            if (fechaBBDD.equals(fechaCompletaTv) && horaBBDD.equals(textoRB) ){
                selectedbutton.setEnabled(false);
            }
        }
    }

    //Método que activa de nuevo los radioButtons cada vez que cambias de fecha.
    public void retornarEstado(){
        String textoRB;
        RadioButton selectedbutton;
        for (int i=0;i<arrayRadioButtons.size();i++){
            selectedbutton = findViewById(arrayRadioButtons.get(i).getId());
            selectedbutton.setEnabled(true);
        }
        rg.clearCheck();
    }
}