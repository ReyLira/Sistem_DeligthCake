package controller;

import dao.ProveedorImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import lombok.Data;
import model.Proveedor;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;
import services.ReporteS;

@Data

@Named(value = "proveedorC")
@SessionScoped
public class ProveedorC implements Serializable {

    private Proveedor proveedor;
    private ProveedorImpl dao;
    private List<Proveedor> lstProveedor;
    private int tipo = 1;
    private PDFOptions pdf;
    private ExcelOptions xls;

    public ProveedorC() {
        proveedor = new Proveedor();
        dao = new ProveedorImpl();
        lstProveedor = new ArrayList<>();
        opcionesPersonalizacion();
    }

    public void registrar() throws Exception {
        try {
            dao.guardar(proveedor);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado", "Registrado con éxito"));
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en registar ProveedorC/registar: ", e.getMessage());
        } finally {
        }
    }

    public void modificar() throws Exception {
        try {
            dao.modificar(proveedor);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado", "Registrado con éxito"));
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en modificar ProveedorC/modificar: ", e.getMessage());
        } finally {
        }
    }

    public void eliminar() throws Exception {
        try {
            dao.eliminar(proveedor);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Eliminado", "Eliminado con éxito"));
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en eliminar ProveedorC/eliminar: ", e.getMessage());
        } finally {
        }
    }

    public void restaurar() throws Exception {
        try {
            dao.restaurar(proveedor);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Restaurado", "Restaurado con éxito"));
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en eliminar ProveedorC/restaurar: ", e.getMessage());
        } finally {
        }
    }

    public void listar() {
        try {
            lstProveedor= dao.listarTodos(tipo);
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en restaurar ProveedorC/modificar: ", e.getMessage());
        } finally {
        }
    }
    
    //REPORTE VISTA PREVIA
    public void reporteProveedor() throws Exception {
        try {
            ReporteS reporte = new ReporteS();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("reports/Proveedor.jasper");
            reporte.ReportePdf(root);
            FacesContext.getCurrentInstance().responseComplete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "REPORTE GENERADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "AL GENERAR REPORTE"));
            System.out.println("Error en reporteProveedor_C " + e.getMessage());
        }
    }

    public void limpiar() {
        proveedor = new Proveedor();
    }
    
    private void opcionesPersonalizacion() {
        xls = new ExcelOptions();
        xls.setFacetBgColor("#19C7FF");
        xls.setFacetFontSize("10");
        xls.setFacetFontColor("#FFFFFF");
        xls.setFacetFontStyle("BOLD");
        xls.setCellFontColor("#000000");
        xls.setCellFontSize("8");
        xls.setFontName("Verdana");

        pdf = new PDFOptions();
        pdf.setFacetBgColor("#19C7FF");
        pdf.setFacetFontColor("#000000");
        pdf.setFacetFontStyle("BOLD");
        pdf.setCellFontSize("12");
        pdf.setFontName("Courier");
        pdf.setOrientation(PDFOrientationType.LANDSCAPE);
    }

    @PostConstruct
    public void construir() {
        listar();
    }
}
