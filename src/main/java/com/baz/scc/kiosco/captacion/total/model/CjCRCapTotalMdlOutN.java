package com.baz.scc.kiosco.captacion.total.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class CjCRCapTotalMdlOutN {
    @JsonProperty("NM")
    private String numeroCliente;
    @JsonProperty("NU")
    private String numeroCuenta;
    @JsonProperty("SA")
    private String total;
    @JsonProperty("SL")
    private String disponible;
    @JsonProperty("SR")
    private String retenido;
    @JsonProperty("SU")
    private String subproducto;
    @JsonProperty("NC")
    private String numCuenta;

    public String getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public String getRetenido() {
        return retenido;
    }

    public void setRetenido(String retenido) {
        this.retenido = retenido;
    }

    public String getSubproducto() {
        return subproducto;
    }

    public void setSubproducto(String subproducto) {
        this.subproducto = subproducto;
    }

    
}
