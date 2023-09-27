package controller;

import dao.CompraDetalleImpl;
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
import model.Proveedor;
import lombok.Data;
import model.Compra;
import model.CompraDetalle;
import model.Producto;
import services.ReporteS;

@Data

@Named(value = "CompraDetalleC")
@SessionScoped
public class CompraDetalleC implements Serializable {

    private Compra compra;
    private CompraDetalle compraDetalle;
    private Proveedor proveedor;
    private Producto producto;
    private CompraDetalleImpl dao;
    private List<Compra> listaCompra;
    private List<CompraDetalle> listadoCompraDetalle;
    private List<Producto> listadoProducto;

    public CompraDetalleC() {
        compra = new Compra();
        proveedor = new Proveedor();
        producto = new Producto();
        compraDetalle = new CompraDetalle();
        dao = new CompraDetalleImpl();
        listaCompra = new ArrayList<>();
        listadoCompraDetalle = new ArrayList<>();
        listadoProducto = new ArrayList<>();
    }

    public void agregarFila() {
        try {
            CompraDetalle compradet = dao.agregarFila(compraDetalle.getProducto().getIDPRO());
            compradet.setIDPRO(this.compraDetalle.getProducto().getIDPRO());
            compradet.setCANCOMDET(this.compraDetalle.getCANCOMDET());
            compradet.setPREPRO(compradet.getProducto().getPREPRO());
            compradet.setSUBCOMDET(compradet.getProducto().getPREPRO() * this.compraDetalle.getCANCOMDET());
            compradet.setNOMPRO(compradet.getProducto().getNOMPRO());
            compradet.setDESPRO(compradet.getProducto().getDESPRO());
            compradet.setSTOPRO(compradet.getProducto().getSTOPRO());
            this.listadoCompraDetalle.add(compradet);
            compraDetalle = new CompraDetalle();
            for (CompraDetalle compradetalle : listadoCompraDetalle) {
                System.out.println(compradetalle);
            }
            sumar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Producto Agregado"));
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en agregarFilaDAO ", e.getMessage());
            e.printStackTrace();
        } finally {

        }
    }

    public void obtenerPrecioStock() throws Exception {
        dao.obtenerPrecioStock(compraDetalle);
    }

    public void registrarCompra() {
        try {
            dao.registrarCompra(compra);
            int idCompra = dao.ultimoIdCompra();
            dao.registrarDetalle(listadoCompraDetalle, idCompra);
            listadoCompraDetalle.clear();
            listarCompra();
            limpiar();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en registrarVentaC ", e.getMessage());
            e.printStackTrace();
        } finally {

        }
    }

    public void eliminarFila(CompraDetalle compradetalle) {
        try {
            listadoCompraDetalle.remove(compradetalle);
            sumar();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en eliminarFilaDAO ", e.getMessage());
            e.printStackTrace();
        } finally {

        }
    }

    public void limpiar() {
        this.compraDetalle = new CompraDetalle();
        this.compra = new Compra();
    }

    public void anular() {
        limpiar();
        listadoCompraDetalle.clear();
    }

    public void reporteCompra(int IDCOM) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        ReporteS reporte = new ReporteS();
        FacesContext facescontext = FacesContext.getCurrentInstance();
        ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
        String root = servletcontext.getRealPath("reports/CompraDetalle.jasper");
        int numeroinformesocial = (IDCOM);
        reporte.ReportePdfCompras(root, numeroinformesocial);
        FacesContext.getCurrentInstance().responseComplete();
    } 

    //LISTA DE VENTAS
    public void listarCompra() {
        try {
            listaCompra = dao.listarCompra();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en ListarC ", e.getMessage());
            e.printStackTrace();
        } finally {

        }
    }

    public void sumar() {
        double precioTotal = 0.0;
        for (CompraDetalle compradetalle : listadoCompraDetalle) {
            precioTotal += compradetalle.getSUBCOMDET();
        }
        System.out.println(precioTotal);
        compra.setTOTCOM(precioTotal);
    }

    @PostConstruct
    public void construir() {
        listarCompra();
    }
}
