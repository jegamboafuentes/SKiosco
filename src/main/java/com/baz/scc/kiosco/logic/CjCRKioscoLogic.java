package com.baz.scc.kiosco.logic;

import com.baz.scc.kiosco.cu.client.CjCRCuClient;
import com.baz.scc.kiosco.cu.dao.CjCRCuDao;
import com.baz.scc.kiosco.cu.model.CjCRCuMdlOut;
import com.baz.scc.kiosco.model.CjCRClienteUnico;
import com.baz.scc.kiosco.model.CjCRKioscoOut;
import com.baz.scc.kiosco.model.CjCRStatus;
import com.baz.scc.kiosco.model.CjCRStatusDescription;
import com.baz.scc.kiosco.model.CjCRUnidadNegocio;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CjCRKioscoLogic {

    private static final Logger LOG = Logger.getLogger(CjCRKioscoLogic.class);
    @Autowired
    CjCRCuClient clienteCUUDNS;

    @Autowired
    CjCRCuDao clienteCUDao;

    public CjCRKioscoOut salida(CjCRClienteUnico clienteUnico, String branch) {
        LOG.info("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*");
        LOG.info("*****Inicio del Servicio MAIN*****");
        LOG.info("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*");
        //Creacion de variables 
        CjCRKioscoOut salida = new CjCRKioscoOut();
        String[] clienteAlnova;// = {"28122404"};//,"49734757"};
        String nombre;
        String appPaterno;
        String appMaterno;
        List<CjCRUnidadNegocio> unidadesNegocio = new ArrayList<CjCRUnidadNegocio>();
        CjCRCuMdlOut clienteCU = new CjCRCuMdlOut();
        CjCRStatus status = new CjCRStatus();
        CjCRStatusDescription statusDescription = new CjCRStatusDescription();

        //Asignacion de variables y validacion
        clienteCU = clienteCUUDNS.clienteUnicoUDNS(clienteUnico);
        clienteAlnova = clienteCU.getResult().getClientesAlnova();
        nombre = clienteCU.getResult().getNombre();
        appPaterno = clienteCU.getResult().getApellidoPaterno();
        appMaterno = clienteCU.getResult().getApellidoMaterno();

        //Validacion de cliente alnova
        if (clienteAlnova.length != 0) {
            unidadesNegocio = clienteCUDao.getUDNS(Integer.parseInt(clienteCU.getResult().getTipoCliente()), clienteUnico, clienteAlnova, branch);
        } else {
            unidadesNegocio = clienteCUDao.getUDNS(Integer.parseInt(clienteCU.getResult().getTipoCliente()), clienteUnico, null, branch);
        }

        if (clienteCU.getResult().getTipoCliente().equals("-1")) {
            status = statusDescription.getSinCu();
        } else {
            status = statusDescription.getCorrecto();
        }

        salida.setStatus(status);
        salida.setNombre(nombre);
        salida.setAppPaterno(appPaterno);
        salida.setAppMaterno(appMaterno);
        salida.setClientesAlnova(clienteAlnova);
        salida.setClienteUnico(clienteUnico);
        salida.setUnidadesNegocio(unidadesNegocio);

        return salida;
    }

}
