package com.baz.scc.kiosco.prespren.client;

import com.baz.scc.kiosco.cu.dao.CjCRCuDao;
import com.baz.scc.kiosco.model.CjCRClienteUnico;
import com.baz.scc.kiosco.prespren.model.CjCRPPCredencialesMdl;
import com.baz.scc.kiosco.prespren.model.CjCRPPSalidaDetalleMdl;
import com.baz.scc.kiosco.support.CjCRPAplication;
import java.util.ArrayList;
import java.util.List;
import javax.xml.soap.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CjCRPPClientDetalle {
    
    @Autowired
    CjCRPAplication appProperties;
    
    private static final Logger LOG = Logger.getLogger(CjCRCuDao.class);
    
    private static final CjCRPPCredencialesMdl credentials = new CjCRPPCredencialesMdl();

    public CjCRPPClientDetalle() {
        credentials.setUsuario("usrBackOffice");
        credentials.setContrasena("kiosco");
    }
    
    public List<Object> soapConectionDetalle (CjCRClienteUnico cu) throws Exception{
        LOG.info("Inicia el cliente para Presta Prenda (DETALLE)");
        List<Object> salidaDetalleList = new ArrayList<Object>();
        
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
//        String url = appProperties.configuraciones().getPrestaPrendaDetalle();
        String url = "http://10.51.217.245:8081/SCP/Servicios/BackOfficeSwnKioscoS90.asmx";
        SOAPMessage soapResponse = soapConnection.call(consultaDetallePP(cu), url);
        LOG.info("Servicio SOAP a consumir: "+url);
        int cantidadBoletas = soapResponse.getSOAPBody().getElementsByTagName("z:row").getLength();
        for(int i = 0; i<cantidadBoletas; i++){
            CjCRPPSalidaDetalleMdl salidaDetalle = new CjCRPPSalidaDetalleMdl();
            salidaDetalle.setBoleta(soapResponse.getSOAPBody().getElementsByTagName("z:row").item(i).getAttributes().item(0).getNodeValue());
            salidaDetalle.setEstatus(soapResponse.getSOAPBody().getElementsByTagName("z:row").item(i).getAttributes().item(1).getNodeValue());
            salidaDetalle.setIdError(soapResponse.getSOAPBody().getElementsByTagName("z:row").item(i).getAttributes().item(2).getNodeValue());
            salidaDetalle.setImporteAbono(soapResponse.getSOAPBody().getElementsByTagName("z:row").item(i).getAttributes().item(3).getNodeValue());
            salidaDetalle.setImporteLiquidacion(soapResponse.getSOAPBody().getElementsByTagName("z:row").item(i).getAttributes().item(4).getNodeValue());
            salidaDetalle.setImporteRefrendo(soapResponse.getSOAPBody().getElementsByTagName("z:row").item(i).getAttributes().item(5).getNodeValue());
            salidaDetalle.setImporteVenta(soapResponse.getSOAPBody().getElementsByTagName("z:row").item(i).getAttributes().item(6).getNodeValue());
            salidaDetalle.setRespuestaError(soapResponse.getSOAPBody().getElementsByTagName("z:row").item(i).getAttributes().item(7).getNodeValue());
            salidaDetalleList.add(salidaDetalle);           
        }
        soapConnection.close();
        LOG.info("El servicio se consumio correctamente");
        return salidaDetalleList;
    }
    
    private static SOAPMessage consultaDetallePP(CjCRClienteUnico cu)throws Exception{
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        soapMessage.getSOAPHeader().setPrefix("soap");
        soapMessage.getSOAPBody().setPrefix("soap");
        SOAPPart soapPart = soapMessage.getSOAPPart();
        soapPart.getEnvelope().setPrefix("soap");
        
        String sufijo = "tem";
        String serverURI = "http://www.w3.org/2003/05/soap-envelope";
        String serverURI2 = "http://tempuri.org/";
        
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("soap", serverURI);
        envelope.addNamespaceDeclaration(sufijo, serverURI2);
        
        SOAPHeader header = envelope.getHeader();
        SOAPElement entAutentificarKiosco = header.addChildElement("EntAutentificarKiosco",sufijo);
        SOAPElement contrasenia = entAutentificarKiosco.addChildElement("Contrasenia",sufijo);
        contrasenia.addTextNode(credentials.getContrasena());
        SOAPElement usuario = entAutentificarKiosco.addChildElement("Usuario",sufijo);
        usuario.addTextNode(credentials.getUsuario());
        
        SOAPBody soapBody = envelope.getBody();
        SOAPElement adeudoTotal = soapBody.addChildElement("ConsultarAdeudoDetalle",sufijo);
        SOAPElement astrIdPaisCU = adeudoTotal.addChildElement("astrIdPaisCU",sufijo);
        astrIdPaisCU.addTextNode(String.valueOf(cu.getPais()));
        SOAPElement astrIdCanalCU = adeudoTotal.addChildElement("astrIdCanalCU",sufijo);
        astrIdCanalCU.addTextNode(String.valueOf(cu.getCanal()));
        SOAPElement astrIdSucursalCU = adeudoTotal.addChildElement("astrIdSucursalCU",sufijo);
        astrIdSucursalCU.addTextNode(String.valueOf(cu.getSucursal()));
        SOAPElement astrFolioCU = adeudoTotal.addChildElement("astrFolioCU",sufijo);
        astrFolioCU.addTextNode(String.valueOf(cu.getFolio()));
        
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction","http://tempuri.org/ConsultarAdeudoDetalle" );
        headers.addHeader("Content-Type", "application/soap+xml");
        
        soapMessage.saveChanges();
        
        return soapMessage;  
    }
    
    
    
}
