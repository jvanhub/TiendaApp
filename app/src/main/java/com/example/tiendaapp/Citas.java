package com.example.tiendaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Citas extends AppCompatActivity {
   //LinearLayout linearLayout = findViewById(R.id.linearCitas);
    //TextView mostradorCitas = new TextView(this);
    ArrayList<String> citas = new ArrayList<String>();
    private Button verCita;
    private Button modificarCita;
    private String fechaBBDD="";
    private String horaBBDD="";
    private String uId="";
    private String id;
    FirebaseUser mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);

        mAuth=FirebaseAuth.getInstance().getCurrentUser();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        id = mAuth.getUid();
        verCita = (Button) findViewById(R.id.buttonVerCitas);
        modificarCita = (Button) findViewById(R.id.buttonModificarCitas);

        verCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recogerCitas();
            }
        });
    }
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
                          /*  String fechaCompleta = fechaBBDD +" "+  horaBBDD;
                            citas.add(fechaCompleta);*/
                             if (uId.equals(id)){
                                 Log.d("valores",fechaBBDD);
                                 Log.d("valores",horaBBDD);
                                 Log.d("valores",uId);
                             }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Citas.this,"Error BBDD",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                //mostrarFechasHoras();
               // citas.clear();

               /* if (citas.size()==0){
                    Toast.makeText(Citas.this,"NO TIENES NINGUNA CITA",Toast.LENGTH_LONG).show();
                }else {
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Citas.this,"Error BBDD",Toast.LENGTH_LONG).show();
            }

        });
    }

    public void mostrarFechasHoras(){
        for(int i = 0; i <citas.size(); i++ )
        {
            Log.d("valores",citas.get(i));
            /*
            mostradorCitas.setText(citas.get(i));
            mostradorCitas.setPadding(10,10,10,10);
            linearLayout.addView(mostradorCitas);*/
            //Log.d("MIRAAAAAA", String.valueOf(mostradorCitas.getId()));
        }
    }
}