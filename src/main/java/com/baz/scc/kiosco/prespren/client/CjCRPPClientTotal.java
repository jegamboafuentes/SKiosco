package com.baz.scc.kiosco.prespren.client;

import com.baz.scc.kiosco.cu.dao.CjCRCuDao;
import com.baz.scc.kiosco.model.CjCRClienteUnico;
import com.baz.scc.kiosco.prespren.model.CjCRPPCredencialesMdl;
import com.baz.scc.kiosco.prespren.model.CjCRPPSalidaTotalMdl;
import com.baz.scc.kiosco.support.CjCRPAplication;
import javax.xml.soap.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CjCRPPClientTotal {
    
    @Autowired
    CjCRPAplication appProperties;

    private static final Logger LOG = Logger.getLogger(CjCRCuDao.class);

    private static final CjCRPPCredencialesMdl credentials = new CjCRPPCredencialesMdl();

    public CjCRPPClientTotal() {
        credentials.setUsuario("usrBackOffice");
        credentials.setContrasena("kiosco");
    }

    public CjCRPPSalidaTotalMdl soapConectionTotal(CjCRClienteUnico cu) throws SOAPException {
        CjCRPPSalidaTotalMdl salidaTotal = new CjCRPPSalidaTotalMdl();
        try {
            LOG.info("Inicia el cliente para Presta Prenda (TOTAl)");

            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            System.out.println(appProperties.configuraciones().getPrestaPrendaTotal()+"<----");
//            String url = appProperties.configuraciones().getPrestaPrendaTotal();
            String url = "http://10.51.217.245:8081/SCP/Servicios/BackOfficeSwnKioscoS90.asmx";
            LOG.info("Servicio SOAP a consumir: " + url);
            SOAPMessage soapResponse = soapConnection.call(consultaTotalPP(cu), url);
            
            salidaTotal.setAdeudoTotal(soapResponse.getSOAPBody().getElementsByTagName("z:row").item(0).getAttributes().item(0).getNodeValue());
            salidaTotal.setFechaNacimiento(soapResponse.getSOAPBody().getElementsByTagName("z:row").item(0).getAttributes().item(1).getNodeValue());
            salidaTotal.setIdError(soapResponse.getSOAPBody().getElementsByTagName("z:row").item(0).getAttributes().item(2).getNodeValue());
            salidaTotal.setNombreCliente(soapResponse.getSOAPBody().getElementsByTagName("z:row").item(0).getAttributes().item(3).getNodeValue());
            salidaTotal.setNumeroBoletas(soapResponse.getSOAPBody().getElementsByTagName("z:row").item(0).getAttributes().item(4).getNodeValue());
            salidaTotal.setRespuestaError(soapResponse.getSOAPBody().getElementsByTagName("z:row").item(0).getAttributes().item(5).getNodeValue());

            soapConnection.close();
            LOG.info("El servicio se consumio correctamente");
        } catch (SOAPException e) {
            LOG.error("Aqui trono -->>>>>",e);
            
        }
        return salidaTotal;
    }

    private static SOAPMessage consultaTotalPP(CjCRClienteUnico cu) throws SOAPException {
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
        SOAPElement entAutentificarKiosco = header.addChildElement("EntAutentificarKiosco", sufijo);
        SOAPElement contrasenia = entAutentificarKiosco.addChildElement("Contrasenia", sufijo);
        contrasenia.addTextNode(credentials.getContrasena());
        SOAPElement usuario = entAutentificarKiosco.addChildElement("Usuario", sufijo);
        usuario.addTextNode(credentials.getUsuario());
        
        SOAPBody soapBody = envelope.getBody();
        SOAPElement adeudoTotal = soapBody.addChildElement("ConsultarAdeudoTotal", sufijo);
        SOAPElement astrIdPaisCU = adeudoTotal.addChildElement("astrIdPaisCU", sufijo);
        astrIdPaisCU.addTextNode(String.valueOf(cu.getPais()));
        SOAPElement astrIdCanalCU = adeudoTotal.addChildElement("astrIdCanalCU", sufijo);
        astrIdCanalCU.addTextNode(String.valueOf(cu.getCanal()));
        SOAPElement astrIdSucursalCU = adeudoTotal.addChildElement("astrIdSucursalCU", sufijo);
        astrIdSucursalCU.addTextNode(String.valueOf(cu.getSucursal()));
        SOAPElement astrFolioCU = adeudoTotal.addChildElement("astrFolioCU", sufijo);
        astrFolioCU.addTextNode(String.valueOf(cu.getFolio()));

        
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", "http://tempuri.org/ConsultarAdeudoTotal");
        headers.addHeader("Content-Type", "application/soap+xml");

        soapMessage.saveChanges();
        return soapMessage;
    }

}
