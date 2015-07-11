package com.baz.scc.kiosco.captacion.model;

import java.util.List;

public class CjCRCapAll {
    private CjCRCapAllTotal total;
    private List<CjCRCapAllDetalle> detalle;

    public CjCRCapAllTotal getTotal() {
        return total;
    }

    public void setTotal(CjCRCapAllTotal total) {
        this.total = total;
    }

    public List<CjCRCapAllDetalle> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<CjCRCapAllDetalle> detalle) {
        this.detalle = detalle;
    }
    
    
 
}
