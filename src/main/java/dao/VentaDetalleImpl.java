package dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.Cliente;
import model.Venta;
import model.Producto;
import model.VentaDetalle;

public class VentaDetalleImpl extends Conexion {

    // Formateo para el campo fecha 
    DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy"); // ("yyyy/MM/dd")  ("dd/MM/yyyy")

    // Metodo para para agregar en la tabla temporal venta
    public VentaDetalle agregarFila(int idpro) throws Exception {
        VentaDetalle ventadetalle = null;
        String sql = "SELECT * FROM PRODUCTO WHERE IDPRO = " + idpro;
        try {
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ventadetalle = new VentaDetalle();
                Producto producto = new Producto();
                ventadetalle.setIDPRO(rs.getInt("IDPRO"));
                producto.setNOMPRO(rs.getString("NOMPRO"));
                producto.setPREPRO(rs.getDouble("PREPRO"));
                producto.setDESPRO(rs.getString("DESPRO"));
                producto.setSTOPRO(rs.getInt("STOPRO"));
                ventadetalle.setProducto(producto);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error en agregarFila_D " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
        }
        return ventadetalle;
    }

    // Metodo para obtener el Precio y el Stock del Producto
    public void obtenerPrecioStock(VentaDetalle ventadetalle) throws Exception {
        String sql = "SELECT * FROM PRODUCTO WHERE IDPRO = ?";
        try {
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ps.setInt(1, ventadetalle.getProducto().getIDPRO());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ventadetalle.setPREPRO(rs.getDouble("PREPRO"));
                ventadetalle.setSTOPRO(rs.getInt("STOPRO"));
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
    public void registrarDetalle(List<VentaDetalle> listaVentaDetalle, int idVenta) throws Exception {
        String sql = "INSERT INTO VENTA_DETALLE (CANVENDET,IDVEN,IDPRO,SUBVENDET) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            for (VentaDetalle ventadetalle : listaVentaDetalle) {
                ps.setInt(1, ventadetalle.getCANVENDET());
                ps.setInt(2, idVenta);
                ps.setInt(3, ventadetalle.getIDPRO());
                ps.setDouble(4, ventadetalle.getSUBVENDET());
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
    public void registrarVenta(Venta venta) throws Exception {
        String sql = "INSERT INTO VENTA (FECVEN,IDCLI,IDEMP,TOTVEN) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ps.setString(1, fecha.format(venta.getFECVEN()));
            ps.setInt(2, venta.getCliente().getIDCLI());
            ps.setInt(3, venta.getEmpleado().getIDEMP());
            ps.setDouble(4, venta.getTOTVEN());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error en registrar_D VENTA " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
        }
    }

   // Metodo para obtener el ultimo id de la venta
    public int ultimoIdVenta() {
        String sql = "SELECT MAX(V.IDVEN) AS IDVEN FROM VENTA V";
        try {
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("IDVEN");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error en obtenerUltimoIdDAO" + e.getMessage());
        }
        return -1;
    }
    
    // Metodo listar para la tabla venta usando una vista
    public List<Venta> listarVenta() throws Exception {
        List<Venta> listado = new ArrayList<>();
        String sql = "SELECT * FROM vVENTA ORDER BY IDVEN DESC";
        try {
            Statement st = dao.Conexion.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Venta venta = new Venta();
                venta.setIDVEN(rs.getInt("IDVEN"));
                venta.setFECVEN(rs.getDate("FECVEN"));
                Cliente cliente = new Cliente();
                cliente.setIDCLI(rs.getInt("IDCLI"));
                cliente.setNOMCLI(rs.getString("NOMCLI"));
                cliente.setDNICLI(rs.getString("DNICLI"));
                venta.setTOTVEN(rs.getDouble("TOTVEN"));
                venta.setCliente(cliente);
                listado.add(venta);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println("Error en el listadoDaoVenta " + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listado;
    }

    public List<VentaDetalle> verDetalle(int IDVEN) throws Exception {
        this.conectar();
        List<VentaDetalle> detalle = new ArrayList();
        VentaDetalle det;
        Venta ven;
        String sql = "SELECT * FROM vVENTA WHERE IDVEN='" + IDVEN +"'";
        try {
            PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                det = new VentaDetalle();
                ven = new Venta();
                ven.setIDVEN(rs.getInt("IDVEN"));
                det.getProducto().setNOMPRO(rs.getString("NOMPRO"));
                det.setCANVENDET(rs.getInt("CANVENDET"));
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
    
    public void mostrarDatos(Producto pro) throws Exception {
        try {
            String sql = "Select * from PRODUCTO where NOMPRO =?";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, pro.getNOMPRO());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pro.setIDPRO(rs.getInt("IDPRO"));
                pro.setNOMPRO(rs.getString("NOMPRO"));
                pro.setPREPRO(rs.getDouble("PREPRO"));
                pro.setSTOPRO(rs.getInt("STOPRO"));
                pro.setDESPRO(rs.getString("DESPRO"));
                pro.setIMGPRO(rs.getString("IMGPRO"));
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error en mostrar datos" + e.getMessage());
        }
    }
}
