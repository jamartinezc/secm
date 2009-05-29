
package negocio.control;

import accesodatos.frontera.consultoradeorigen.ConsultoraDeOrigen;
import accesodatos.frontera.consultoradeorigen.factory.ConsultoraDeOrigenFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import negocio.entidades.ListaDeCorreos;
import negocio.entidades.OrigenDeDatos;
import negocio.entidades.ServidorSMTP;


/**
 *
 * @author Jorge A. Martinez
 */
public class AdministradoraListasDeCorreos {

    private LinkedList<ListaDeCorreos> listas;

    private static AdministradoraListasDeCorreos instancia;

    public static AdministradoraListasDeCorreos getInstancia() {

        if(instancia == null){
            instancia = new AdministradoraListasDeCorreos();
        }
        return instancia;
    }

    private AdministradoraListasDeCorreos(){
        listas = new LinkedList<ListaDeCorreos>();
    }

    /**
     *
     * @param nombre el nombre de esta lista.
     * @param origen Tipo de origen de datos, debe tomarse de ConsultoraDeOrigenFactory.
     * @param datos Debe tener la property con key "rutaOrigen" correspondiente a la ruta de acceso al origen de los datos.
     * @return
     */public ListaDeCorreos crearListaDeCorreos(String nombre, int origen, Properties datos){
        
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
                setListas((LinkedList<ListaDeCorreos>) in.readObject());
             in.close();
         }else{
                setListas(new LinkedList<ListaDeCorreos>());
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
     * Busca una lista con el nombre indicado y la retorna, si esta no se encuentra retorna null.
     * @param nombreLista Nombre de la lista a buscar
     * @return La lista encontrada o <b>null</b> si no se encuentra.
     */
    public ListaDeCorreos buscar(String nombreLista){
        
        for (Iterator<ListaDeCorreos> it = listas.iterator(); it.hasNext();) {
            ListaDeCorreos listaDeCorreos = it.next();
            System.out.println("comparando con: "+listaDeCorreos+" para el nombre: "+nombreLista);
            if(listaDeCorreos.getNombre().equals(nombreLista)){
                System.out.println("ENCONTRADO: "+listaDeCorreos+" buscando por: "+nombreLista);
                return listaDeCorreos;
            }else{
                System.out.println("es diferente");
            }
        }

        return null;
    }

    public boolean guardar(ListaDeCorreos lista){

        boolean abierto = abrir();
        if( !abierto ){
            return false;
        }
        //no permitir listas duplicadas
        ListaDeCorreos listaEncontrada = buscar(lista.getNombre());
        if(listaEncontrada == null){//si la lista no existe
            getListas().add(lista);//agregarla
        }else{
            //reemplazar la que existe
            getListas().remove(listaEncontrada);
            getListas().add(lista);
        }

        String nombreDeArchivo = "Listas.ecm";

        ObjectOutputStream out = null;
         try
         {
           out = new ObjectOutputStream( new FileOutputStream(nombreDeArchivo) );
           out.writeObject(getListas());
           out.close();
         }
         catch(IOException ex)
         {
           ex.printStackTrace();
           return false;
         }
        return true;
   }

    public boolean enviarLista(ListaDeCorreos lista, ServidorSMTP servidor){
        lista.setServidorSMTP(servidor);
        return EnviadoraDeCorreos.getInstancia().enviarLista(lista);
    }

    /**
     * @return the listas
     */
    public LinkedList<ListaDeCorreos> getListas() {
        return listas;
    }

    /**
     * @param listas the listas to set
     */
    public void setListas(LinkedList<ListaDeCorreos> listas) {
        this.listas = listas;
    }

    public void eliminarRegistrosdeLista(String nombreLista, String[] correosAEliminar){

        ListaDeCorreos lista = buscar(nombreLista);
        //lista.getOrigenDeDatos().isModificable();
        AdministradoraListaNoreceptores.getInstancia().agregarNoReceptores(lista.getNoReceptores(), correosAEliminar);
    }
}
