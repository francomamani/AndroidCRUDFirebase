package com.example.hpuser.androidcrud;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hpuser.androidcrud.Adapter.ProductoAdapter;
import com.example.hpuser.androidcrud.Modelo.Producto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    private FirebaseDatabase bd;
    private DatabaseReference ref;

    private List productoLista = new ArrayList();
    private RecyclerView recyclerView;
    private ProductoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        this.bd = FirebaseDatabase.getInstance();
        this.ref = bd.getReference();

        /*recycler view listado*/
        recyclerView = findViewById(R.id.lista);
        /*items del listado de recyclerView*/
        mAdapter = new ProductoAdapter(productoLista);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        //initProductoData();
        changeProductoData();
    }
    /*
    * carga los productos en productoLista de la base de datos
    * */


    private void initProductoData() {
        //productoLista.clear();
        DatabaseReference pref = this.ref.child("productos");
        pref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( DataSnapshot producto : dataSnapshot.getChildren()) {
                    Producto p = producto.getValue(Producto.class);
                    productoLista.add(p);
                    System.out.println(p);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*escucha con datos en cambio*/
    private void changeProductoData() {
        DatabaseReference pref = this.ref.child("productos");
        ValueEventListener cambiosListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productoLista.clear();
                for( DataSnapshot producto : dataSnapshot.getChildren()) {
                    Producto p = producto.getValue(Producto.class);
                    productoLista.add(p);
                    System.out.println(p);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        pref.addValueEventListener(cambiosListener);

    }
}
