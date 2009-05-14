
package negocio.entidades;

import accesodatos.frontera.consultoradeorigen.ConsultoraDeOrigen;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author Administrador
 */
public class OrigenDeDatos {

    public Properties columnas;

    public String origen;

    public ConsultoraDeOrigen comportamientoOrigen;

    public Properties getColumnas() {
        return columnas;
    }

    public void setColumnas(Properties columnas) {
        this.columnas = columnas;
    }

    public ConsultoraDeOrigen getComportamientoOrigen() {
        return comportamientoOrigen;
    }

    public void setComportamientoOrigen(ConsultoraDeOrigen comportamientoOrigen) {
        this.comportamientoOrigen = comportamientoOrigen;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public boolean abrir(){
        return comportamientoOrigen.abrir(origen);
    }
    
    public Correo[] leerOrigenDeDatos(){

        boolean conexionAbierta = abrir();
        if( conexionAbierta ){
            Enumeration etiquetas = columnas.propertyNames();
            ArrayList<String> columnasDeOrigen = new ArrayList<String>(15);
            ArrayList<String> etiquetasList = new ArrayList<String>(15);

            while(etiquetas.hasMoreElements()){
                String etiqueta = (String) etiquetas.nextElement();
                etiquetasList.add(etiqueta);
                columnasDeOrigen.add( columnas.getProperty(etiqueta));
            }

            String[] columnasAConsultar = (String[]) columnasDeOrigen.toArray();

            String[][] datos = comportamientoOrigen.consultarDatos(columnasAConsultar);


            //inicializar correo por correo
            Correo correo = new Correo();
            correo.setDestinatariosCC(null);
            correo.setDestinatariosTO(null);
            correo.setDestinatariosBCC(null);
            correo.setAsunto(null);
            correo.setMensaje(null);
            correo.setAdjuntos(null);


            
        }

        
        return null;
    }

    public boolean isModificable(){
        return false;
    }
    

}
