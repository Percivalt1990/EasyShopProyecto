package com.mycompany.easyshopproyecto.logica;

public class Facturas {
    
    private int id;
    private Clientes cliente;  // Relación con Cliente
    private Usuarios usuario;  // Relación con Usuario
    private String formaPago;
    private String fecha;
    private long total;
    private String pdfUrl;

    public Facturas() {
    }

    public Facturas(int id, Clientes cliente, Usuarios usuario, String formaPago, String fecha, long total, String pdfUrl) {
        this.id = id;
        this.cliente = cliente;
        this.usuario = usuario;
        this.formaPago = formaPago;
        this.fecha = fecha;
        this.total = total;
        this.pdfUrl = pdfUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
}
