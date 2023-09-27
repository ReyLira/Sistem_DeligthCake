package dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.Compra;
import model.Proveedor;
import model.CompraDetalle;
import model.Empleado;
import model.Producto;

public class CompraDetalleImpl extends Conexion {

    // Formateo para el campo fecha 
    DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy"); // ("yyyy/MM/dd")  ("dd/MM/yyyy")

    // Metodo para para agregar en la tabla temporal venta
    public CompraDetalle agregarFila(int idpro) throws Exception {
        CompraDetalle compradetalle = null;
        String sql = "SELECT * FROM PRODUCTO WHERE IDPRO = " + idpro;
        try {
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                compradetalle = new CompraDetalle();
                Producto producto = new Producto();
                compradetalle.setIDPRO(rs.getInt("IDPRO"));
                producto.setNOMPRO(rs.getString("NOMPRO"));
                producto.setPREPRO(rs.getDouble("PREPRO"));
                producto.setDESPRO(rs.getString("DESPRO"));
                producto.setSTOPRO(rs.getInt("STOPRO"));
                compradetalle.setProducto(producto);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error en agregarFila_D " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
        }
        return compradetalle;
    }

    // Metodo para obtener el Precio y el Stock del Producto
    public void obtenerPrecioStock(CompraDetalle compradetalle) throws Exception {
        String sql = "SELECT * FROM PRODUCTO WHERE IDPRO = ?";
        try {
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ps.setInt(1, compradetalle.getProducto().getIDPRO());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                compradetalle.setPREPRO(rs.getDouble("PREPRO"));
                compradetalle.setSTOPRO(rs.getInt("STOPRO"));
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Error en obtenerPrecioStock " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
        }
    }

    // Metodo para registrar el la venta detalle 
    public void registrarDetalle(List<CompraDetalle> listaCompraDetalle, int idCompra) throws Exception {
        String sql = "INSERT INTO COMPRA_DETALLE (CANCOMDET,IDCOM,IDPRO,SUBCOMDET) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            for (CompraDetalle compradetalle : listaCompraDetalle) {
                ps.setInt(1, compradetalle.getCANCOMDET());
                ps.setInt(2, idCompra);
                ps.setInt(3, compradetalle.getIDPRO());
                ps.setDouble(4, compradetalle.getSUBCOMDET());
                ps.executeUpdate();
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error en registroMultipleDAO " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
        }
    }

    // Metodo para registar la venta 
    public void registrarCompra(Compra compra) throws Exception {
        String sql = "INSERT INTO COMPRA (FECCOM,IDPROV,TOTCOM,IDEMP) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ps.setString(1, fecha.format(compra.getFECCOM()));
            ps.setInt(2, compra.getProveedor().getIDPROV());
            ps.setDouble(3, compra.getTOTCOM());
            ps.setInt(4, compra.getEmpleado().getIDEMP());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error en registrar_D COMPRA " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
        }
    }

    // Metodo para obtener el ultimo id de la venta
    public int ultimoIdCompra() {
        String sql = "SELECT MAX(C.IDCOM) AS IDCOM FROM COMPRA C";
        try {
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("IDCOM");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error en obtenerUltimoIdDAO" + e.getMessage());
        }
        return -1;
    }

    // Metodo listar para la tabla compra usando una vista
    public List<Compra> listarCompra() throws Exception {
        List<Compra> listado = new ArrayList<>();
        String sql = "SELECT * FROM vCOMPRA ORDER BY IDCOM DESC";
        try {
            Statement st = dao.Conexion.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Compra compra = new Compra();
                compra.setIDCOM(rs.getInt("IDCOM"));
                compra.setFECCOM(rs.getDate("FECCOM"));
                Proveedor proveedor = new Proveedor();
                proveedor.setIDPROV(rs.getInt("IDPROV"));
                proveedor.setRUCPROV(rs.getString("RUCPROV"));
                proveedor.setRAZPROV(rs.getString("RAZPROV"));
                Empleado empleado = new Empleado();
                empleado.setIDEMP(rs.getInt("IDEMP"));
                empleado.setNOMEMP(rs.getString("NOMEMP"));
                compra.setTOTCOM(rs.getDouble("TOTCOM"));
                compra.setProveedor(proveedor);
                compra.setEmpleado(empleado);
                listado.add(compra);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println("Error en el listadoDaocompra " + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listado;
    }

    public List<CompraDetalle> verDetalle(int IDCOM) throws Exception {
        this.conectar();
        List<CompraDetalle> detalle = new ArrayList();
        CompraDetalle det;
        Compra com;
        String sql = "SELECT * FROM vCOMPRA WHERE IDCOM='" + IDCOM + "'";
        try {
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                det = new CompraDetalle();
                com = new Compra();
                com.setIDCOM(rs.getInt("IDCOM"));
                det.getProducto().setNOMPRO(rs.getString("NOMPRO"));
                det.setCANCOMDET(rs.getInt("CANCOMDET"));
                det.getProducto().setPREPRO(rs.getDouble("PREPRO"));
                detalle.add(det);
            }
        } catch (Exception e) {
            System.out.println("ERROR EN LISTAR:" + e);
        } finally {
            this.cerrar();
        }
        return detalle;
    }
}
