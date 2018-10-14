package com.example.hpuser.androidcrud.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hpuser.androidcrud.R;

class ProductoViewHolder extends RecyclerView.ViewHolder{
    public TextView nombre;
    public TextView descripcion;
    public Button editarBtn;

    public ProductoViewHolder(View itemView) {
        super(itemView);
        this.nombre = (TextView)itemView.findViewById(R.id.nombre);
        this.descripcion = (TextView)itemView.findViewById(R.id.descripcion);
        this.editarBtn = (Button) itemView.findFocus(R.id.editarBtn);
    }
}
