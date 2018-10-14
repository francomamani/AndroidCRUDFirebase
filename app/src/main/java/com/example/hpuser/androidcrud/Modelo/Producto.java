package com.example.hpuser.androidcrud.Modelo;

public class Producto {
    public String producto_id;
    public String nombre;
    public String descripcion;

    public Producto() {}

    public Producto(String producto_id,
                    String nombre,
                    String descripcion) {
        this.producto_id = producto_id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getProducto_id() {
        return producto_id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setProducto_id(String producto_id) {
        this.producto_id = producto_id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "producto_id='" + producto_id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
