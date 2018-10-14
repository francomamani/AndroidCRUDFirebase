package com.example.hpuser.androidcrud.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hpuser.androidcrud.ListaActivity;
import com.example.hpuser.androidcrud.MainActivity;
import com.example.hpuser.androidcrud.Modelo.Producto;
import com.example.hpuser.androidcrud.R;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoViewHolder> {


    private List<Producto> productoLista;
/*    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            System.out.println("click!");
        }
    };*/

    public ProductoAdapter(List<Producto> productoLista) {
        this.productoLista = productoLista;
    }

    private void accion(View view, int posicion) {
        Producto p = productoLista.get(posicion);
        view.getContext().startActivity(
                new Intent(view.getContext(),MainActivity.class)
                          .putExtra("producto_id", p.getProducto_id())
            );
        System.out.println("posicion: " + posicion);
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.producto_view,
                                            parent,
                                            false);
        return new ProductoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        holder.nombre.setText(productoLista.get(position).getNombre());
        holder.descripcion.setText(productoLista.get(position).getDescripcion());
        holder.editarBtn.setOnClickListener(view -> accion(view, holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return productoLista.size();
    }
}
