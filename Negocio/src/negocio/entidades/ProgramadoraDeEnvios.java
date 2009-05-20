/*

 */

package negocio.entidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Jorge A. Martínez
 */
public class ProgramadoraDeEnvios {

    private LinkedList<CorreoCalendarizado> calendarizados;

    private static ProgramadoraDeEnvios instancia;

    public static ProgramadoraDeEnvios getInstancia() {
        if(instancia==null){
            instancia = new ProgramadoraDeEnvios();
        }
        return instancia;
    }

    private ProgramadoraDeEnvios(){
        calendarizados=new LinkedList<CorreoCalendarizado>();
    }

    /**
     * Programa el envía de correo para que se envíe solo una vez
     * @param lista Lista a enviar.
     * @param fechaEnvio fecha en la que sequiere se envíe el correo.
     * @return verdadero si se pudo guardar el correoCalendarizado, falso de lo contrario.
     */
    public boolean programarEnvio(ListaDeCorreos lista, Date fechaEnvio){
        return programarEnvio(lista, fechaEnvio, -1);
    }

    /**
     * Programa el envío de una listaDeCorreos para cierta fecha y cada cierto tiempo.
     * @param lista
     * @param fechaEnvio
     * @param tiempoDeReenvio
     * @return
     */
    public boolean programarEnvio(ListaDeCorreos lista, Date fechaEnvio, long tiempoDeReenvio){
        CorreoCalendarizado calendarizado = new CorreoCalendarizado();
        calendarizado.setCorreos(lista);
        calendarizado.setFechaEnvio(fechaEnvio);
        calendarizado.setDiasEntreEnvios(tiempoDeReenvio);

        return guardar(calendarizado);
    }

    public boolean guardar(CorreoCalendarizado calendarizado){

        boolean abierto = abrir();
        if( !abierto ){
            return false;
        }
        calendarizados.add(calendarizado);

        String nombreDeArchivo = "Calendarizados.ecm";

        ObjectOutputStream out = null;
         try
         {
           out = new ObjectOutputStream( new FileOutputStream(nombreDeArchivo) );
           out.writeObject(calendarizados);
           out.close();
         }
         catch(IOException ex)
         {
           ex.printStackTrace();
           return false;
         }
        return true;
   }

    public boolean abrir(){

       String nombreDeArchivo = "Calendarizados.ecm";

       ObjectInputStream in = null;
       try
       {
         File archivo = new File( "Calendarizados.ecm" );
         if( archivo.length() > 0){
             in = new ObjectInputStream(new FileInputStream(nombreDeArchivo));
                calendarizados = ((LinkedList<CorreoCalendarizado>) in.readObject());
             in.close();
         }else{
                calendarizados = (new LinkedList<CorreoCalendarizado>());
         }
       }
       catch(IOException ex)
       {
         ex.printStackTrace();
         return false;
       }
       catch(ClassNotFoundException ex)
       {
         ex.printStackTrace();
         return false;
       }
       return true;
    }

}
