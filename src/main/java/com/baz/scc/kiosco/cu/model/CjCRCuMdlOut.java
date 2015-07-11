package com.baz.scc.kiosco.cu.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class CjCRCuMdlOut {
    @JsonProperty("porClienteUnicoConNombreResult")
    CjCRCuResultMdlOut result;

    public CjCRCuResultMdlOut getResult() {
        return result;
    }

    public void setResult(CjCRCuResultMdlOut result) {
        this.result = result;
    }   
}
