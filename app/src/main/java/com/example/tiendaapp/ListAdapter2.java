package com.example.tiendaapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Esta clase relaciona la parte grafica con los datos (Fecha y Hora) que vamos ha tratar.
public class ListAdapter2 extends RecyclerView.Adapter<ListAdapter2.ViewHolder> {

    private List<ListElemnt>mData;//Esta lista tiene todos los datos de ListElement.
    private LayoutInflater mInflater;//Describe de que archivo proviene.
    public Context context;//Define de que clase estamos llamando este adaptador.

    //Constructor.
    public ListAdapter2(List<ListElemnt> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context=context;
        this.mData = itemList;
    }
    public ListAdapter2(){

    }


    //A continuación damos la referencia de como se va a ver cada "tarjeta"
    @NonNull
    @Override
    public ListAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element_actual, null);
        return new ViewHolder(view);
    }

    //
    @Override
    public void onBindViewHolder(@NonNull ListAdapter2.ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    //Método que devuelve el tamaño de elementos que hay en la lista.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //Este metodo sirve para redefinir los elementos de la lista.
    public void setItems(List<ListElemnt> items){
        mData=items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mostrar, fecha, hora;
        Button btMod, btElim;

        ViewHolder(View itemView){
            super(itemView);
            mostrar = itemView.findViewById(R.id.textViewCitasCard2);
            fecha = itemView.findViewById(R.id.textViewFechaCard2);
            hora = itemView.findViewById(R.id.textViewHoraCard2);
            btMod = itemView.findViewById(R.id.buttonModifi);
            btElim = itemView.findViewById(R.id.buttonDelet);
        }

        void bindData(final ListElemnt item){
            fecha.setText(item.getFecha());
            hora.setText(item.getHora());
            btMod.setContentDescription(item.getIdCita());
            btMod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),ModificarCita.class);
                    Toast.makeText(v.getContext(), btMod.getContentDescription(), Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(intent);
                }
            });
        }

    }
}
