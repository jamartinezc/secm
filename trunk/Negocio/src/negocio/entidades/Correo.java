/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio.entidades;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class Correo implements Serializable{
    
    private String[] destinatariosTO;
    private String[] destinatariosCC;
    private String[] destinatariosBCC;
    private String asunto;
    private String mensaje;
    private File[] adjuntos;

    public Correo(){
        destinatariosCC = new String[0];
        destinatariosTO = new String[0];
        destinatariosBCC = new String[0];
        asunto="";
        mensaje="";
        adjuntos=new File[0];
    }
    
    public String[] getDestinatariosBCC() {
        return destinatariosBCC;
    }

    public void setDestinatariosBCC(String[] destinatariosBCC) {
        this.destinatariosBCC = destinatariosBCC;
    }

    public String[] getDestinatariosCC() {
        return destinatariosCC;
    }

    public void setDestinatariosCC(String[] destinatariosCC) {
        this.destinatariosCC = destinatariosCC;
    }

    public String[] getDestinatariosTO() {
        return destinatariosTO;
    }

    public void setDestinatariosTO(String[] destinatario) {
        this.destinatariosTO = destinatario;
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

    public File[] getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(File[] adjuntos) {
        this.adjuntos = adjuntos;
    }
    

}
