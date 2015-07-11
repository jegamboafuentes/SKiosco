package com.baz.scc.kiosco.cu.dao;

import com.baz.scc.kiosco.captacion.logic.CjCRCapLogic;
import com.baz.scc.kiosco.captacion.model.*;
import com.baz.scc.kiosco.model.CjCRClienteUnico;
import com.baz.scc.kiosco.model.CjCRUnidadNegocio;
import com.baz.scc.kiosco.prespren.client.*;
import com.baz.scc.kiosco.prespren.model.*;
import com.baz.scc.kiosco.support.CjCRPAppConfig;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CjCRCuDao {

    @Autowired
    @Qualifier("usrcajaJdbcTemplate")
    private JdbcTemplate usrcajaJdbcTemplate;

    private static final Logger LOG = Logger.getLogger(CjCRCuDao.class);

    @Autowired
    CjCRPPClientTotal ppTotal;
    @Autowired
    CjCRPPClientDetalle ppDetalle;
    @Autowired
    CjCRCapLogic captacionClientes;
    @Autowired
    CjCRPAppConfig appConfig;

    public List<CjCRUnidadNegocio> getUNDSBD(int tipoCliente) {
        LOG.info("Inicia proceso de extraccion de Unidades de Negocio Oracle");
        String query = "SELECT U.FIUDNID, D.FCNOMUDN\n"
                + "FROM  TCCJKITIPOCTEUDN U\n"
                + "INNER JOIN TCCJKIUDN D\n"
                + "ON (U.FIUDNID = D.FIUDNID)\n"
                + "WHERE U.FITIPOCLIENTEID = " + tipoCliente + "\n"
                + "ORDER BY U.FIUDNID";
        return usrcajaJdbcTemplate.query(query, new RowMapper<CjCRUnidadNegocio>() {

            @Override
            public CjCRUnidadNegocio mapRow(ResultSet rs, int rowNum) throws SQLException {
                CjCRUnidadNegocio unidadNegocio = new CjCRUnidadNegocio();

                unidadNegocio.setId(rs.getInt(1));
                unidadNegocio.setNombre(rs.getString(2));
                return unidadNegocio;
            }

        });

    }

    /*Este metodo debe de cambiar por la partde donde jalen los datos de Oracle*/
    public List<CjCRUnidadNegocio> getUDNS(int tipoCliente, CjCRClienteUnico cu, String []alnova, String branch) {
        boolean banderaBD = appConfig.isClienteUnicoTipoClienteDb();//Se paremtriza desde app config
        List<CjCRUnidadNegocio> udns = new ArrayList<CjCRUnidadNegocio>();

        //Objetos para los clientes 
        CjCRPPSalidaTotalMdl prestaPrendaTotal = new CjCRPPSalidaTotalMdl();
        List<Object> prestaPrendaDetalle = new ArrayList<Object>();

        CjCRCapAll captacionServices = new CjCRCapAll();
        ArrayList<Object> captacionDetalle = new ArrayList<Object>();

        //Valida si se jalaran los datos de la BD o los tomara en duro
        if (banderaBD == true) {
            udns = getUNDSBD(tipoCliente);
            return udns;
        } else {
            CjCRUnidadNegocio cargado = new CjCRUnidadNegocio();
            CjCRUnidadNegocio captacion = new CjCRUnidadNegocio();
            CjCRUnidadNegocio credito = new CjCRUnidadNegocio();
            CjCRUnidadNegocio prestap = new CjCRUnidadNegocio();
            CjCRUnidadNegocio micronegocio = new CjCRUnidadNegocio();
            cargado.setId(1);
            cargado.setNombre("Cargado");
            captacion.setId(2);
            captacion.setNombre("Captacion");
            credito.setId(3);
            credito.setNombre("Credito");
            prestap.setId(4);
            prestap.setNombre("Presta Prenda");
            micronegocio.setId(5);
            micronegocio.setNombre("Micronegocio");

            switch (tipoCliente) {
                case 1:
                    udns.add(cargado);
                    break;
                case 2:
                    udns.add(captacion);
                    break;
                case 3:
                    udns.add(credito);
                    break;
                case 4:
                    udns.add(captacion);
                    udns.add(credito);
                    break;
                case 5:
                    udns.add(prestap);
                    break;
                case 6:
                    udns.add(credito);
                    udns.add(prestap);
                    break;
                case 7:
                    udns.add(prestap);
                    udns.add(captacion);
                    break;
                case 8:
                    udns.add(credito);
                    udns.add(captacion);
                    udns.add(prestap);
                    break;
                case 9:
                    udns.add(micronegocio);
                    break;
                case 10:
                    udns.add(micronegocio);
                    udns.add(credito);
                    break;
                case 11:
                    udns.add(micronegocio);
                    udns.add(prestap);
                    break;
                case 12:
                    udns.add(micronegocio);
                    udns.add(prestap);
                    udns.add(credito);
                    break;
                case 13:
                    udns.add(micronegocio);
                    udns.add(captacion);
                    break;
                case 14:
                    udns.add(micronegocio);
                    udns.add(captacion);
                    udns.add(credito);
                    break;
                case 15:
                    udns.add(micronegocio);
                    udns.add(captacion);
                    udns.add(prestap);
                    break;
                case 16:
                    udns.add(micronegocio);
                    udns.add(prestap);
                    udns.add(credito);
                    udns.add(captacion);
                    break;
                default:
                    LOG.error("Error en CjCRCuDao(getUDNS) Tipo de cliente no registrado -CLIENTE CU");
                    break;
            }
            if (udns.contains(prestap)) {
                //Consumo de servicio Total de Presta Prenda
                try {
                    prestaPrendaTotal = ppTotal.soapConectionTotal(cu);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.error("Error en CjCRCuDao(getUDNS) al consumir el servicio SOAP de Presta Prenda (Total)" + e);
                }
                //Consumo de servicio Detalle de Presta Prenda
                try {
                    prestaPrendaDetalle = ppDetalle.soapConectionDetalle(cu);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.error("Error en CjCRCuDao(getUDNS) al consumir el servicio SOAP de Presta Prenda (Detalle)" + e);
                }
                prestap.setTotal(prestaPrendaTotal);
                prestap.setDetalle(prestaPrendaDetalle);
            }

            if (udns.contains(captacion)) {
                try {
                    captacionServices = captacionClientes.captacionServices(alnova,branch);
                    captacionDetalle = (ArrayList<Object>) (Object) captacionServices.getDetalle();
                } catch (Exception captacionLog) {
                    LOG.error("Error en CjCRCuDao(getUDNS) al consumir el servicio Rest de Captacion Logic" + captacionLog);
                }
                captacion.setTotal(captacionServices.getTotal());
                captacion.setDetalle(captacionDetalle);
            }

            return udns;
        }

    }

}
