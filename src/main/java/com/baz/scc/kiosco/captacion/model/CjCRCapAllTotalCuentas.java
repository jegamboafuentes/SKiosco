package com.baz.scc.kiosco.captacion.model;

public class CjCRCapAllTotalCuentas {
    private String noCuenta;
    private String nombreCuenta;
    private String producto;
    private String subProducto;
    private Double totalCuenta;
    private Double disponibleCuenta;
    private Double retenidoCuenta;
    

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

    public Double getTotalCuenta() {
        return totalCuenta;
    }

    public void setTotalCuenta(Double totalCuenta) {
        this.totalCuenta = totalCuenta;
    }

    public Double getDisponibleCuenta() {
        return disponibleCuenta;
    }

    public void setDisponibleCuenta(Double disponibleCuenta) {
        this.disponibleCuenta = disponibleCuenta;
    }

    public Double getRetenidoCuenta() {
        return retenidoCuenta;
    }

    public void setRetenidoCuenta(Double retenidoCuenta) {
        this.retenidoCuenta = retenidoCuenta;
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
     
    
}
