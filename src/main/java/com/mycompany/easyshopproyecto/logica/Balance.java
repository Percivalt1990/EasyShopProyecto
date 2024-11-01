
package com.mycompany.easyshopproyecto.logica;


public class Balance {
    
    private long ingresos;
    private long egresos;

    public Balance() {
    }

    public Balance(long ingresos, long egresos) {
        this.ingresos = ingresos;
        this.egresos = egresos;
    }

    public long getIngresos() {
        return ingresos;
    }

    public void setIngresos(long ingresos) {
        this.ingresos = ingresos;
    }

    public long getEgresos() {
        return egresos;
    }

    public void setEgresos(long egresos) {
        this.egresos = egresos;
    }
    
    
    
    
    
    
}
