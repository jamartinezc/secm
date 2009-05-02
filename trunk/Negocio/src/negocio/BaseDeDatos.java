/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio;

/**
 *
 * @author Administrador
 */
public class BaseDeDatos implements OrigenDeDatos{
    
    private String [][] columnasAConsultar;
    private String usuario;
    private char[] contraseña;
    private String driver;
    private String direccion;
            

    public Correo leerOrigenDeDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isModificable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[][] getColumnasAConsultar() {
        return columnasAConsultar;
    }

    public void setColumnasAConsultar(String[][] columnasAConsultar) {
        this.columnasAConsultar = columnasAConsultar;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public char[] getContraseña() {
        return contraseña;
    }

    public void setContraseña(char[] contraseña) {
        this.contraseña = contraseña;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
