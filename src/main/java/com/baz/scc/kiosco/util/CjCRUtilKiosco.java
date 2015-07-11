package com.baz.scc.kiosco.util;

import static java.lang.Math.abs;
import java.util.Random;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CjCRUtilKiosco {
    
    private static final Logger LOG = Logger.getLogger(CjCRUtilKiosco.class); 
  
    public Integer createI (){
        int i;
        int iAbs;
        Random rnd = new Random(System.currentTimeMillis());
        i = rnd.nextInt();
        iAbs = abs(i)/1000000; 
        return iAbs;
    }
    public String getProducto (String cuentaVeinteDigitos){
        String cuentaCliente;
        if(cuentaVeinteDigitos.length()==20){
            final String entidadBanco = cuentaVeinteDigitos.substring(0,4);
            final String sucursalGestora = cuentaVeinteDigitos.substring(4,8);
            final String digitosVerificadores = cuentaVeinteDigitos.substring(8,10);
            final String productosCuenta = cuentaVeinteDigitos.substring(10,12);
            final String consecutivoCuenta = cuentaVeinteDigitos.substring(12,20);
            cuentaCliente = productosCuenta;
        }else{
            LOG.error("Error en CjCRUtilKiosco (getProducto), Error: El numero de cuenta de veinte digitos es incorreco");
            cuentaCliente = "00";
        }
        return cuentaCliente;
    }
    public String cuentaCliente (String cuentaVeinteDigitos){
        String cuentaCliente;
        if(cuentaVeinteDigitos.length()==20){
            final String entidadBanco = cuentaVeinteDigitos.substring(0,4);
            final String sucursalGestora = cuentaVeinteDigitos.substring(4,8);
            final String digitosVerificadores = cuentaVeinteDigitos.substring(8,10);
            final String productosCuenta = cuentaVeinteDigitos.substring(10,12);
            final String consecutivoCuenta = cuentaVeinteDigitos.substring(12,20);
            cuentaCliente = sucursalGestora+productosCuenta+consecutivoCuenta;
        }else{
            LOG.error("Error en CjCRUtilKiosco (cuentaCliente), Error: El numero de cuenta de veinte digitos es incorreco");
            cuentaCliente = "00000000000000000000";
        }
        return cuentaCliente;
    }
    
    
}
