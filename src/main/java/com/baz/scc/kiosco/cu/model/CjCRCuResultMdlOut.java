package com.baz.scc.kiosco.cu.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class CjCRCuResultMdlOut {
    @JsonProperty("Nombre")
    String nombre; 
    @JsonProperty("ApellidoMaterno")
    String apellidoMaterno;
    @JsonProperty("ApellidoPaterno")
    String apellidoPaterno;
    @JsonProperty("ClientesAlnova")
    String [] clientesAlnova;
    @JsonProperty("TipoCliente")
    String tipoCliente;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
    
    public String[] getClientesAlnova() {
        return clientesAlnova;
    }

    public void setClientesAlnova(String[] clientesAlnova) {
        this.clientesAlnova = clientesAlnova;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
    
    
    
}
