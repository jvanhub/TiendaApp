package com.example.tiendaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Esta clase relaciona la parte grafica con los datos (Fecha y Hora) que vamos ha tratar.
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListElemnt> mData;//Esta lista tiene todos los datos de ListElement.
    private final LayoutInflater mInflater;//Describe de que archivo proviene.
    public final Context context;//Define de que clase estamos llamando este adaptador.

    //Constructor.
    public ListAdapter(List<ListElemnt> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    //A continuación damos la referencia de como se va a ver cada "tarjeta"
    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ViewHolder(view);
    }

    //
    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    //Método que devuelve el tamaño de elementos que hay en la lista.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //Este metodo sirve para redefinir los elementos de la lista.
    public void setItems(List<ListElemnt> items) {
        mData = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mostrar, fecha, hora, servicio;

        ViewHolder(View itemView) {
            super(itemView);
            servicio = itemView.findViewById(R.id.textViewServicio);
            mostrar = itemView.findViewById(R.id.textViewCitasCard);
            fecha = itemView.findViewById(R.id.textViewFechaCard);
            hora = itemView.findViewById(R.id.textViewHoraCard);
        }

        void bindData(final ListElemnt item) {
            fecha.setText(item.getFecha());
            hora.setText(item.getHora());
            servicio.setText(item.getServicio());

        }
    }
}
