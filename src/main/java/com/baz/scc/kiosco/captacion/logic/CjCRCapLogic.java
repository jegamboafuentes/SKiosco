package com.baz.scc.kiosco.captacion.logic;

import com.baz.scc.kiosco.captacion.detalle.client.CjCRCapDetalleClient;
import com.baz.scc.kiosco.captacion.detalle.model.*;
import com.baz.scc.kiosco.captacion.model.*;
import com.baz.scc.kiosco.captacion.total.client.CjCRCapTotalClient;
import com.baz.scc.kiosco.captacion.total.model.*;
import com.baz.scc.kiosco.support.CjCRPCaptacionConfig;
import com.baz.scc.kiosco.util.CjCRUtilKiosco;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CjCRCapLogic {
    private static final Logger LOG = Logger.getLogger(CjCRCapLogic.class);
    
    @Autowired
    CjCRCapTotalClient totalClient;
    @Autowired
    CjCRCapDetalleClient detalleClient;
    @Autowired
    CjCRUtilKiosco util;
    @Autowired
    CjCRPCaptacionConfig captacionConfig;
    
    public CjCRCapAll captacionServices(String [] alnova, String branch)
   {
        CjCRCapAll salida = new CjCRCapAll();
        List<CjCRCapAllDetalle> listaDetalle = new ArrayList<CjCRCapAllDetalle>();
        CjCRCapTotalMdlIn [] inTotal = new CjCRCapTotalMdlIn[alnova.length];
        if(alnova.length!=1){
            for(int i= 0;i<alnova.length;i++){
                inTotal[i] = getCjCRCapTotalMdlIn(alnova[i], branch);
            }
        }else{
            inTotal[0] = getCjCRCapTotalMdlIn(alnova[0], branch);
        }
        salida.setTotal(captacionTotalService(inTotal));
        
        for(int i = 0; i<salida.getTotal().getCuentas().size();i++){
            CjCRCapAllDetalle detalle = new CjCRCapAllDetalle();
            CjCRCapDetalleMdlIn entradaDetalle = new CjCRCapDetalleMdlIn();
            String noCuenta = salida.getTotal().getCuentas().get(i).getNoCuenta();
            entradaDetalle.setAc(noCuenta);
            detalle = captacionDetalleService(getCjCRCapDetalleMdlIn(inTotal[0],noCuenta));
            listaDetalle.add(detalle);
        }    
        salida.setDetalle(listaDetalle);
    
        //Seteando valores para vista total
        for(int i = 0; i<salida.getTotal().getCuentas().size();i++){
            String noCuentaVeinte = salida.getTotal().getCuentas().get(i).getNoCuenta();
            salida.getTotal().getCuentas().get(i).setNoCuenta(util.cuentaCliente(noCuentaVeinte));
            salida.getTotal().getCuentas().get(i).setProducto(util.getProducto(noCuentaVeinte));
            String subProducto = listaDetalle.get(i).getSubProducto();
            salida.getTotal().getCuentas().get(i).setSubProducto(subProducto);
            String nombreCuenta = listaDetalle.get(i).getNombreCuenta();
            salida.getTotal().getCuentas().get(i).setNombreCuenta(nombreCuenta);
        }
        return salida;
    }
    
    private CjCRCapTotalMdlIn getCjCRCapTotalMdlIn (String clienteAlnova, String branch){
        CjCRCapTotalMdlIn objetoRegreso = new CjCRCapTotalMdlIn();
        String user = captacionConfig.getCaptacionP055User();
        String entity = util.createI().toString();
        String terminal = captacionConfig.getCaptacionP055Terminal();
        String alnova = clienteAlnova;
        String me = captacionConfig.getCaptacionP055Me();
        String fu = captacionConfig.getCaptacionP055Fu();
        objetoRegreso.setU(user);
        objetoRegreso.setB(branch);
        objetoRegreso.setI(entity);
        objetoRegreso.setT(terminal);
        objetoRegreso.setNu(alnova);
        objetoRegreso.setMe(me);
        objetoRegreso.setFu(fu);
        return objetoRegreso;
    }
    private CjCRCapDetalleMdlIn getCjCRCapDetalleMdlIn (CjCRCapTotalMdlIn entradaTotal, String numCuenta){
        CjCRCapDetalleMdlIn objetoRegreso = new CjCRCapDetalleMdlIn();
        String user = entradaTotal.getU();
        String branch = entradaTotal.getB();
        String entity = util.createI().toString();
        String transaction = entradaTotal.getT();
        String numeroCuenta = numCuenta;
        String da = captacionConfig.getCaptacionB44SDa();
        String dt = captacionConfig.getCaptacionB44SDt();
        String op = captacionConfig.getCaptacionB44SOp();
        String mo = captacionConfig.getCaptacionB44SMo();
        String pa = captacionConfig.getCaptacionB44SPa();
        String fi = captacionConfig.getCaptacionB44SFi();
        String tr = captacionConfig.getCaptacionB44STr();
        String in = captacionConfig.getCaptacionB44SIn();
        String to = captacionConfig.getCaptacionB44STo();
        objetoRegreso.setU(user);
        objetoRegreso.setB(branch);
        objetoRegreso.setI(entity);
        objetoRegreso.setT(transaction);
        objetoRegreso.setAc(numeroCuenta);
        objetoRegreso.setDa(da);
        objetoRegreso.setDt(dt);
        objetoRegreso.setOp(op);
        objetoRegreso.setMo(mo);
        objetoRegreso.setPa(pa);
        objetoRegreso.setFi(fi);
        objetoRegreso.setTr(tr);
        objetoRegreso.setIn(in);
        objetoRegreso.setTo(to);
        return objetoRegreso;          
    }
    private CjCRCapAllTotal captacionTotalService(CjCRCapTotalMdlIn[] in){    
        CjCRCapAllTotal salidaTotalCaptacion = new CjCRCapAllTotal();
        CjCRCapTotalMdlOut captacionTotalS = new CjCRCapTotalMdlOut();
        List<CjCRCapTotalMdlOutN> totales = new ArrayList<CjCRCapTotalMdlOutN>();
        for(int i = 0;i<in.length;i++){//Este for es para la cantidad de alnovas
            CjCRCapTotalMdlOut temporal = totalClient.p055Client(in[i]);
            for(int j = 0; j<(temporal.getTotal().length)-1;j++){
                totales.add(temporal.getTotal()[j]);
            }
            captacionTotalS.setNumeroCliente(temporal.getNumeroCliente());
        }
        CjCRCapTotalMdlOutN[] arrayTotal = totales.toArray(new CjCRCapTotalMdlOutN[totales.size()]);
        captacionTotalS.setTotal(arrayTotal);
        //CjCRCapTotalMdlOut captacionTotalS = totalClient.p055Client(in);
        double disponible = 0;
        double total = 0;
        List<CjCRCapAllTotalCuentas> cuentas = new ArrayList<CjCRCapAllTotalCuentas>();
        
        for(int i=0;i<captacionTotalS.getTotal().length;i++){
            double disponibleCuenta = Double.parseDouble(captacionTotalS.getTotal()[i].getDisponible());
            double totalCuenta = Double.parseDouble(captacionTotalS.getTotal()[i].getTotal());
            CjCRCapAllTotalCuentas cuenta = new CjCRCapAllTotalCuentas();
            disponible = disponible + disponibleCuenta;
            total = total + totalCuenta;
            cuenta.setNoCuenta(captacionTotalS.getTotal()[i].getNumeroCuenta());
            cuenta.setTotalCuenta(Double.parseDouble(captacionTotalS.getTotal()[i].getTotal()));
            cuenta.setDisponibleCuenta(Double.parseDouble(captacionTotalS.getTotal()[i].getDisponible()));
            cuenta.setRetenidoCuenta(Double.parseDouble(captacionTotalS.getTotal()[i].getRetenido()));
            cuentas.add(cuenta);
        }
        salidaTotalCaptacion.setDisponible(disponible);
        salidaTotalCaptacion.setTotal(total);
        salidaTotalCaptacion.setCuentas(cuentas);
        return salidaTotalCaptacion;
    }
    private CjCRCapAllDetalle captacionDetalleService(CjCRCapDetalleMdlIn in){
        CjCRCapAllDetalle salidaDetalle = new CjCRCapAllDetalle();
        List<CjCRCapAllDetalleMvtos> movimiento = new ArrayList<CjCRCapAllDetalleMvtos>();
        CjCRCapDetalleMdlOut clienteSalida = detalleClient.b44SClient(in);
        salidaDetalle.setNoCuenta(in.getAc());
        salidaDetalle.setNombreCuenta(clienteSalida.getDs());
        salidaDetalle.setSubProducto(clienteSalida.getDq());
        salidaDetalle.setProducto(util.getProducto(in.getAc()));
        
        //Movimientos
        for(int i = 0; i<clienteSalida.getN2().length;i++){       
            CjCRCapAllDetalleMvtos mov = new CjCRCapAllDetalleMvtos();
            Double importe = 0.0;
            Double saldo = 0.0;
            try{
                importe = Double.parseDouble(clienteSalida.getN2()[i].getMo());
                saldo = Double.parseDouble(clienteSalida.getN2()[i].getBa());
            }catch(Exception e){
                LOG.error("Error en CjCRCapLogic (captacionDetalleService) al parsear elemto: "+e);
            }
            mov.setFecha(clienteSalida.getN2()[i].getFc());
            mov.setImporte(importe);
            mov.setSaldo(saldo);
            mov.setConcepto(clienteSalida.getN2()[i].getOs());
            movimiento.add(mov);
            
        }
        CjCRCapAllDetalleMvtos movimientoArray [] = new CjCRCapAllDetalleMvtos[movimiento.size()];
        movimientoArray = movimiento.toArray(movimientoArray);
        salidaDetalle.setMovimientos(movimientoArray);
        return salidaDetalle;  
    }
    
    
    
    
}
