/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio.entidades;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class CorreoCalendarizado implements Serializable {
    
    private Date fechaEnvio;
    private long diasEntreEnvios;
    private String nombreDeLista;//TODO asignar la lista correspondiente al enviar

    public CorreoCalendarizado(){
        diasEntreEnvios=-1;
    }

    public String getNombreDeLista() {
        return nombreDeLista;
    }

    public void setNombreDeLista(String nombreDeLista) {
        this.nombreDeLista = nombreDeLista;
    }
    
    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public long getDiasEntreEnvios() {
        return diasEntreEnvios;
    }

    public void setDiasEntreEnvios(long diasEntreEnvios) {
        this.diasEntreEnvios = diasEntreEnvios;
    }

}
