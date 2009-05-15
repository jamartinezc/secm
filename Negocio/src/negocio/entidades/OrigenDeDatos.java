
package negocio.entidades;

import accesodatos.frontera.consultoradeorigen.ConsultoraDeOrigen;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

/**
 *
 * @author Administrador
 */
public class OrigenDeDatos implements Serializable{

    private Properties columnas;
    private String mensaje;
    private String asunto;
    private File[] ajuntos;
    

    private String origen;

    private ConsultoraDeOrigen comportamientoOrigen;

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

    public File[] getAjuntos() {
        return ajuntos;
    }

    public void setAjuntos(File[] ajuntos) {
        this.ajuntos = ajuntos;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

            
            //inicializar correo por correo con los datos principales
            Iterator<String> etiquetasListIt = etiquetasList.iterator();
            for(int i=0; i<datos.length;i++){//pora cada correo
                int j=0,datosPrincipales=0;
                Correo correo = new Correo();
                while (etiquetasListIt.hasNext() && datosPrincipales<6) {//por cada columna
                    String etiqueta = etiquetasListIt.next();
                    String columna=datos[i][j];
                    if(etiqueta.equals("#destinatariosCC#")){
                        correo.setDestinatariosCC(columna.split(","));
                    }else if(etiqueta.equals("#destinatariosTO#")){
                        correo.setDestinatariosTO(columna.split(","));
                    }else if(etiqueta.equals("#destinatariosBCC#")){
                        correo.setDestinatariosBCC(columna.split(","));
                    }else if(etiqueta.equals("#asunto#")){
                        correo.setAsunto(columna);
                    }else if(etiqueta.equals("#adjuntos#")){
                        correo.setAdjuntos(null);//TODO ?
                    }else if(etiqueta.equals("#mensaje#")){
                        mensaje=columna;
                    }
                    j++;
                }
            correo.setMensaje(null);
            }


        }

        
        return null;
    }

    public boolean isModificable(){
        return false;
    }

    public String toString(){
        return "{"+origen+","+columnas+"}";
    }

}
