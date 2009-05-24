
package negocio.control;

import java.util.Calendar;

/**
 *
 * @author Jorge A. Martínez
 */
public class TriggerDeNoReceptores implements Runnable{

    private boolean ejecutar;

    @Override
    public void run() {
        ejecutar=true;
        while(ejecutar){
//            CorreoCalendarizado siguiente = ProgramadoraDeEnvios.getInstancia().getSiguienteEnvio();
            Calendar fechaActual = Calendar.getInstance();
            fechaActual.getTimeInMillis();
//            if(fechaActual.getTimeInMillis() >= siguiente.getFechaEnvio().getTime()){
//                //TODO registrar errores en el envío de correo
//                ProgramadoraDeEnvios.getInstancia().enviarCorreo(siguiente);
//            }
//            try {
//                Thread.sleep(siguiente.getFechaEnvio().getTime() - fechaActual.getTimeInMillis());
//            } catch (InterruptedException ex) {
//                System.out.println("Se ingresó un nuevo CorreoCalendarizado, evaluar si debe ser enviado primero.");
//            }
        }
    }

    public boolean isEjecutar() {
        return ejecutar;
    }

    public void setEjecutar(boolean ejecutar) {
        this.ejecutar = ejecutar;
    }

}
