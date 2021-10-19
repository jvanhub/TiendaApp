package com.example.tiendaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListElemnt>mData;
    private LayoutInflater mInflater;//Describe de que archivo proviene.
    private Context context;//De que clase estamo llamando este adaptador.
    public ListAdapter(List<ListElemnt> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context=context;
        this.mData = itemList;
    }


    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItems(List<ListElemnt> items){
        mData=items;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mostrar, fecha, hora, idUsuario;
        Button modificar, eliminar;

        ViewHolder(View itemView){
            super(itemView);
            mostrar = itemView.findViewById(R.id.textViewCitasCard);
            fecha = itemView.findViewById(R.id.textViewFechaCard);
            hora = itemView.findViewById(R.id.textViewHoraCard);
            modificar = itemView.findViewById(R.id.buttonModifi);
            eliminar = itemView.findViewById(R.id.buttonDelet);


        }

        void bindData(final ListElemnt item){
            fecha.setText(item.getFecha());
            hora.setText(item.getHora());

        }
    }
}
