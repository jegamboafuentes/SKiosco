package com.baz.scc.kiosco.model;

import java.util.List;

public class CjCRKioscoOut {
    
    private CjCRStatus status;
    private String nombre;
    private String appPaterno;
    private String appMaterno;
    private String [] clientesAlnova;
    private CjCRClienteUnico clienteUnico;
    private List<CjCRUnidadNegocio> unidadesNegocio;
    private Integer branch;
    

    public CjCRStatus getStatus() {
        return status;
    }

    public void setStatus(CjCRStatus status) {
        this.status = status;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAppPaterno() {
        return appPaterno;
    }

    public void setAppPaterno(String appPaterno) {
        this.appPaterno = appPaterno;
    }

    public String getAppMaterno() {
        return appMaterno;
    }

    public void setAppMaterno(String appMaterno) {
        this.appMaterno = appMaterno;
    }

    public String[] getClientesAlnova() {
        return clientesAlnova;
    }

    public void setClientesAlnova(String[] clientesAlnova) {
        this.clientesAlnova = clientesAlnova;
    }

    public CjCRClienteUnico getClienteUnico() {
        return clienteUnico;
    }

    public void setClienteUnico(CjCRClienteUnico clienteUnico) {
        this.clienteUnico = clienteUnico;
    }

    public List<CjCRUnidadNegocio> getUnidadesNegocio() {
        return unidadesNegocio;
    }

    public void setUnidadesNegocio(List<CjCRUnidadNegocio> unidadesNegocio) {
        this.unidadesNegocio = unidadesNegocio;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    } 
}
