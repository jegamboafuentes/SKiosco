package com.baz.scc.kiosco.support;

import com.baz.scc.kiosco.model.CjCRPAppConfigMdl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CjCRPAplication {
    @Autowired
    CjCRPAppConfig appConfig;
    
    public CjCRPAppConfigMdl configuraciones (){
        CjCRPAppConfigMdl modelo = new CjCRPAppConfigMdl();
        String cuUnds;
        String ppTotal;
        String ppDetalle;
        String capTotal;
        String capDetalle;
        Boolean produccion;
        produccion = appConfig.isProduction();
        
        if(produccion){
            cuUnds = appConfig.getClienteUnicoUDNSProduccion();
            ppTotal = appConfig.getPrestaPrendaTotalProduccion();
            ppDetalle = appConfig.getPrestaPrendaDetalleProduccion();
            capTotal = appConfig.getCaptacionTotalProduccion();
            capDetalle = appConfig.getCaptacionDetalleProduccion();
        }else{
            cuUnds = appConfig.getClienteUnicoUDNSDesarrollo();
            ppTotal = appConfig.getPrestaPrendaTotalDesarrollo();
            ppDetalle = appConfig.getPrestaPrendaDetalleDesarrollo();
            capTotal = appConfig.getCaptacionTotalDesarrollo();
            capDetalle = appConfig.getCaptacionDetalleDesarrollo();
        }
        modelo.setClienteUnicoUDNS(cuUnds);
        modelo.setPrestaPrendaTotal(ppTotal);
        modelo.setPrestaPrendaDetalle(ppDetalle);
        modelo.setCaptacionTotal(capTotal);
        modelo.setCaptacionDetalle(capDetalle);
        return modelo;
    }
    
}
