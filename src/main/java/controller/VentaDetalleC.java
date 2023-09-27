package controller;

import dao.VentaDetalleImpl;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import model.Venta;
import lombok.Data;
import model.Producto;
import model.VentaDetalle;
import services.ReporteS;

@Data

@Named(value = "VentaDetalleC")
@SessionScoped
public class VentaDetalleC implements Serializable {

    private Venta venta;
    private VentaDetalle ventadetalle;
    private Producto producto;
    private VentaDetalleImpl dao;
    private List<Venta> listaVenta;
    private List<VentaDetalle> listadoVentaDetalle;
    private List<Producto> listadoProducto;

    public VentaDetalleC() {
        venta = new Venta();
        producto = new Producto();
        ventadetalle = new VentaDetalle();
        dao = new VentaDetalleImpl();
        listaVenta = new ArrayList<>();
        listadoVentaDetalle = new ArrayList<>();
        listadoProducto = new ArrayList<>();
    }

    public void validarStock() throws Exception {
        if (ventadetalle.getCANVENDET() <= ventadetalle.getSTOPRO()) {
            agregarFila();
            Logger.getGlobal().log(Level.INFO, "Correcto Agregado");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "La Cantidad es Mayor al Stock o Stock Agotado"));
            Logger.getGlobal().log(Level.WARNING, "Que pena no se pudo agregar");
        }
    }

    public void agregarFila() {
        try {
            VentaDetalle ventadet = dao.agregarFila(ventadetalle.getProducto().getIDPRO());
            ventadet.setIDPRO(this.ventadetalle.getProducto().getIDPRO());
            ventadet.setCANVENDET(this.ventadetalle.getCANVENDET());
            ventadet.setPREPRO(ventadet.getProducto().getPREPRO());
            ventadet.setSUBVENDET(ventadet.getProducto().getPREPRO() * this.ventadetalle.getCANVENDET());
            ventadet.setNOMPRO(ventadet.getProducto().getNOMPRO());
            ventadet.setDESPRO(ventadet.getProducto().getDESPRO());
            ventadet.setSTOPRO(ventadet.getProducto().getSTOPRO());
            this.listadoVentaDetalle.add(ventadet);
            ventadetalle = new VentaDetalle();
            for (VentaDetalle ventaDetalle : listadoVentaDetalle) {
                System.out.println(ventaDetalle);
            }
            sumar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Producto Agregado"));
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en agregarFilaDAO ", e.getMessage());
            e.printStackTrace();
        } finally {

        }
    }
    
    public void agregar(String codigo) {
        try {
            System.out.println("producto2 " + codigo);
            producto.setNOMPRO(codigo);
            dao.mostrarDatos(producto);

        } catch (Exception e) {
            System.out.println("error en buscar ProductoC " + e.getMessage());
        }
    }
        

    public void obtenerPrecioStock() throws Exception {
        dao.obtenerPrecioStock(ventadetalle);
    }

    public void registrarVenta() {
        try {
            dao.registrarVenta(venta);
            int idVenta = dao.ultimoIdVenta();
            dao.registrarDetalle(listadoVentaDetalle, idVenta);
            listadoVentaDetalle.clear();
            listarVenta();
            limpiar();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en registrarVentaC ", e.getMessage());
            e.printStackTrace();
        } finally {

        }
    }

    public void eliminarFila(VentaDetalle ventadetalle) {
        try {
            listadoVentaDetalle.remove(ventadetalle);
            sumar();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en eliminarFilaDAO ", e.getMessage());
            e.printStackTrace();
        } finally {

        }
    }

    public void reporteVenta(int IDVEN) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        ReporteS reporte = new ReporteS();
        FacesContext facescontext = FacesContext.getCurrentInstance();
        ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
        String root = servletcontext.getRealPath("reports/DetalleVenta.jasper");
        int numeroinformesocial = (IDVEN);
        reporte.ReportePdfVentas(root, numeroinformesocial);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void limpiar() {
        this.ventadetalle = new VentaDetalle();
        this.venta = new Venta();
    }

    public void anular() {
        limpiar();
        listadoVentaDetalle.clear();
    }

    //LISTA DE VENTAS
    public void listarVenta() {
        try {
            listaVenta = dao.listarVenta();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en ListarC ", e.getMessage());
            e.printStackTrace();
        } finally {

        }
    }

    public void sumar() {
        double precioTotal = 0.0;
        for (VentaDetalle ventaDetalle : listadoVentaDetalle) {
            precioTotal += ventaDetalle.getSUBVENDET();
        }
        System.out.println(precioTotal);
        venta.setTOTVEN(precioTotal);
    }

    @PostConstruct
    public void construir() {
        listarVenta();
    }

}
