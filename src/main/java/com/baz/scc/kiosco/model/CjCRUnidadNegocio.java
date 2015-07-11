package com.baz.scc.kiosco.model;

import java.util.List;

public class CjCRUnidadNegocio {
    private int id;
    private String nombre;
    private Object total;
    private List<Object> detalle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }    

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
        this.total = total;
    }

    public List<Object> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<Object> detalle) {
        this.detalle = detalle;
    }
    
    
  
}
