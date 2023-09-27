package dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Producto;

public class ProductoImpl extends Conexion implements ICRUD<Producto> {

    @Override
    public void guardar(Producto producto) throws Exception {
        try {
            String sql = "insert into producto" + " (NOMPRO,PREPRO,DESPRO,ESTPRO,STOPRO)"
                    + " values (?,?,?,?,?) ";
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ps.setString(1, producto.getNOMPRO());
            ps.setDouble(2, producto.getPREPRO());
            ps.setString(3, producto.getDESPRO());
            ps.setString(4, "A");
            ps.setInt(5, producto.getSTOPRO());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en ProductoImpl/registrar: " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void modificar(Producto producto) throws Exception {
        try {
            String sql = "update producto set NOMPRO=?,PREPRO=?,DESPRO=?,ESTPRO=?,STOPRO=? where IDPRO=?";
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ps.setString(1, producto.getNOMPRO());
            ps.setDouble(2, producto.getPREPRO());
            ps.setString(3, producto.getDESPRO());
            ps.setString(4, "A");
            ps.setInt(5, producto.getSTOPRO());
            ps.setInt(6, producto.getIDPRO());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en ProductoImpl/modificar: " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void eliminar(Producto producto) throws Exception {
        try {
            String sql = "update Producto set ESTPRO='I' where IDPRO=?";
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ps.setInt(1, producto.getIDPRO());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en ProductoImpl/eliminar: " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }
    
    @Override
    public void restaurar(Producto producto) throws Exception {
        try {
            String sql = "update Producto set ESTPRO='A' where IDPRO=?";
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ps.setInt(1, producto.getIDPRO());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en ProductoImpl/eliminar: " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public List<Producto> listarTodos(int tipo) throws Exception {
        List<Producto> lista = null ;
        String sql = "";
        switch (tipo) {
            case 1:
                sql = "select * from vPRODUCTO where ESTPRO='A'";
                break;
            case 2:
                sql = "select * from vPRODUCTO where ESTPRO='I'";
                break;
            case 3:
                sql = "select * from vPRODUCTO";
                break;
            default:
                sql = "select * from vPRODUCTO where ESTPRO='A'";
                break;
        }
        try {
            lista = new ArrayList();
            Statement st = dao.Conexion.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Producto pro = new Producto();
                pro.setIDPRO(rs.getInt("IDPRO"));
                pro.setNOMPRO(rs.getString("NOMPRO"));
                pro.setPREPRO(rs.getDouble("PREPRO"));
                pro.setDESPRO(rs.getString("DESPRO"));
                pro.setSTOPRO(rs.getInt("STOPRO"));
                lista.add(pro);
            }
        } catch (Exception e) {
            System.out.println("Error al listar todos" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return lista;
    }
    
    public List<Producto> Graficos() throws Exception {
        List<Producto> lista = null ;
        String sql = "select * from vGRAFICOSPRO WHERE ROWNUM < 6";
        try {
            lista = new ArrayList();
            Statement st = dao.Conexion.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Producto pro = new Producto();
                pro.setNOMPRO(rs.getString("Nombre"));
                pro.setSTOPRO(rs.getInt("Cantidad"));
                lista.add(pro);
            }
        } catch (Exception e) {
            System.out.println("Error al listar todos" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return lista;
    }
    
    public List<Producto> listarTodosIMG() throws Exception {
        List<Producto> lista = null ;
        String sql = "SELECT * FROM vPRODUCTOIMG";
        try {
            lista = new ArrayList();
            Statement st = dao.Conexion.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Producto pro = new Producto();
                pro.setIDPRO(rs.getInt("IDPRO"));
                pro.setNOMPRO(rs.getString("NOMPRO"));
                pro.setPREPRO(rs.getDouble("PREPRO"));
                pro.setDESPRO(rs.getString("DESPRO"));
                pro.setSTOPRO(rs.getInt("STOPRO"));
                pro.setIMGPRO(rs.getString("IMGPRO"));
                lista.add(pro);
            }
        } catch (Exception e) {
            System.out.println("Error al listar todos" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return lista;
    }
}


