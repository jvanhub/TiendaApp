package com.example.tiendaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Esta clase es la encargada de dar funcionalidad al activity_citas.
 */
public class Citas extends AppCompatActivity {
    private String servicioBBDD, fechaBBDD, horaBBDD, uId, id, idCita = "";
    ListAdapter2 listAdapter;
    FirebaseUser mAuth;
    DatabaseReference mDatabase;
    List<ListElemnt> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);

        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        id = mAuth.getUid();
        Button verCita = (Button) findViewById(R.id.buttonVerCitas);
        Button verCitaActual = (Button) findViewById(R.id.buttonVerCitasActules);
        Button volver = (Button) findViewById(R.id.buttonVolver2);
        elements = new ArrayList<>();

        /**
         * Evento encargado de acceder al método de "recogerCitas" que se encarga de consultar la
         * BBDD de todas las citas y mostrarlas.
         */
        verCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recogerCitas();
            }
        });

        /**
         * Evento encargado de acceder al método de "recogerCitasActualizadas" que se encarga de
         * consultar la BBDD de las citas creadas desde el día actual en adelante y mostrarlas.
         */
        verCitaActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recogerCitasActualizadas();
            }
        });

        /**
         * Evento que accede a la clase y activity de "Bienvenida" al pulsar el botón "Vovler".
         */
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Citas.this, Bienvenida.class));
            }
        });
    }

    /**
     * Método encargado de recoger, comparar e insertar los datos junto con los elementos.
     */
    public void recogerCitas() {
        mDatabase.child("Reservas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                elements.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        servicioBBDD = snapshot.child("servicio").getValue().toString();
                        fechaBBDD = snapshot.child("fecha").getValue().toString();
                        horaBBDD = snapshot.child("hora").getValue().toString();
                        uId = snapshot.child("uId").getValue().toString();
                        if (uId.equals(id)) {
                            insertElements();
                        }
                    } catch (NullPointerException n) {
                        Toast.makeText(Citas.this, "No hay citas pendientes", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Citas.this, "Error BBDD", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Método encargado de crear e introducir los datos en cada elemento.
     */
    public void insertElements() {
        elements.add(new ListElemnt(servicioBBDD, fechaBBDD, horaBBDD, null));
        ListAdapter listAdapter = new ListAdapter(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    /**
     * Método encargado de recoger y filtrar todas las citas desde el día de hoy incluido en adelante,
     * las fechas pasadas no.
     */
    public void recogerCitasActualizadas() {
        mDatabase.child("Reservas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Calendar calendario = Calendar.getInstance();
                int dia = calendario.get(Calendar.DAY_OF_MONTH);
                int mes = (calendario.get(Calendar.MONTH) + 1);
                int anyo = calendario.get(Calendar.YEAR);
                elements.clear();
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        fechaBBDD = snapshot.child("fecha").getValue().toString();
                        horaBBDD = snapshot.child("hora").getValue().toString();
                        uId = snapshot.child("uId").getValue().toString();
                        servicioBBDD = snapshot.child("servicio").getValue().toString();
                        String extractFecha[] = fechaBBDD.split("/");
                        idCita = snapshot.getKey();
                        if (uId.equals(id)) {
                            if (((Integer.parseInt(extractFecha[2]) - anyo) >= 0) && ((Integer.parseInt(extractFecha[1]) - mes) >= 0) && ((Integer.parseInt(extractFecha[0]) - dia) >= 0)) {
                                insertElementsActual();
                            }
                        }
                    }
                } catch (NullPointerException n) {
                    Toast.makeText(Citas.this, "No hay citas pendientes", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Citas.this, "Error BBDD", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Método encargado de crear e introducir los datos en cada elemento.
     */
    public void insertElementsActual() {
        elements.add(new ListElemnt(servicioBBDD, fechaBBDD, horaBBDD, idCita));
        listAdapter = new ListAdapter2(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}