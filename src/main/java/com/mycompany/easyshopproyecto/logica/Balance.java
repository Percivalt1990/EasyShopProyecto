package com.mycompany.easyshopproyecto.logica;

public class Balance {
    private long totalVentas;
    private long totalCompras;
    private long balance;

    // Constructor con tres parámetros
    public Balance(long totalVentas, long totalCompras, long balance) {
        this.totalVentas = totalVentas;
        this.totalCompras = totalCompras;
        this.balance = balance;
    }

    // Getters y Setters
    public long getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(long totalVentas) {
        this.totalVentas = totalVentas;
    }

    public long getTotalCompras() {
        return totalCompras;
    }

    public void setTotalCompras(long totalCompras) {
        this.totalCompras = totalCompras;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
