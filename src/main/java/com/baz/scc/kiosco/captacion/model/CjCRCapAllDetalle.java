package com.baz.scc.kiosco.captacion.model;

public class CjCRCapAllDetalle {
    private String noCuenta;
    private String nombreCuenta;
    private String producto;
    private String subProducto;
    private CjCRCapAllDetalleMvtos [] movimientos;

    public String getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(String noCuenta) {
        this.noCuenta = noCuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }
    
    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(String subProducto) {
        this.subProducto = subProducto;
    }

    public CjCRCapAllDetalleMvtos[] getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(CjCRCapAllDetalleMvtos[] movimientos) {
        this.movimientos = movimientos;
    }
  
    
}
