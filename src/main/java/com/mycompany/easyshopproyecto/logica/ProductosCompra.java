
package com.mycompany.easyshopproyecto.logica;

import java.io.Serializable;


public class ProductosCompra implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    private int id;
    private String proveedor;
    private String nombre;
    private double precio;
    private int cantidad;

    public ProductosCompra() {
    }

    public ProductosCompra(int id, String proveedor, String nombre, double precio, int cantidad) {
        this.id = id;
        this.proveedor = proveedor;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    // Get especial
     public double getSubtotal() {
            return this.precio * this.cantidad;
    }
    
}
