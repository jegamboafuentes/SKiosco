package com.baz.scc.kiosco.service;

import com.baz.scc.kiosco.logic.CjCRKioscoLogic;
import com.baz.scc.kiosco.logic.total.CjCRKioscoTotalLogic;
import com.baz.scc.kiosco.model.CjCRClienteUnico;
import com.baz.scc.kiosco.model.CjCRKioscoOut;
import com.baz.scc.kiosco.model.total.CjCRTotalKioscoOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CjCRKioscoService {
    
    @Autowired
    CjCRKioscoLogic logica;
    @Autowired 
    CjCRKioscoTotalLogic logicaTotal;
    
    public CjCRKioscoOut salidaService(CjCRClienteUnico cu, String branch){
        return logica.salida(cu,branch);
    }
    
    public CjCRTotalKioscoOut salidaServiceTotal(CjCRClienteUnico cu, String branch){
        return logicaTotal.salidaTotal(cu, branch);
    }
    
}
