/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio;

import java.io.File;

/**
 *
 * @author Administrador
 */
public class Correo {
    
    private String destinatario;
    private String asunto;
    private String mensaje;
    private File[] adjuntos;

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
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
