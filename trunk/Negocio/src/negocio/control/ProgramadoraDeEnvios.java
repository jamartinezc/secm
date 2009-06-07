/*

 */

package negocio.control;

import negocio.entidades.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Jorge A. Martínez
 */
public class ProgramadoraDeEnvios {

    private LinkedList<CorreoCalendarizado> calendarizados;
    private Thread triggerDeEnvio;

    private static ProgramadoraDeEnvios instancia;

    public static ProgramadoraDeEnvios getInstancia() {
        if(instancia==null){
            instancia = new ProgramadoraDeEnvios();
        }
        return instancia;
    }

    private ProgramadoraDeEnvios(){
        calendarizados=new LinkedList<CorreoCalendarizado>();
        triggerDeEnvio=new Thread(new TriggerDeEnvio());
        triggerDeEnvio.start();
        
    }

    /**
     * Programa el envía de correo para que se envíe solo una vez
     * @param lista Lista a enviar.
     * @param fechaEnvio fecha en la que sequiere se envíe el correo.
     * @return verdadero si se pudo guardar el correoCalendarizado, falso de lo contrario.
     */
    public boolean programarEnvio(ListaDeCorreos lista, ServidorSMTP servidorDeEnvio,Date fechaEnvio){
        return programarEnvio(lista, servidorDeEnvio, fechaEnvio, -1L);
    }

    /**
     * Programa el envío de una listaDeCorreos para cierta fecha y cada cierto tiempo.
     * @param lista
     * @param fechaEnvio
     * @param tiempoDeReenvio
     * @return
     */
    public boolean programarEnvio(ListaDeCorreos lista, ServidorSMTP servidorDeEnvio, Date fechaEnvio, long tiempoDeReenvio){
        lista.setServidorSMTP(servidorDeEnvio);
        AdministradoraListasDeCorreos.getInstancia().guardarLista(lista);
        
        CorreoCalendarizado calendarizado = new CorreoCalendarizado();
        calendarizado.setNombreDeLista(lista.getNombre());
        calendarizado.setFechaEnvio(fechaEnvio);
        calendarizado.setDiasEntreEnvios(tiempoDeReenvio);
        //informar al thread que se agregó otro CorreoCalendarizado
        triggerDeEnvio.interrupt();//Despierta al thread si esta durmiendo para que evalue si el nuevo CorreoCalendarizado debe ser enviado primero

        return guardar(calendarizado);
    }

    public boolean guardar(CorreoCalendarizado calendarizado){

        boolean abierto = abrir();
        if( !abierto ){
            return false;
        }

        CorreoCalendarizado existente = buscar(calendarizado.getNombreDeLista());
        if( existente != null ){
            calendarizados.remove(existente);
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

    protected void enviarCorreo(CorreoCalendarizado correoAEnviar) {

        AdministradoraListasDeCorreos.getInstancia().abrir();
        ListaDeCorreos listaAEnviar = AdministradoraListasDeCorreos.getInstancia().buscar(correoAEnviar.getNombreDeLista());
        if(listaAEnviar != null){
            EnviadoraDeCorreos.getInstancia().enviarLista(listaAEnviar);
        }
        long tParaEnvio = correoAEnviar.getDiasEntreEnvios();
        if(tParaEnvio > 0){
            System.out.println("Reprogrmando envío...");
            System.out.println("Envio actual: "+correoAEnviar.getFechaEnvio());
            correoAEnviar.setFechaEnvio( new Date(Calendar.getInstance().getTimeInMillis() + tParaEnvio) );
            ProgramadoraDeEnvios.getInstancia().guardar(correoAEnviar);
            System.out.println("Nuevo envio: "+correoAEnviar.getFechaEnvio());
        }
    }

    protected CorreoCalendarizado getSiguienteEnvio() {

        boolean hayAlmenosUnCalendarizado=false;
        boolean abierto = abrir();
        if( !abierto ){
            return null;
        }
        CorreoCalendarizado calendarizadoSiguiente= new CorreoCalendarizado();
        calendarizadoSiguiente.setFechaEnvio(new Time(Long.MAX_VALUE));
        for (Iterator<CorreoCalendarizado> it = calendarizados.iterator(); it.hasNext();) {
            hayAlmenosUnCalendarizado = true;
            CorreoCalendarizado correoCalendarizado = it.next();
            if( calendarizadoSiguiente.getFechaEnvio().getTime() > correoCalendarizado.getFechaEnvio().getTime() ){
                calendarizadoSiguiente=correoCalendarizado;
            }
        }
        
        //si no se encontró un minimo, hubo un error y se retorna null.
        if( calendarizadoSiguiente.getFechaEnvio().equals(new Time(Long.MAX_VALUE)) ){
            return null;
        }

        return calendarizadoSiguiente;
    }

    /**
     * Busca un correo calendarizado para la lista de nombre indicado
     * @param nombreCalendarizado el nombre a buscar en los calendarizados
     * @return el CorreoCalendarizado encontrado o null si no encuentra nada.
     */
    private CorreoCalendarizado buscar(String nombreCalendarizado) {

        Iterator<CorreoCalendarizado> it = calendarizados.iterator();
        while (it.hasNext()) {
            CorreoCalendarizado calendarizado = it.next();
            if(calendarizado.getNombreDeLista().equals(nombreCalendarizado)){
                return calendarizado;
            }
        }
        return null;
    }

    public LinkedList<CorreoCalendarizado> getCalendarizados() {
        abrir();
        System.out.println(calendarizados);
        return calendarizados;
    }

    public static void main(String[] args) {
        getInstancia().getCalendarizados();
    }
}
