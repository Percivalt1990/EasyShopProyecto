package com.mycompany.easyshopproyecto.logica;


public class Proveedores {
    private int id;
    private String nombre;
    private String numeroTelefonico;
    private String direccion;
    private String email;

    public Proveedores() {
    }

    public Proveedores(int id, String nombre, String numeroTelefonico, String direccion, String email) {
        this.id = id;
        this.nombre = nombre;
        this.numeroTelefonico = numeroTelefonico;
        this.direccion = direccion;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
       
}
