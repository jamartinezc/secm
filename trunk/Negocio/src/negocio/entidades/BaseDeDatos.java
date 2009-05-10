
package negocio.entidades;

/**
 *
 * @author Administrador
 */
public class BaseDeDatos implements OrigenDeDatos{
    
    private String SqlAConsultar;
    private String usuario;
    private char[] contraseña;
    private String driver;
    private String direccion;
            

    @Override
    public Correo leerOrigenDeDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isModificable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSqlAConsultar() {
        return SqlAConsultar;
    }

    public void setSqlAConsultar(String SqlAConsultar) {
        this.SqlAConsultar = SqlAConsultar;
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
