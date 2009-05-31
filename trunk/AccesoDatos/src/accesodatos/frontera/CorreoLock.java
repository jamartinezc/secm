/*
    Ponerlo en el driver de correo y lansar una exception si está bloqueado, atrapar la exception y esperar 15 secs
 */

package accesodatos.frontera;

import java.util.LinkedList;

/**
 *
 * @author Jorge A. Martínez
 */
public class CorreoLock {

    private LinkedList<String> correosEnUso;

    private static CorreoLock instancia;

    public static CorreoLock getInstancia() {
        if(instancia == null){
            instancia = new CorreoLock();
        }
        return instancia;
    }

    private CorreoLock(){

    }

    public void bloquearCorreo(String cuenta){
        if( ! estaBloqueado(cuenta) ){
            correosEnUso.add(cuenta);
        }
    }

    public void desbloquearCorreo(String cuenta){
        correosEnUso.remove(cuenta);
    }

    /**
     * Verifica si un correo está en uso.
     * @param cuenta cuenta de correo a verificar
     * @return true - Si está en uso.<br>
     *         false - Si esta libre.
     */
    public boolean estaBloqueado(String cuenta){

        return correosEnUso.contains(cuenta);
    }

}
