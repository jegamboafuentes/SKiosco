package com.baz.scc.kiosco.captacion.total.client;

import com.baz.scc.kiosco.captacion.total.model.*;
import com.baz.scc.kiosco.support.CjCRPAplication;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Transaction P055
@Service
public class CjCRCapTotalClient {
    
    private static final Logger LOG = Logger.getLogger(CjCRCapTotalClient.class);
    
    @Autowired
    CjCRPAplication appProperties;
    
    public CjCRCapTotalMdlOut p055Client (CjCRCapTotalMdlIn in){
        CjCRCapTotalMdlOut p055Out = new CjCRCapTotalMdlOut();
        try{
            LOG.info("Inicia el cliente para Captación (TX P055)");
            Client client = Client.create();
            String url = appProperties.configuraciones().getCaptacionTotal();
            String body;
            //Se deben de parametrizar todo esto
            body = "{\"U\":\""+in.getU()+"\",\"B\":\""+in.getB()+"\",\"I\":\""+in.getI()+"\",\"T\":\""+in.getT()+"\",\"NU\":\""+in.getNu()+"\",\"ME\":\""+in.getMe()+"\",\"FU\":\""+in.getFu()+"\"}";
//            body = "{\"U\":\"B111111\",\"B\":\"0112\",\"I\":\"0127\",\"T\":\"W080\",\"NU\":\"49734781\",\"ME\":\"0\",\"FU\":\"00\"}";
            LOG.info("Servicio a consumir: " + url);
            LOG.info("Cuerpo: "+body);
            WebResource webResource = client.resource(url);
            ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class,body);
            
            if(response.getStatus() != 200){ //Se debe de validar el caso que el status sea 206
                throw new RuntimeException("\"Error: Error en el servicio, este respondio con un codigo:" + response.getStatus());
            }
            
            String json = response.getEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
            try{
                p055Out = mapper.readValue(json,CjCRCapTotalMdlOut.class);
            }catch(Exception mapeo){
                LOG.error("Error en CjCRCapTotalClient(p055Client) -Error en la deserializacion del JSON: "+mapeo);
            }
        }catch(Exception client){
            LOG.error("Error en CjCRCapTotalClient(p055Client) -Error en el cliente para la TX P055: "+client);
        }
        LOG.info("Servicio P055 se consumio correctamente");
        return p055Out;
    }
    
}
