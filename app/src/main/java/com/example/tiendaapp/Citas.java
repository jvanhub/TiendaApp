package com.example.tiendaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
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

public class Citas extends AppCompatActivity {
    private String fechaBBDD="";
    private String horaBBDD="";
    private String uId="";
    private String id;
    FirebaseUser mAuth;
    DatabaseReference mDatabase;
    List<ListElemnt> elements;
    List<ListElemnt> elements2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);

        mAuth=FirebaseAuth.getInstance().getCurrentUser();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        id = mAuth.getUid();
        Button verCita = (Button) findViewById(R.id.buttonVerCitas);
        Button verCitaActual = (Button) findViewById(R.id.buttonVerCitasActules);
        elements = new ArrayList<>();
        elements2 = new ArrayList<>();

        verCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recogerCitas();
            }
        });

        verCitaActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recogerCitasActualizadas();
            }
        });
    }

    //Se encarga de recger, comparar e insertar los datos junto con los elementos.
    public void recogerCitas(){
        mDatabase.child("Reservas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    mDatabase.child("Reservas").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            fechaBBDD=snapshot.child("fecha").getValue().toString();
                            horaBBDD=snapshot.child("hora").getValue().toString();
                            uId=snapshot.child("uId").getValue().toString();

                            if (uId.equals(id)){
                                insertElements();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Citas.this,"Error BBDD",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                elements.clear();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Citas.this,"Error BBDD",Toast.LENGTH_LONG).show();
            }
        });
    }

    //Método encargado de crear e introducir los datos en cada elemento.
    public void insertElements(){
        elements.add(new ListElemnt(fechaBBDD,horaBBDD));
        ListAdapter listAdapter =  new ListAdapter(elements,this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
    public void recogerCitasActualizadas(){
        mDatabase.child("Reservas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Calendar calendario = Calendar.getInstance();
                int dia =calendario.get(Calendar.DAY_OF_MONTH);
                int mes = (calendario.get(Calendar.MONTH)+1);
                int anyo = calendario.get(Calendar.YEAR);

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    mDatabase.child("Reservas").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            fechaBBDD=snapshot.child("fecha").getValue().toString();
                            horaBBDD=snapshot.child("hora").getValue().toString();
                            uId=snapshot.child("uId").getValue().toString();
                            String extractFecha[] = fechaBBDD.split("/");

                            if (uId.equals(id)){
                                if(Integer.parseInt(extractFecha[2]) - anyo < 0){
                                }else if(Integer.parseInt(extractFecha[1]) - mes< 0){
                                }else if(Integer.parseInt(extractFecha[0]) - dia< 0){
                                }else {
                                    insertElementsActual();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Citas.this,"Error BBDD",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                elements2.clear();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Citas.this,"Error BBDD",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void insertElementsActual(){
        elements2.add(new ListElemnt(fechaBBDD,horaBBDD));
        ListAdapter2 listAdapter =  new ListAdapter2(elements2,this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}