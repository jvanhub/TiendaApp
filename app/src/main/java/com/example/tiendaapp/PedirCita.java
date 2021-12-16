package com.example.tiendaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta clase es la encargada de dar funcionalidad al activity_pedir_cita.
 */
public class PedirCita extends AppCompatActivity {

    private RadioButton hora9, hora10, hora11, hora12, hora15, hora16, hora17, hora18, hora19;
    private RadioGroup rg;
    private Button confirmar, volver, fecha;
    private String fechaCompletaTv, horaCita, fechaBBDD, horaBBDD, servicio, textHint, nombreBBDD, nTelfBBDD, emailBBDD = "";
    private int contador;
    ArrayList<RadioButton> arrayRadioButtons = new ArrayList<>();
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_cita);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        fecha = (Button) findViewById(R.id.button_fecha);
        confirmar = (Button) findViewById(R.id.button_confirmar);
        volver = (Button) findViewById(R.id.buttonVolver1);
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

        //Llamada al método "calendarDate()" para seleccionar fecha.
        calendarDate();

        /**
         * Evento que accede al método "calendarDate()" para seleccionar fecha.
         */
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarDate();
            }
        });

        /**
         * Evento que accede al método para que cuando pulsamos sobre el botón "Confirmar" haga las
         * comprobaciones e inserte los datos en la BBDD.
         */
        confirmar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                servicio = getIntent().getStringExtra("servicio");
                int radioId = rg.getCheckedRadioButtonId();
                RadioButton selectedbutton = findViewById(radioId);

                try {
                    if (fechaCompletaTv.equals("")) {
                        Toast.makeText(PedirCita.this, "SELECCIONE FECHA", Toast.LENGTH_SHORT).show();
                    } else if (rg.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(PedirCita.this, "SELECCIONE HORA", Toast.LENGTH_SHORT).show();
                    } else if (contador >= 2) {
                        Toast.makeText(PedirCita.this, "Has alcanzado el límite máximo de citas", Toast.LENGTH_SHORT).show();
                    } else {
                        contador++;
                        mDatabase.child("Usuarios").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                nombreBBDD = snapshot.child("nombres").getValue().toString();
                                nTelfBBDD = snapshot.child("n_telefonos").getValue().toString();
                                emailBBDD = snapshot.child("emails").getValue().toString();
                                horaCita = selectedbutton.getText().toString();
                                String id = mAuth.getCurrentUser().getUid();
                                Map<String, Object> map = new HashMap<>();
                                map.put("servicio", servicio);
                                map.put("fecha", fechaCompletaTv);
                                map.put("hora", horaCita);
                                map.put("uId", id);
                                map.put("nombre", nombreBBDD);
                                map.put("telefono", nTelfBBDD);
                                map.put("email", emailBBDD);
                                mDatabase.child("Reservas").push().setValue(map);
                                Toast.makeText(PedirCita.this, "CITA CONFIRMADA", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PedirCita.this, Bienvenida.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }catch (NullPointerException np){
                    Toast.makeText(PedirCita.this, "SELECCIONE FECHA", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /**
         * Evento que accede a la clase y activity de "Bienvenida" al pulsar el botón "Vovler".
         */
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PedirCita.this, Bienvenida.class));
            }
        });
    }

    /**
     * Método para que cuando pulsamos muestre el calendario.
     */
    public void calendarDate() {
        Calendar calendario = Calendar.getInstance();
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = calendario.get(Calendar.MONTH);
        int anyo = calendario.get(Calendar.YEAR);

        //Cuadro del calendario.
        DatePickerDialog datePikerDialog = new DatePickerDialog(PedirCita.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fechaCompletaTv = dayOfMonth + "/" + (month + 1) + "/" + year;
                textHint = fecha.getText().toString() + " ";
                fecha.setText(textHint + fechaCompletaTv);
                extraerValores();
            }
        }
                , anyo, mes, dia);
        datePikerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FAB5B5")));

        //Fecha mínima, para evitar citas de días anteriores al actual.
        datePikerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePikerDialog.show();
    }

    /**
     * Método que realiza la consulta en la base de datos para recoger los datos.
     */
    public void extraerValores() {
        mDatabase.child("Reservas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mDatabase.child("Reservas").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                fechaBBDD = snapshot.child("fecha").getValue().toString();
                                horaBBDD = snapshot.child("hora").getValue().toString();
                                comparador();
                            } catch (NullPointerException n) {
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(PedirCita.this, "Error BBDD", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                retornarEstado();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PedirCita.this, "Error BBDD", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Método que recorre el array y compara los datos en la base de datos para desactivar radioButtons.
     */
    public void comparador() {
        String textoRB;
        RadioButton selectedbutton;

        for (int i = 0; i < arrayRadioButtons.size(); i++) {
            selectedbutton = findViewById(arrayRadioButtons.get(i).getId());
            textoRB = selectedbutton.getText().toString();

            if (fechaBBDD.equals(fechaCompletaTv) && horaBBDD.equals(textoRB)) {
                selectedbutton.setEnabled(false);
            }
        }
    }

    /**
     * Método que activa de nuevo los radioButtons cada vez que cambias de fecha.
     */
    public void retornarEstado() {
        RadioButton selectedbutton;
        for (int i = 0; i < arrayRadioButtons.size(); i++) {
            selectedbutton = findViewById(arrayRadioButtons.get(i).getId());
            selectedbutton.setEnabled(true);
        }
        rg.clearCheck();
    }
}