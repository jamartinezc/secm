/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio;

import java.util.Date;

/**
 *
 * @author Administrador
 */
public class CorreoCalendarizado {
    
    private Date fechaEnvio;
    private long diasEntreEnvios;

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
