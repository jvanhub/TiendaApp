package com.example.tiendaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Servicios extends AppCompatActivity {

    private CardView cv1,cv2,cv3,cv4,cv5;
    private TextView tv1,tv2,tv3,tv4,tv5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        cv1 = (CardView) findViewById(R.id.cv_1);
        cv2 = (CardView) findViewById(R.id.cv_2);
        cv3 = (CardView) findViewById(R.id.cv_3);
        cv4 = (CardView) findViewById(R.id.cv_4);
        cv5 = (CardView) findViewById(R.id.cv_5);

        tv1 = (TextView) findViewById(R.id.textViewTipo);
        tv2 = (TextView) findViewById(R.id.textViewTipo1);
        tv3 = (TextView) findViewById(R.id.textViewTipo2);
        tv4 = (TextView) findViewById(R.id.textViewTipo3);
        tv5 = (TextView) findViewById(R.id.textViewTipo4);

        Intent intent = new Intent(this,PedirCita.class);

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Servicios.this, tv1.getText(), Toast.LENGTH_SHORT).show();
                intent.putExtra("servicio", tv1.getText());
                startActivity(intent);
            }
        });

        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Servicios.this, tv2.getText(), Toast.LENGTH_SHORT).show();
                intent.putExtra("servicio", tv2.getText());
                startActivity(intent);
            }
        });

        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Servicios.this, tv3.getText(), Toast.LENGTH_SHORT).show();
                intent.putExtra("servicio", tv3.getText());
                startActivity(intent);
            }
        });

        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Servicios.this, tv4.getText(), Toast.LENGTH_SHORT).show();
                intent.putExtra("servicio", tv4.getText());
                startActivity(intent);
            }
        });

        cv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Servicios.this, tv5.getText(), Toast.LENGTH_SHORT).show();
                intent.putExtra("servicio", tv5.getText());
                startActivity(intent);
            }
        });
    }
}