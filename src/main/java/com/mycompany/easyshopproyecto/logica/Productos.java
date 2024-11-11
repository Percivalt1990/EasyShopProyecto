package com.mycompany.easyshopproyecto.logica;

public class Productos {

    private int id;
    private String idCategoria; // Cambiado a idCategoria
    private String nombre;
    private int cantidad;
    private double precio;
    private String descripcion;
    private String idProveedor;

    public Productos() {
    }

    public Productos(int id, String idCategoria, String nombre, int cantidad, double precio, String descripcion, String idProveedor) {
        this.id = id;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descripcion = descripcion;
        this.idProveedor = idProveedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCategoria() { // Cambiado a idCategoria
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) { // Cambiado a idCategoria
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }
}
