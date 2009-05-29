/*

 */

package negocio.control;

import java.util.Calendar;
import negocio.entidades.CorreoCalendarizado;

/**
 * Clase que evalua si es hora de enviar un correo.
 * @author Jorge A. Martínez
 */
public class TriggerDeEnvio implements Runnable {

    private boolean ejecutar;

    @Override
    public void run() {
        ejecutar=true;
        long dormir;
        while(ejecutar){
            CorreoCalendarizado siguiente = ProgramadoraDeEnvios.getInstancia().getSiguienteEnvio();
            Calendar fechaActual = Calendar.getInstance();
            if(siguiente != null){
                if(fechaActual.getTimeInMillis() >= siguiente.getFechaEnvio().getTime()){
                    //TODO registrar errores en el envío de correo
                    ProgramadoraDeEnvios.getInstancia().enviarCorreo(siguiente);
                    dormir = (30*1000);//para debug
                    //dormir = (10*60*1000);//esperar 10 minutos
                }else{
                    dormir = siguiente.getFechaEnvio().getTime() - fechaActual.getTimeInMillis();
                }
            }else{
                dormir = (30*1000);//para debug
                //dormir = (10*60*1000);//esperar 10 minutos
            }

            try {
                System.out.println("Durmiendo por:"+dormir);
                Thread.sleep(dormir);
            } catch (InterruptedException ex) {
                System.out.println("Se ingresó un nuevo CorreoCalendarizado, evaluar si debe ser enviado primero.");
            }
        }
    }

    public boolean isEjecutar() {
        return ejecutar;
    }

    public void setEjecutar(boolean ejecutar) {
        this.ejecutar = ejecutar;
    }

}
