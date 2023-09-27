package dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Proveedor;

public class ProveedorImpl extends Conexion implements ICRUD<Proveedor> {

    @Override
    public void guardar(Proveedor proveedor) throws Exception {
        try {
            String sql = "insert into PROVEEDOR (RAZPROV, DIRPROV, CELPROV, RUCPROV, EMAPROV, CODUBI, ESTPROV) values (?,?,?,?,?,?,?)";
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ps.setString(1, proveedor.getRAZPROV());
            ps.setString(2, proveedor.getDIRPROV());
            ps.setString(3, proveedor.getCELPROV());
            ps.setString(4, proveedor.getRUCPROV());
            ps.setString(5, proveedor.getEMAPROV());
            ps.setString(6, proveedor.getCODUBI());
            ps.setString(7, "A");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en ProveedorImpl/registrar:  ", e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void modificar(Proveedor proveedor) throws Exception {
        try {
            String sql = "update PROVEEDOR set RAZPROV=?, DIRPROV=?, CELPROV=?, RUCPROV=?, EMAPROV=?, CODUBI=?, ESTPROV=? where IDPROV=? ";
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ps.setString(1, proveedor.getRAZPROV());
            ps.setString(2, proveedor.getDIRPROV());
            ps.setString(3, proveedor.getCELPROV());
            ps.setString(4, proveedor.getRUCPROV());
            ps.setString(5, proveedor.getEMAPROV());
            ps.setString(6, proveedor.getCODUBI());
            ps.setString(7, "A");
            ps.setInt(8, proveedor.getIDPROV());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en ProveedorImpl/modificar: ", e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void eliminar(Proveedor proveedor) throws Exception {
        try {
            String sql = "update PROVEEDOR set ESTPROV='I' where IDPROV=? ";
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ps.setInt(1, proveedor.getIDPROV());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en ProveedorImpl/Cambiar Estado: ", e.getMessage());
        } finally {
            this.cerrar();
        }
    }
    
    @Override
    public void restaurar(Proveedor proveedor) throws Exception {
        try {
            String sql = "update PROVEEDOR set ESTPROV='A' where IDPROV=? ";
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ps.setInt(1, proveedor.getIDPROV());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en ProveedorImpl/Cambiar Estado: ", e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public List listarTodos(int tipo) throws Exception {
        List<Proveedor> lista = null;
        String sql = "";
        switch (tipo) {
            case 1:
                sql = "select * from vPROVEEDOR where ESTPROV='A'";
                break;
            case 2:
                sql = "select * from vPROVEEDOR where ESTPROV='I'";
                break;
            case 3:
                sql = "select * from vPROVEEDOR";
                break;
            default:
                sql = "select * from vPROVEEDOR where ESTPROV='A'";
                break;
        }
        try {
            lista = new ArrayList();
            Statement st = dao.Conexion.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setIDPROV(rs.getInt("IDPROV"));
                proveedor.setRAZPROV(rs.getString("RAZPROV"));
                proveedor.setDIRPROV(rs.getString("DIRPROV"));
                proveedor.setCELPROV(rs.getString("CELPROV"));
                proveedor.setRUCPROV(rs.getString("RUCPROV"));
                proveedor.setEMAPROV(rs.getString("EMAPROV"));
                proveedor.setCODUBI(rs.getString("CODUBI"));
                proveedor.setDISUBI(rs.getString("DISUBI"));
                proveedor.setPROUBI(rs.getString("PROUBI"));
                proveedor.setDEPUBI(rs.getString("DEPUBI"));
                lista.add(proveedor);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error al listar todos: ", e.getMessage());
        } finally {
            this.cerrar();
        }
        return lista;
    }

}
