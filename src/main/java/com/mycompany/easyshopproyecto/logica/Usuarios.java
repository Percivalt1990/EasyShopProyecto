package com.mycompany.easyshopproyecto.logica;

public class Usuarios {
    private int id;
    private String nombre;
    private String tipoDocumento;
    private String numeroDocumento;
    private String email;
    private String telefono;
    private String password;
    private String confirmacion;
    private Boolean permisos; // Cambiado a Boolean para permitir null

    public Usuarios() {
    }

    public Usuarios(int id, String nombre, String tipoDocumento, String numeroDocumento, String email, String telefono, String password, String confirmacion, Boolean permisos) {
        this.id = id;
        this.nombre = nombre;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.confirmacion = confirmacion;
        this.permisos = permisos;
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

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }

    public Boolean getPermisos() {
        return permisos;
    }

    public void setPermisos(Boolean permisos) {
        this.permisos = permisos;
    }
}
