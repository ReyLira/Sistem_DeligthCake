package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;
import model.Empleado;

public class UsuarioD extends Conexion {

    public static String rol = "";
    public static Boolean logueo = false;

    public Cliente loginCliente(String usuario, String password) throws Exception {
        Cliente user = new Cliente();
//        String sql = "select DNICLI, PWDCLI from CLIENTE where DNICLI=? and PWDCLI=? ";
        String sql = "select * from CLIENTE where DNICLI=? and PWDCLI=? AND ESTCLI='A' ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                logueo = true;
                user.setDNICLI(usuario);
                user.setPWDCLI(password);
                user.setNOMCLI(rs.getString("NOMCLI"));
                user.setAPEPATCLI(rs.getString("APEPATCLI"));
                user.setAPEMATCLI(rs.getString("APEMATCLI"));
                user.setEMACLI(rs.getString("EMACLI"));
            } else {
                logueo = false;
            }
            ps.close();
            rs.close();
            return user;
        } catch (Exception e) {
            System.out.println("Errorr en login_D_Cliente " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Empleado loginRolEmpleado(String usuario, String password) throws Exception {
        Empleado user = new Empleado();
//        String sql = "SELECT DNIEMP, PWDEMP, ROLEMP FROM EMPLEADO WHERE DNIEMP=? AND PWDEMP=? ";
        String sql = "SELECT * FROM EMPLEADO WHERE DNIEMP=? AND PWDEMP=? AND ESTEMP='A'";
        try {
            PreparedStatement ps = (PreparedStatement) this.conectar().prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rol = rs.getString("ROLEMP");
                logueo = true;
                user.setNOMEMP(rs.getString("NOMEMP"));
                user.setAPEPATEMP(rs.getString("APEPATEMP"));
                user.setAPEMATEMP(rs.getString("APEMATEMP"));
                user.setEMAEMP(rs.getString("EMAEMP"));
            } else {
                logueo = false;
            }
            ps.close();
            rs.close();
            return user;
        } catch (Exception e) {
            System.out.println("Errorr en loginRol_D " + e.getMessage());
            Logger.getGlobal().log(Level.WARNING, "Errorr en loginRol_D {0} ", e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Empleado loginEmpleado(Empleado empleado) throws Exception {
        Empleado user = new Empleado();
        String sql = "select DNIEMP, PWDEMP from EMPLEADO where DNIEMP=? and PWDEMP=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, empleado.getDNIEMP());
            ps.setString(2, empleado.getPWDEMP());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                logueo = true;
                user.setDNIEMP(empleado.getDNIEMP());
                user.setPWDEMP(empleado.getPWDEMP());
            } else {
                logueo = false;
            }
            ps.close();
            rs.close();
            return user;
        } catch (Exception e) {
            System.out.println("Errorr en login_D_Empleado " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

//    public Cliente seguridad(Cliente cliente) throws Exception {
//        Cliente user = new Cliente();
//        String sql = "select DNICLI, PWDCLI from CLIENTE where DNICLI=? and PWDCLI=? ";
//        try {
//            PreparedStatement ps = this.conectar().prepareStatement(sql);
//            ps.setString(1, cliente.getDNICLI());
//            ps.setString(2, cliente.getPWDCLI());
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                logueo = true;
//                user.setDNICLI(cliente.getDNICLI());
//                user.setPWDCLI(cliente.getPWDCLI());
//            } else {
//                logueo = false;
//            }
//            ps.close();
//            rs.close();
//            return user;
//        } catch (Exception e) {
//            System.out.println("Errorr en login_D_Cliente " + e.getMessage());
//            e.printStackTrace();
//            throw e;
//        }
//    }
}
