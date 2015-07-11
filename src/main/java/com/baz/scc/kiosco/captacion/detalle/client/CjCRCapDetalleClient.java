package com.baz.scc.kiosco.captacion.detalle.client;

import com.baz.scc.kiosco.captacion.detalle.model.*;
import com.baz.scc.kiosco.captacion.total.client.CjCRCapTotalClient;
import com.baz.scc.kiosco.support.CjCRPAplication;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Transaccion B44S
@Service
public class CjCRCapDetalleClient {

    private static final Logger LOG = Logger.getLogger(CjCRCapTotalClient.class);

    @Autowired
    CjCRPAplication appProperties;
    
    public CjCRCapDetalleMdlOut b44SClient (CjCRCapDetalleMdlIn in){
        CjCRCapDetalleMdlOut b44SOut = new CjCRCapDetalleMdlOut();
        try{
            LOG.info("Inicia el cliente para TX B44S AC: "+in.getAc());
            Client client = new Client();
            //String url = "http://10.63.32.113/B44S/9012";
            String url = appProperties.configuraciones().getCaptacionDetalle();
            String body;
            body = "{\"U\":\""+in.getU()+"\",\"B\":\""+in.getB()+"\",\"I\":\""+in.getI()+"\",\"T\":\""+in.getT()+"\",\"AC\":\""+in.getAc()+"\",\"DA\":\""+in.getDa()+"\",\"DT\":\""+in.getDt()+"\",\"OP\":\""+in.getOp()+"\",\"MO\":\""+in.getMo()+"\",\"PA\":\""+in.getPa()+"\",\"FI\":\""+in.getFi()+"\",\"TR\":\""+in.getTr()+"\",\"IN\":\""+in.getIn()+"\",\"TO\":\""+in.getTo()+"\"}";
            //body = "{\"U\":\"B111111\",\"B\":\"0673\",\"I\":\"0127\",\"T\":\"1FXG\",\"AC\":\"01270172400115289118\",\"DA\":\"\",\"DT\":\"\",\"OP\":\"\",\"MO\":\"\",\"PA\":\"\",\"FI\":\"\",\"TR\":\"\",\"IN\":\"\",\"TO\":\"000\"}";
            LOG.info("Servicio a consumir: " + url);
            LOG.info("Cuerpo: "+body);
            WebResource webResource = client.resource(url);
            ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class,body);
            if(response.getStatus() != 200){
                throw new RuntimeException("\"Error: Error en el servicio B44S, este respondio con un codigo: " + response.getStatus());
            }
            
            String json = response.getEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
            try{
                b44SOut = mapper.readValue(json, CjCRCapDetalleMdlOut.class);
            }catch(Exception desearilzada){
                LOG.error("Error en CjCRCapTDetalleClient(B44SClient) -Error en la deserializacion del JSON: "+desearilzada);
            }
        }catch(Exception client){
            LOG.error("Error en CjCRCapDetalleClient(B44SClient) -Error en el cliente para la TX B44S: "+client);
        }
        LOG.info("Servicio B44S se consumio correctamente");
        return b44SOut;
    }
    
}
