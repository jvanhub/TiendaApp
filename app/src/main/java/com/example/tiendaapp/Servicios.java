package com.example.tiendaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Servicios extends AppCompatActivity {

    private CardView cv1;
    private CardView cv2;
    private CardView cv3;
    private CardView cv4;
    private CardView cv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        cv1 = (CardView) findViewById(R.id.cv_1);
        cv2 = (CardView) findViewById(R.id.cv_2);
        cv3 = (CardView) findViewById(R.id.cv_3);
        cv4 = (CardView) findViewById(R.id.cv_4);
        cv5 = (CardView) findViewById(R.id.cv_5);

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Servicios.this,PedirCita.class));
            }
        });
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Servicios.this,PedirCita.class));
            }
        });
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Servicios.this,PedirCita.class));
            }
        });
        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Servicios.this,PedirCita.class));
            }
        });
        cv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Servicios.this,PedirCita.class));
            }
        });
    }
}