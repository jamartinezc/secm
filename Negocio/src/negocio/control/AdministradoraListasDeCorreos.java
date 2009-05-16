
package negocio.control;

import accesodatos.frontera.consultoradeorigen.ConsultoraDeOrigen;
import accesodatos.frontera.consultoradeorigen.factory.ConsultoraDeOrigenFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Properties;
import negocio.entidades.ListaDeCorreos;
import negocio.entidades.OrigenDeDatos;


/**
 *
 * @author Jorge A. Martinez
 */
public class AdministradoraListasDeCorreos {

    public LinkedList<ListaDeCorreos> listas;

    private static AdministradoraListasDeCorreos instancia;

    public static AdministradoraListasDeCorreos getInstancia() {

        if(instancia == null){
            instancia = new AdministradoraListasDeCorreos();
        }
        return instancia;
    }

    private AdministradoraListasDeCorreos(){
    }

    public ListaDeCorreos crearListaDeCorreos(String nombre, int origen, Properties datos){
        
        ConsultoraDeOrigen cons = ConsultoraDeOrigenFactory.create(origen, datos);
        ListaDeCorreos lista = new ListaDeCorreos();
        OrigenDeDatos origenDeDatos = new OrigenDeDatos();
        origenDeDatos.setComportamientoOrigen(cons);
        origenDeDatos.setOrigen(datos.getProperty("rutaOrigen"));
        lista.setNombre(nombre);
        lista.setOrigenDeDatos(origenDeDatos);

        return lista;
    }

    public boolean setColumnas(ListaDeCorreos lista, Properties columnas, String asunto, String mensaje, File[] adjuntos){
        lista.getOrigenDeDatos().setColumnas(columnas);
        lista.getOrigenDeDatos().setAsunto(asunto);
        lista.getOrigenDeDatos().setMensaje(mensaje);
        lista.getOrigenDeDatos().setAdjuntos(adjuntos);
        //return guardar(lista);
        return true;
    }

    public boolean abrir(){

       String nombreDeArchivo = "Listas.ecm";

       ObjectInputStream in = null;
       try
       {
         File archivo = new File( "Listas.ecm" );
         if( archivo.length() > 0){
             in = new ObjectInputStream(new FileInputStream(nombreDeArchivo));
             listas = (LinkedList<ListaDeCorreos>)in.readObject();
             in.close();
         }else{
            listas = new LinkedList<ListaDeCorreos>();
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

    public boolean guardar(ListaDeCorreos lista){

        boolean abierto = abrir();
        if( !abierto ){
            return false;
        }
        listas.add(lista);

        String nombreDeArchivo = "Listas.ecm";

        ObjectOutputStream out = null;
         try
         {
           out = new ObjectOutputStream( new FileOutputStream(nombreDeArchivo) );
           out.writeObject(listas);
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
