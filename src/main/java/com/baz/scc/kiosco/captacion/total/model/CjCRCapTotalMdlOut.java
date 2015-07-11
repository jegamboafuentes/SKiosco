package com.baz.scc.kiosco.captacion.total.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class CjCRCapTotalMdlOut {
    @JsonProperty("N")
    private CjCRCapTotalMdlOutN total[];
    @JsonProperty("NC")
    private String numeroCliente;

    public CjCRCapTotalMdlOutN[] getTotal() {
        return total;
    }

    public void setTotal(CjCRCapTotalMdlOutN[] total) {
        this.total = total;
    }

    public String getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }
     
}
