package controller;

import com.google.gson.JsonSyntaxException;
import dao.ClienteImpl;
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
import model.Cliente;
import lombok.Data;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;
import services.EmailS;
import services.ReniecS;
import services.ReporteS;
import services.SmsS;

@Data

@Named(value = "ClienteC")
@SessionScoped
public class ClienteC implements Serializable {

    private Cliente cliente;
    private ClienteImpl dao;
    private List<Cliente> lstCliente;
    private int tipo = 1;
    private PDFOptions pdf;
    private ExcelOptions xls;

    private ReniecS reniecs;
    private Boolean disabled = false;

    public ClienteC() {
        cliente = new Cliente();
        dao = new ClienteImpl();
        lstCliente = new ArrayList<>();
        opcionesPersonalizacion();
        reniecs = new ReniecS();
    }

    public void buscarPorDNICliente() throws Exception {
        try {
            reniecs.buscarPorDNICliente(cliente);
            if (reniecs.disabled == true) {
                this.disabled = true;
            } else {
                this.disabled = false;
                limpiar();
            }
        } catch (Exception e) {
            System.out.println("Error en buscarPorDNI_C " + e.getMessage());
        }
    }

    public void registrar() throws Exception {
        try {
            dao.guardar(cliente);
            EmailS.enviarPwdCliente(cliente);
            SmsS.enviarSMSCliente(cliente);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado", "Registrado con éxito"));
        } catch (Exception e) {
            Logger.getLogger(ClienteC.class.getName()).log(Level.SEVERE, "Error en registars ClienteC/registar: ", e.getMessage());
        }
    }

    public void modificar() throws Exception {
        try {
            dao.modificar(cliente);
//            EmailS.enviarPwdCliente(cliente);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado", "Registrado con éxito"));
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "Error en modificar ClienteC/modificar: ", e.getMessage());
        } finally {

        }
    }

    public void eliminar() throws Exception {
        try {
            dao.eliminar(cliente);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Eliminado", "Eliminado con éxito"));
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "Error en eliminar ClienteC/eliminar: ", e.getMessage());
        } finally {

        }
    }

    public void restaurar() throws Exception {
        try {
            dao.restaurar(cliente);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Restaurado", "Restaurado con éxito"));
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "Error en eliminar ClienteC/restaurar: ", e.getMessage());
        } finally {

        }
    }

//REPORTE VISTA PREVIA
    public void reporteCliente() throws Exception {
        try {
            ReporteS reporte = new ReporteS();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("reports/Cliente.jasper");
            reporte.ReportePdf(root);
            FacesContext.getCurrentInstance().responseComplete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "REPORTE GENERADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "AL GENERAR REPORTE"));
            System.out.println("Error en reporteCliente_C " + e.getMessage());
        }
    }

    public void listar() {
        try {
            lstCliente = dao.listarTodos(tipo);
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "Error en restaurar ClienteC/listar: ", e.getMessage());
        }
//        finally {
//
//        }
    }

    public void limpiar() {
        cliente = new Cliente();
    }

    public void opcionesPersonalizacion() {
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
