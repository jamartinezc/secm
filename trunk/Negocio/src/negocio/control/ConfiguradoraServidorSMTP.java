
package negocio.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import negocio.entidades.ServidorSMTP;

/**
 *
 * @author JORGE
 */
public class ConfiguradoraServidorSMTP {
    
    private static ConfiguradoraServidorSMTP instancia;
    private LinkedList<ServidorSMTP> servidores;

    private ConfiguradoraServidorSMTP(){
        servidores = new LinkedList();
    }

    public static ConfiguradoraServidorSMTP getInstancia() {
        if(instancia == null){
            instancia = new ConfiguradoraServidorSMTP();
        }
        return instancia;
    }
    
    public boolean CrearServidorSMTP(String host,int puerto, boolean usarSSL, String correoRemitente, char[] contraseña){
        ServidorSMTP nuevoServidor = new ServidorSMTP();
        nuevoServidor.setContrasena(contraseña);
        nuevoServidor.setCorreoRemitente(correoRemitente);
        nuevoServidor.setHost(host);
        nuevoServidor.setPuerto(puerto);
        nuevoServidor.setUsarSSL(usarSSL);
//        nuevoServidor.guardar();
        guardar(nuevoServidor);
        return true;
    }

    public LinkedList<ServidorSMTP> getServidores() {
        return servidores;
    }

    public void setServidores(LinkedList<ServidorSMTP> servidores) {
        this.servidores = servidores;
    }

    /**
     * Carga el archivo de servidores, este método es llamada cada vez que se consulta la lista.
     * @return true - si se pudo abrir el archivo<br>
     *         false - si no se pudo abrir el archivo debido a un error.
     */
    public boolean abrir(){

       String nombreDeArchivo = "Servidores.ecm";

       ObjectInputStream in = null;
       try
       {
         File archivo = new File( "Servidores.ecm" );
         if( archivo.length() > 0){
             in = new ObjectInputStream(new FileInputStream(nombreDeArchivo));
                servidores=((LinkedList<ServidorSMTP>) in.readObject());
             in.close();
         }else{
                servidores=(new LinkedList<ServidorSMTP>());
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

    /**
     * Busca un servidor SMTP con el correo especificado
     * @param correoDeEnvio correo a buscar
     * @return el servidor que tiene ese correo indicado, configurado.
     */
    public ServidorSMTP buscar(String correoDeEnvio){

        for (Iterator<ServidorSMTP> it = servidores.iterator(); it.hasNext();) {
            ServidorSMTP servidor = it.next();
            System.out.println("comparando con: "+servidor+" para el nombre: "+correoDeEnvio);
            if(servidor.getCorreoRemitente().equals(correoDeEnvio)){
                System.out.println("ENCONTRADO: "+servidor+" buscando por: "+correoDeEnvio);
                return servidor;
            }else{
                System.out.println("es diferente");
            }
        }

        return null;
    }

    /**
     * Guarda un servidor en la lista de servidores y lo guarda en el archivo.
     * @param servidor
     * @return true - si se pudo escribir el archivo<br>
     *         false - si no se pudo escribir en el archivo
     */
    public boolean guardar(ServidorSMTP servidor){

        boolean abierto = abrir();
        if( !abierto ){
            return false;
        }
        //no permitir servidores duplicados
        ServidorSMTP servidorEncontrado = buscar(servidor.getCorreoRemitente());
        if(servidorEncontrado == null){//si el servidor no existe
            servidores.add(servidor);//agregarlo
        }else{
            //reemplazar el que existe
            servidores.remove(servidorEncontrado);
            servidores.add(servidor);
        }

        String nombreDeArchivo = "Servidores.ecm";

        ObjectOutputStream out = null;
         try
         {
           out = new ObjectOutputStream( new FileOutputStream(nombreDeArchivo) );
           out.writeObject(servidores);
           out.close();
         }
         catch(IOException ex)
         {
           ex.printStackTrace();
           return false;
         }
        return true;
   }
}
