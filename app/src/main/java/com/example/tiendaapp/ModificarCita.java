package com.example.tiendaapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

public class ModificarCita extends AppCompatActivity {

    private RadioButton hora9, hora10, hora11, hora12, hora15, hora16, hora17, hora18, hora19;
    private RadioGroup rg;
    private Button confirmar;
    private TextView fecha;
    private String fechaCompletaTv = "";
    private String horaCita = "";
    private String fechaBBDD = "";
    private String horaBBDD = "";
    private String id;
    private String idRefTablaButton;
    int radioId;
    private Button volver;
    RadioButton selectedbutton;
    Bundle bundle;
    ArrayList<RadioButton> arrayRadioButtons = new ArrayList<>();
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_cita);

        //Recibe datos (id del boton) desde ListaAdapter2.java -> bindData.
        bundle = getIntent().getExtras();
        idRefTablaButton = bundle.getString("boton");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        rg = (RadioGroup) findViewById(R.id.radioGroup_M);
        fecha = (TextView) findViewById(R.id.editTextTextFecham);

        confirmar = (Button) findViewById(R.id.button_confirmar_m);
        volver = (Button) findViewById(R.id.buttonVolver_m);
        hora9 = (RadioButton) findViewById(R.id.radiobutton9_00m);
        hora10 = (RadioButton) findViewById(R.id.radiobutton10_00m);
        hora11 = (RadioButton) findViewById(R.id.radiobutton11_00m);
        hora12 = (RadioButton) findViewById(R.id.radiobutton12_00m);
        hora15 = (RadioButton) findViewById(R.id.radiobutton15_00m);
        hora16 = (RadioButton) findViewById(R.id.radiobutton16_00m);
        hora17 = (RadioButton) findViewById(R.id.radiobutton17_00m);
        hora18 = (RadioButton) findViewById(R.id.radiobutton18_00m);
        hora19 = (RadioButton) findViewById(R.id.radiobutton19_00m);

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
                int dia = calendario.get(Calendar.DAY_OF_MONTH);
                int mes = calendario.get(Calendar.MONTH);
                int anyo = calendario.get(Calendar.YEAR);

                //Cuadro del calendario.
                DatePickerDialog datePikerDialog = new DatePickerDialog(ModificarCita.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fechaCompletaTv = dayOfMonth + "/" + (month + 1) + "/" + year;
                        fecha.setText(fechaCompletaTv);
                        extraerValores();
                    }
                }
                        , anyo, mes, dia);
                //Fecha mínima, para evitar citas de dias anteriores al actual.
                datePikerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePikerDialog.show();
            }
        });

        //Método para que cuando pulsamos sobre el button realice una acción.
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioId = rg.getCheckedRadioButtonId();
                selectedbutton = findViewById(radioId);

                if (fechaCompletaTv.equals("")) {
                    Toast.makeText(ModificarCita.this, "SELECCIONE FECHA", Toast.LENGTH_LONG).show();
                } else if (rg.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(ModificarCita.this, "SELECCIONE HORA", Toast.LENGTH_LONG).show();
                } else {
                    recogerDatosBBDD();
                }
            }
        });
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModificarCita.this, Citas.class));
            }
        });
    }

    //Método que realiza la consulta en la base de datos para compararlos datos.
    public void extraerValores() {
        mDatabase.child("Reservas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mDatabase.child("Reservas").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            fechaBBDD = snapshot.child("fecha").getValue().toString();
                            horaBBDD = snapshot.child("hora").getValue().toString();
                            comparador();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(ModificarCita.this, "Error BBDD", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                retornarEstado();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ModificarCita.this, "Error BBDD", Toast.LENGTH_LONG).show();
            }
        });
    }

    //Método que recorre el array y compara los datos en la base de datos para desactivar radioButtons.
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

    //Método que activa de nuevo los radioButtons cada vez que cambias de fecha.
    public void retornarEstado() {
        String textoRB;
        RadioButton selectedbutton;
        for (int i = 0; i < arrayRadioButtons.size(); i++) {
            selectedbutton = findViewById(arrayRadioButtons.get(i).getId());
            selectedbutton.setEnabled(true);
        }
        rg.clearCheck();
    }

    //Método que recoge el dato de la base de datos.
    public void recogerDatosBBDD() {
        mDatabase.child("Reservas").child(idRefTablaButton).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fechaBBDD = snapshot.child("fecha").getValue().toString();
                horaBBDD = snapshot.child("hora").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ModificarCita.this, "Error BBDD", Toast.LENGTH_LONG).show();
            }
        });
        modificador();
    }

    //Método que modifica los campos en la base de datos.
    public void modificador() {
        Calendar calendario = Calendar.getInstance();
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = (calendario.get(Calendar.MONTH) + 1);
        int anyo = calendario.get(Calendar.YEAR);
        id = mAuth.getUid();
        String extractFecha[] = fechaBBDD.split("/");
        if (Integer.parseInt(extractFecha[2]) - anyo < 0) {
        } else if (Integer.parseInt(extractFecha[1]) - mes < 0) {
        } else if (Integer.parseInt(extractFecha[0]) - dia < 0) {
        } else {
            horaCita = selectedbutton.getText().toString();
            Map<String, Object> map = new HashMap<>();
            map.put("fecha", fechaCompletaTv);
            map.put("hora", horaCita);
            map.put("uId", id);
            mDatabase.child("Reservas").child(idRefTablaButton).setValue(map);
            //mDatabase.child("Reservas").push().setValue(map);
            startActivity(new Intent(ModificarCita.this, Citas.class));
        }
    }
}

