package com.baz.scc.kiosco.model;

public class CjCRStatusDescription {
    
    private static CjCRStatus correcto = new CjCRStatus();
    private static CjCRStatus sinCu = new CjCRStatus();
    private static CjCRStatus error = new CjCRStatus();
    private static CjCRStatus errorBranch = new CjCRStatus();

    public CjCRStatusDescription() {
        correcto.setError(0);
        correcto.setDescripcion("Correcto");
        sinCu.setError(-1);
        sinCu.setDescripcion("CU incorrecto");
        error.setError(-2);
        error.setDescripcion("Error en el servicio");
        errorBranch.setError(-3);
        errorBranch.setDescripcion("Error el Branch de entrada no es correcto");
        
    }

    public CjCRStatus getCorrecto() {
        return correcto;
    }

    public CjCRStatus getSinCu() {
        return sinCu;
    }

    public CjCRStatus getError() {
        return error;
    }

    public CjCRStatus getErrorBranch() {
        return errorBranch;
    }
    
    
      
}
