package com.example.hpuser.androidcrud;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hpuser.androidcrud.Modelo.Producto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase bd;
    private DatabaseReference ref;
    private EditText nombreEdit;
    private EditText descripcionEdit;
    private Button guardarBtn;
    private Button listarBtn;
    private Button actualizarBtn;
    private List productos;
    private String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.bd = FirebaseDatabase.getInstance();
        bd.setPersistenceEnabled(true);
        this.ref = bd.getReference();

        this.nombreEdit = (EditText) findViewById(R.id.nombreEdit);
        this.descripcionEdit = (EditText) findViewById(R.id.descripcionEdit);
        this.guardarBtn = (Button) findViewById(R.id.guardarBtn);
        this.listarBtn = (Button) findViewById(R.id.listarBtn);
        this.actualizarBtn = (Button) findViewById(R.id.actualizarBtn);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            key = intent.getStringExtra("producto_id");
        }
        //String key = "-LOF8n5kSbCmrW9chwrC";
        DatabaseReference pref = this.ref.child("productos").child(key);
        pref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Producto p = dataSnapshot.getValue(Producto.class);
                nombreEdit.setText(p.getNombre());
                descripcionEdit.setText(p.getDescripcion());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference productosRef = this.ref.child("productos");
        productosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productos = new ArrayList<Producto>();
                for(DataSnapshot productoDataSnapshot : dataSnapshot.getChildren()) {
                    Producto producto = productoDataSnapshot.getValue(Producto.class);
                    productos.add(producto);
                    System.out.println(producto.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void guardarProducto(View view) {

        //this.ref.child("productos").child(producto.getProducto_id()).setValue(producto);
        String producto_id = this.ref.child("productos").push().getKey();
        Producto producto = new Producto();
        producto.setNombre(this.nombreEdit.getText().toString());
        producto.setDescripcion(this.descripcionEdit.getText().toString());
        producto.setProducto_id(producto_id);
        this.ref.child("productos").child(producto.getProducto_id()).setValue(producto);

    }

    public void listar(View view){
        Intent intent = new Intent(this,
                                    ListaActivity.class);
        startActivity(intent);
    }
    public void leerProductos(View view) {
        productos.clear();
        DatabaseReference pref = this.ref.child("productos");
        pref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( DataSnapshot producto : dataSnapshot.getChildren()) {
                    Producto p = producto.getValue(Producto.class);
                    productos.add(p);
                    System.out.println(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void update(String producto_id, Producto p) {
        DatabaseReference pref= this.ref.child("productos")
                                        .child(producto_id);
        pref.setValue(p);
    }

    public void destroy(String producto_id) {
        DatabaseReference pref= this.ref.child("productos")
                .child(producto_id);
        pref.removeValue();
    }

    public void eliminar(View view) {
        if (key.length() > 0 ){
            this.destroy(key);
            Intent intent = new Intent(this, ListaActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this,
                            "producto no seleccionado",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void actualizar(View view) {
        Producto p = new Producto();
        p.setNombre(nombreEdit.getText().toString());
        p.setDescripcion(descripcionEdit.getText().toString());
        p.setProducto_id(key);
        this.update(key, p);
        Intent intent =
                new Intent(this, ListaActivity.class);
        startActivity(intent);
    }
}
