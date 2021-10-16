package com.example.tiendaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Citas extends AppCompatActivity {
    LinearLayout linearLayout = findViewById(R.id.linearCitas);
    ArrayList<String> citas = new ArrayList<>();

    private String fechaBBDD="";
    private String horaBBDD="";
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);

        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

    }
    /*public void recogerCitas(){
        mDatabase.child("Reservas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for ( final DataSnapshot snapshot: dataSnapshot.getChildren()){
                    mDatabase.child("Reservas").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            fechaBBDD=snapshot.child("fecha").getValue().toString();
                            horaBBDD=snapshot.child("hora").getValue().toString();
                            horaBBDD=snapshot.child("uId").getValue().toString();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Citas.this,"Error BBDD",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Citas.this,"Error BBDD",Toast.LENGTH_LONG).show();
            }
        });

    }*/

    /*public void crearLayaouts(){
        for(int i = 0; i <citas.size(); i++ )
        {
            TextView textView = new TextView(this);
            textView.setText(citas.get(i));
            //textView.setPadding(10,10,10,10);
            linearLayout.addView(textView);

            Log.d("MIRAAAAAA", String.valueOf(textView.getId()));

            if(textView.getId() ==0) {
                textView.setTextColor(Color.RED);
                textView.setPadding(10, 10, 10, 40);
            }else {
                textView.setTextColor(Color.BLUE);
                textView.setPadding(10, 10, 10, 10);
            }
        }
    }*/
}