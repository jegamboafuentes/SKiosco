package com.baz.scc.kiosco.captacion.model;

import java.util.List;

public class CjCRCapAllTotal {
    private double total;
    private double disponible;
    private List<CjCRCapAllTotalCuentas> cuentas;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    } 

    public double getDisponible() {
        return disponible;
    }

    public void setDisponible(double disponible) {
        this.disponible = disponible;
    }

    public List<CjCRCapAllTotalCuentas> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CjCRCapAllTotalCuentas> cuentas) {
        this.cuentas = cuentas;
    }
    
    
}
