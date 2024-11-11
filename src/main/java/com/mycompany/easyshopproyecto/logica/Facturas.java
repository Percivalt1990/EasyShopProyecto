
package com.mycompany.easyshopproyecto.logica;


public class Facturas {
    
    private int id;
    private int idCliente;
    private int idUsuario;
    private String formaPago;
    private String fecha;
    private long total;
    private String pdfUrl;

    public Facturas() {
    }

    public Facturas(int id, int idCliente, int idUsuario, String formaPago, String fecha, long total, String pdfUrl) {
        this.id = id;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
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

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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
