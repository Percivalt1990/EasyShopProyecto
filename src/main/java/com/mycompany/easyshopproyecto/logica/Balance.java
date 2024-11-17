package com.mycompany.easyshopproyecto.logica;



public class Balance {
    private double totalIngresos;
    private double totalEgresos;
    private double balance;

    // Constructor por defecto, inicializando valores a 0.0
    public Balance() {
        this.totalIngresos = 0.0;
        this.totalEgresos = 0.0;
        this.balance = 0.0;
    }

    // Constructor con parámetros
    public Balance(double totalIngresos, double totalEgresos, double balance) {
        this.totalIngresos = totalIngresos;
        this.totalEgresos = totalEgresos;
        this.balance = balance;
    }

    // Getter y Setter para totalIngresos
    public double getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    // Getter y Setter para totalEgresos
    public double getTotalEgresos() {
        return totalEgresos;
    }

    public void setTotalEgresos(double totalEgresos) {
        this.totalEgresos = totalEgresos;
    }

    // Getter y Setter para balance
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
