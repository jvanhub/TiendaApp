package com.example.tiendaapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Esta clase relaciona la parte gráfica con los datos (Fecha y Hora) que trataremos.
 */
public class ListAdapter2 extends RecyclerView.Adapter<ListAdapter2.ViewHolder> {

    private List<ListElemnt>mData;//Esta lista tiene todos los datos de ListElement.
    private LayoutInflater mInflater;//Describe de que archivo proviene.
    public Context context;//Define de que clase estamos llamando este adaptador.

    /**
     * Método constructor.
     */
    public ListAdapter2(List<ListElemnt> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context=context;
        this.mData = itemList;
    }
    public ListAdapter2(){
    }

    /**
     * Método al que damos la referencia de como se va a ver cada "tarjeta"
     */
    @NonNull
    @Override
    public ListAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element_actual, null);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ListAdapter2.ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    /**
     * Método que devuelve el tamaño de elementos que hay en la lista.
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * Método que se encarga de redefinir los elementos de la lista.
     */
    public void setItems(List<ListElemnt> items){
        mData=items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mostrar, fecha, hora, servicio;
        Button btMod, btElim;
        DatabaseReference dbr;


        ViewHolder(View itemView){
            super(itemView);
            dbr = FirebaseDatabase.getInstance().getReference();
            mostrar = itemView.findViewById(R.id.textViewCitasCard2);
            fecha = itemView.findViewById(R.id.textViewFechaCard2);
            hora = itemView.findViewById(R.id.textViewHoraCard2);
            servicio = itemView.findViewById(R.id.textViewServicio2);
            btMod = itemView.findViewById(R.id.buttonModifi);
            btElim = itemView.findViewById(R.id.buttonDelet);
        }

        void bindData(final ListElemnt item){
            fecha.setText(item.getFecha());
            hora.setText(item.getHora());
            servicio.setText(item.getServicio());
            btElim.setContentDescription(item.getIdCita());

            btElim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbr.child("Reservas").child(btElim.getContentDescription().toString()).removeValue();
                    Toast.makeText(v.getContext(), "La cita se ha eliminado correctamente.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(),Citas.class);
                    v.getContext().startActivity(intent);
                }
            });

            btMod.setContentDescription(item.getIdCita());
            btMod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),ModificarCita.class);
                    intent.putExtra("boton",btMod.getContentDescription());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
