package com.baz.scc.kiosco.captacion.detalle.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class CjCRCapDetalleMdlOutN1 {
    @JsonProperty("AM")
    private String am;
    @JsonProperty("DA")
    private String da;
    @JsonProperty("OB")
    private String ob;

    public String getAm() {
        return am;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public String getDa() {
        return da;
    }

    public void setDa(String da) {
        this.da = da;
    }

    public String getOb() {
        return ob;
    }

    public void setOb(String ob) {
        this.ob = ob;
    }
    
    
    
}
