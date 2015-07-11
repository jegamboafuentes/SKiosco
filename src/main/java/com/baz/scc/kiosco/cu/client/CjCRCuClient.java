package com.baz.scc.kiosco.cu.client;

import com.baz.scc.kiosco.cu.model.CjCRCuMdlOut;
import com.baz.scc.kiosco.model.CjCRClienteUnico;
import com.baz.scc.kiosco.support.CjCRPAplication;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CjCRCuClient {
        
    private static final Logger LOG = Logger.getLogger(CjCRCuClient.class);
    
    @Autowired
    CjCRPAplication appProperties;
    
    public CjCRCuMdlOut clienteUnicoUDNS(CjCRClienteUnico cu) {
        CjCRCuMdlOut resultado = new CjCRCuMdlOut();
        try {
            LOG.info("Inicia el cliente para Cliente Unico (UDNS cliente)");
            Client client = Client.create();
            String service = appProperties.configuraciones().getClienteUnicoUDNS();
            String parametros = "?pais=" + cu.getPais() + "&canal=" + cu.getCanal() + "&sucursal=" + cu.getSucursal() + "&folio=" + cu.getFolio();
            String url = service + parametros;
            LOG.info("Servicio a consumir: " + url);
            WebResource webResource = client.resource(url);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
            
            if (response.getStatus() != 200) {
                throw new RuntimeException("Error: Error en el servicio, este respondio con un codigo: " + response.getStatus());
            }
            
            String json = response.getEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
            try {
                resultado = mapper.readValue(json, CjCRCuMdlOut.class);
            } catch (Exception mapeado) {
                LOG.error("Error en CjCRCuClient(clienteUnicoUDNS)-Error en la desparcializacion del JSON: " + mapeado.getMessage());
            }
        }catch(Exception ex){
            LOG.error("Error en CjCRCuClient(clienteUnicoUDNS)-Error en el cliente del servicio: Cliente Unico (UDNS cliente) "+ex.getMessage());
        }
        LOG.info("El servicio se consumio correctamente");
        
        //Validacion
        if(resultado.getResult().getTipoCliente().equals("")&&resultado.getResult().getClientesAlnova().length==0){
            LOG.error("Error en CjCRCuClient(clienteUnicoUDNS)-CU incorrecto, (No devuelve nada el cliente para Cliente Unico (UDNS cliente))");
            resultado.getResult().setTipoCliente("-1");
            String [] comodin = {"-1"};
            resultado.getResult().setClientesAlnova(comodin);
        }
        
        return resultado;
    }
    
}
