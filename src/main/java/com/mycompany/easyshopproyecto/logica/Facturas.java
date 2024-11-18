package com.mycompany.easyshopproyecto.logica;

import java.time.LocalDateTime;

public class Facturas {
    private int id;
    private int clienteId;
    private int usuarioId;
    private String formaPago;
    private LocalDateTime fecha;
    private long total;
    private String pdfUrl;

    public Facturas() {
    }

    public Facturas(int id, int clienteId, int usuarioId, String formaPago, LocalDateTime fecha, long total, String pdfUrl) {
        this.id = id;
        this.clienteId = clienteId;
        this.usuarioId = usuarioId;
        this.formaPago = formaPago;
        this.fecha = fecha;
        this.total = total;
        this.pdfUrl = pdfUrl;
    }
    

    // Getters y Setters para cada campo

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
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
