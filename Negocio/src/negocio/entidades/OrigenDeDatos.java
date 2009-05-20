
package negocio.entidades;

import accesodatos.frontera.consultoradeorigen.ConsultoraDeOrigen;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

/**
 *
 * @author Administrador
 */
public class OrigenDeDatos implements Serializable{

    private Properties columnas;
    private String mensaje;
    private String asunto;
    private File[] adjuntos;
    

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

    public File[] getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(File[] adjuntos) {
        this.adjuntos = adjuntos;
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
        String where=null;
        LinkedList<Correo> correos = new LinkedList<Correo>();
        boolean conexionAbierta = abrir();
        if( conexionAbierta ){
            Enumeration etiquetas = columnas.propertyNames();
            ArrayList<String> columnasDeOrigen = new ArrayList<String>(15);
            ArrayList<String> etiquetasList = new ArrayList<String>(15);

            while(etiquetas.hasMoreElements()){
                String etiqueta = (String) etiquetas.nextElement();
                if( ! etiqueta.equalsIgnoreCase("#WHERE#") ){
                    etiquetasList.add(etiqueta);
                    columnasDeOrigen.add( columnas.getProperty(etiqueta));
                }else{
                    where= columnas.getProperty(etiqueta);
                }
            }

           String[] columnasAConsultar;
            if(where != null){//si hay un where insertarlo al final
                columnasAConsultar = new String[ columnasDeOrigen.size()+1 ];
                int id=0;
                for (Iterator<String> it = columnasDeOrigen.iterator(); it.hasNext();) {
                    String columna = it.next();
                    columnasAConsultar[id]=columna;
                    id++;
                }
                columnasAConsultar[id]=where;
            }else{
                 columnasAConsultar = columnasDeOrigen.toArray(new String[0]);
            }

            String[][] datos = comportamientoOrigen.consultarDatos(columnasAConsultar);

            //inicializar correo por correo con los datos principales
            
            for(int i=0; i<datos.length;i++){//para cada correo
                int j=0;
                Correo correo = new Correo();
                String mensajeActual=new String(mensaje);
                correo.setAdjuntos(adjuntos);
                correo.setAsunto(asunto);
                
                Iterator<String> etiquetasListIt = etiquetasList.iterator();
                while (etiquetasListIt.hasNext()) {//por cada columna
                    String etiqueta = etiquetasListIt.next();
                    String columna=datos[i][j];
                    if( ! etiqueta.equalsIgnoreCase("#WHERE#") ){//ignorar la etiqueta where
                        if(etiqueta.equals("#destinatariosCC#")){
                            correo.setDestinatariosCC(columna.split(","));
                        }else if(etiqueta.equals("#destinatariosTO#")){
                            correo.setDestinatariosTO(columna.split(","));
                        }else if(etiqueta.equals("#destinatariosBCC#")){
                            correo.setDestinatariosBCC(columna.split(","));
                        }else{
                            mensajeActual=mensajeActual.replaceAll(etiqueta, columna);
                        }
                        j++;
                    }
                }
            correo.setMensaje(mensajeActual);
            correos.add(correo);
            }

        }

        
        return correos.toArray(new Correo[0]);
    }

    public boolean isModificable(){
        return false;
    }

    public String toString(){
        return "{"+origen+","+columnas+"}";
    }

}
