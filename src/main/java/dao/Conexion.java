package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Data;

@Data

public class Conexion {

    private static Connection cnx = null;

    public static Connection conectar() {
        try {
            String user = "Delightcake";
            String pwd = "12345";
            String driver = "oracle.jdbc.driver.OracleDriver";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
//            String user = "sa";
//            String pwd = "Esteban123xx";
//            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//            String url = "jdbc:sqlserver://localhost:1433;databaseName=dbDELIGHTCAKE";
            Class.forName(driver).newInstance();
            cnx = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, "Error de Conexión/Conectar ", e.getMessage());
        }
        return cnx;
    }

    public void cerrar() {
        try {
            if (cnx != null) {
                cnx.close();
            }
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "Error en Cerrar ", e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        conectar();
        try {
            if (cnx != null) {
                Logger.getGlobal().log(Level.INFO, "CONEXIÓN EXITOSA");
                JOptionPane.showMessageDialog(null, "CONEXIÓN EXITOSA", "CORRECTO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Logger.getGlobal().log(Level.WARNING, "SIN CONEXIÓN REVISA");
                JOptionPane.showMessageDialog(null, "SIN CONEXIÓN REVISA", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, "Error en ", e.getMessage());
        }
    }

}
