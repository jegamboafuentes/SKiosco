package com.baz.scc.kiosco.logic.total;

import com.baz.scc.kiosco.cu.client.CjCRCuClient;
import com.baz.scc.kiosco.cu.dao.CjCRCuDao;
import com.baz.scc.kiosco.cu.model.CjCRCuMdlOut;
import com.baz.scc.kiosco.model.CjCRClienteUnico;
import com.baz.scc.kiosco.model.CjCRStatus;
import com.baz.scc.kiosco.model.CjCRStatusDescription;
import com.baz.scc.kiosco.model.CjCRUnidadNegocio;
import com.baz.scc.kiosco.model.total.CjCRTotalKioscoOut;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CjCRKioscoTotalLogic {
    private static final Logger LOG = Logger.getLogger(CjCRKioscoTotalLogic.class);
    
    @Autowired
    CjCRCuClient clienteCUUDNS;
       
    @Autowired
    CjCRCuDao clienteCUDao;  
    
    public CjCRTotalKioscoOut salidaTotal (CjCRClienteUnico clienteUnico, String branch){
        LOG.info("++++++++++++++++++++++++++++++++++++");
        LOG.info("*****Inicio del Servicio TOTAL*****");
        LOG.info("++++++++++++++++++++++++++++++++++++");
        CjCRTotalKioscoOut salidaTotal = new CjCRTotalKioscoOut();
        String [] clienteAlnova;
        String nombre;
        String appPaterno;
        String appMaterno;
        List<CjCRUnidadNegocio> unidadesNegocio = new ArrayList<CjCRUnidadNegocio>();
        CjCRCuMdlOut clienteCU = new CjCRCuMdlOut();
        CjCRStatus status = new CjCRStatus();
        CjCRStatusDescription statusDescription = new CjCRStatusDescription();
        Integer branchInt = 0;
        
        //Iniciamos siempre el branch en incorrecto para validar errores
        status = statusDescription.getError();
        
        
        //Valida que el Branch sea un numero
        try{
            branchInt = Integer.parseInt(branch);
        }catch(Exception e){
            status = statusDescription.getErrorBranch();
            LOG.error("CjCRKioscoTotalLogic (salidaTotal) el branch de entrada: "+branch+" no es una entrada correcta.");
        }
        
        //Asignacion de variables y validacion
        clienteCU = clienteCUUDNS.clienteUnicoUDNS(clienteUnico);
        clienteAlnova = clienteCU.getResult().getClientesAlnova();
        nombre = clienteCU.getResult().getNombre();
        appPaterno = clienteCU.getResult().getApellidoPaterno();
        appMaterno = clienteCU.getResult().getApellidoMaterno();
        
        //Validacion de cliente alnova
        if(clienteAlnova.length!=0){
            //unidadesNegocio = clienteCUDao.getUDNS(Integer.parseInt(clienteCU.getResult().getTipoCliente()),clienteUnico,clienteAlnova[0]);
        }else{
            //unidadesNegocio = clienteCUDao.getUDNS(Integer.parseInt(clienteCU.getResult().getTipoCliente()),clienteUnico,null);
        }
        
        if(clienteCU.getResult().getTipoCliente().equals("-1") || status==statusDescription.getErrorBranch()){
            if(status!=statusDescription.getErrorBranch()){
                status = statusDescription.getSinCu();
            }  
        }else{
            status = statusDescription.getCorrecto();
        }
        
        salidaTotal.setAppMaterno(appMaterno);
        salidaTotal.setAppPaterno(appPaterno);
        salidaTotal.setNombre(nombre);
        salidaTotal.setClientesAlnova(clienteAlnova);
        salidaTotal.setClienteUnico(clienteUnico);
        salidaTotal.setBranch(branchInt);
        salidaTotal.setStatus(status);
        return salidaTotal;
    }
    
    
}
