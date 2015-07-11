package com.baz.scc.kiosco.rest;

import com.baz.scc.kiosco.model.CjCRClienteUnico;
import com.baz.scc.kiosco.model.CjCRKioscoOut;
import com.baz.scc.kiosco.model.total.CjCRTotalKioscoOut;
import com.baz.scc.kiosco.service.CjCRKioscoService;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CjCRKioscoRest {
    
    @Autowired
    CjCRKioscoService servicio;
    
    //main service
    //http://localhost:8080/Kiosco/kiosco
    @RequestMapping(value = "/kiosco/main/pais={pais}/canal={canal}/sucursal={sucursal}/folio={folio}/branch={branch}",
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON})
    
    @ResponseBody
    public CjCRKioscoOut salidaFinal(@PathVariable("pais") int pais, @PathVariable("canal") int canal, @PathVariable("sucursal") int sucursal,@PathVariable("folio") int folio, @PathVariable("branch") String branch){
        CjCRClienteUnico cu = new CjCRClienteUnico();
        cu.setPais(pais);
        cu.setCanal(canal);
        cu.setSucursal(sucursal);
        cu.setFolio(folio);
        return servicio.salidaService(cu,branch);
    }
    
    //total service
    //http://localhost:8080/Kiosco/kiosco/total/pais={pais}/canal={canal}/sucursal={sucursal}/folio={folio}
    @RequestMapping(value = "/kiosco/total/pais={pais}/canal={canal}/sucursal={sucursal}/folio={folio}/branch={branch}",
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON})
    
    @ResponseBody
    public CjCRTotalKioscoOut  salidaTotal(@PathVariable("pais") int pais, @PathVariable("canal") int canal, @PathVariable("sucursal") int sucursal,@PathVariable("folio") int folio, @PathVariable("branch") String branch){
        CjCRClienteUnico cu = new CjCRClienteUnico();
        cu.setPais(pais);
        cu.setCanal(canal);
        cu.setSucursal(sucursal);
        cu.setFolio(folio);
        return servicio.salidaServiceTotal(cu, branch);
    }
    
}
