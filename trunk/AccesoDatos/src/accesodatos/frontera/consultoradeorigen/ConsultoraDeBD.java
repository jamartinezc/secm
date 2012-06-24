
package accesodatos.frontera.consultoradeorigen;

import accesodatos.frontera.DriverBD;
import java.io.Serializable;
import java.lang.String;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Clase para consultar una base de datos.
 * @author Jorge A. Martínez
 */
public class ConsultoraDeBD implements ConsultoraDeOrigen , Serializable{

    private String driver;
    private String protocoloDBMS;
    transient private Connection conexion;//transient para que no se guarde al ser persistida
    private String usuario;
    private char[] contrasena;
    private String baseDeDatos;

    /**
     *
     * Constructora de ConsultoraDeBD
     * @param driver Driver de conexión con la Base de datos.
     * @param protocolo Protocolo de la conexión de la BD, p.e. mysql, oracle:thin:@ (tomarlo de el archivo de Drivers.properties)
     * @param usuarioBD usuario de la base de datos
     * @param contrasenaBD contrasena de este usuario
     */
    public ConsultoraDeBD(String driver, String protocolo, String usuarioBD, String contrasenaBD, String baseDeDatos){
        this.driver=driver;
        protocoloDBMS=protocolo;
        usuario=usuarioBD;
        contrasena=contrasenaBD.toCharArray();
        this.baseDeDatos=baseDeDatos;
    }

    
    @Override
    public boolean abrir(String ruta) {

        if( conexion !=  null ){
            try {
                if (!conexion.isClosed()) {
                    return true;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(ConsultoraDeBD.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

        try {
            conexion = DriverBD.conectar(ruta, usuario, String.valueOf(contrasena), driver, protocoloDBMS);
            DriverBD.seleccionarBD(conexion, baseDeDatos);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String[] consultarColumnasDisponibles() {
        String[] tablas;
        String[] columnas;
        LinkedList<String> tablasYcolumnas=new LinkedList<String>();
        try {
            tablas = DriverBD.consultarTablas(conexion);

            for (int i = 0; i < tablas.length; i++) {
                try {
                    columnas = DriverBD.consultarColumnas(conexion, tablas[i]);
                    for (int j = 0; j < columnas.length; j++) {
                        String columna = tablas[i]+"."+columnas[j];
                        tablasYcolumnas.add(columna);
                    }
                } catch (SQLException ex) {
                    //Ocurre cuando no se tienen permisos para acceder a la tabla seleccionada
                }
            }
        } catch (SQLException ex) {
            return null;
        }

        return tablasYcolumnas.toArray(new String[0]);
    }

    /**
     * Convierte las instrucciones enviadas a una consulta SQL para obtener los datos deseados.
     * @param columnas Las columnas a consultar en el formato "tabla.columna",
     * la ultima cadena no debe corresponder a una columna sino a una condicion
     * en SQL. Si solo se pasa la condición, se asume que esta es un query SQL completo, p.e. "SELECT * FROM tabla".
     * @return Datos extraidos de la base de datos relacionados por filas, o <b>null</b> si columnas.lenght es igual a 0.
     */
    @Override
    public String[][] consultarDatos(String[] columnas) {
        String consultaSQLcolumnas = "SELECT ";
        String consultaSQLtablas = " FROM ";
        String consultaSQL="";
        String nombreTabla="";
        String where="";
        LinkedList<String> tablasAgregadas=new LinkedList<String>();
        int nCol = columnas.length-1;
        if(nCol >= 1){

            for(int i = 0; i <= nCol; i++){
                if ( ! columnas[i].contains("WHERE ") ) {
                    consultaSQLcolumnas += columnas[i] + ",";
                    System.out.println(columnas[i]);
                    String nuevoNombreTabla = columnas[i].split("\\.")[0];
//                if( ! nuevoNombreTabla.equalsIgnoreCase(nombreTabla) ){
                    if (!tablasAgregadas.contains(nuevoNombreTabla)) {
                        nombreTabla = nuevoNombreTabla;
                        tablasAgregadas.add(nombreTabla);
                        consultaSQLtablas += nombreTabla + ",";
                    }
                } else {
                    where=columnas[i];
                }
            }
            //eliminar la ultima "," de consultaSQLtablas y consultaSQLcolumnas
            consultaSQLtablas = consultaSQLtablas.substring(0, consultaSQLtablas.length()-1);
            consultaSQLcolumnas = consultaSQLcolumnas.substring(0, consultaSQLcolumnas.length()-1);
            /*if( columnas[ columnas.length-1 ].equals("")){
            consultaSQL=consultaSQLcolumnas+consultaSQLtablas;
            }else*/{
                consultaSQL=consultaSQLcolumnas+consultaSQLtablas+" "+where;
            }

        }else if(nCol == 0){
            consultaSQL = columnas[0];
        }else{
            return null;
        }
        return DriverBD.consultar(conexion, consultaSQL);
        
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getProtocoloDBMS() {
        return protocoloDBMS;
    }

    public void setProtocoloDBMS(String protocoloDBMS) {
        this.protocoloDBMS = protocoloDBMS;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public String getContrasenia() {
        return String.valueOf(contrasena);
    }

    public void setContrasenia(String contrasenia) {
        this.contrasena = contrasenia.toCharArray();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getBaseDeDatos() {
        return baseDeDatos;
    }

    public void setBaseDeDatos(String BaseDeDatos) {
        this.baseDeDatos = BaseDeDatos;
    }

    

    public static void main(String[] args) {
        //"localhost", "test","tset", "com.mysql.jdbc.Driver", "mysql://"
        ConsultoraDeBD c = new ConsultoraDeBD("com.mysql.jdbc.Driver", "mysql://", "test", "tset","sakila");
//        String[] col = {"select staff.first_name,store.last_update from staff,store where staff_id=manager_staff_id"};
        String[] col = {"staff.first_name AS uno","store.last_update AS dos","staff_id=manager_staff_id"};
        c.abrir("localhost");
        String[][] s = c.consultarDatos(col);

        for (int i = 0; i < s.length; i++) {
            String[] strings = s[i];
            for (int j = 0; j < strings.length; j++) {
                String string = strings[j];
                System.out.print(string+"  ");
            }
            System.out.println("");
        }
    }
    
}
