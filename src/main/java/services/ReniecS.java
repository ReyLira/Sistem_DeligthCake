package services;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Cliente;
import model.Empleado;
import com.google.gson.JsonSyntaxException;

public class ReniecS {

    public static Boolean disabled = true;

    public static void main(String[] args) throws Exception {
//        Cliente cliente = new Cliente();
//        cliente.setDNICLI("76473233");
//        buscarPorDNICliente(cliente);
        Empleado empleado = new Empleado();
        empleado.setDNIEMP("76473233");
        buscarPorDNIEmpleado(empleado);
    }

    public static void buscarPorDNICliente(Cliente cliente) throws Exception {
        String leerdni = cliente.getDNICLI();
        String token = "?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp1bGlvLnF1aXNwZUB2YWxsZWdyYW5kZS5lZHUucGUifQ.6M-P2QMMvKFZEeMvTUXvkOooM02N_pWqt0OdlaYW3PM";
        String enlace = "https://dniruc.apisperu.com/api/v1/dni/" + leerdni + token;
        try {
            URL url = new URL(enlace);
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            if (root.isJsonObject()) {
                disabled = true;
                JsonObject rootobj = root.getAsJsonObject();
                String nombres = rootobj.get("nombres").getAsString();
                String apellido_paterno = rootobj.get("apellidoPaterno").getAsString();
                String apellido_materno = rootobj.get("apellidoMaterno").getAsString();
//                cliente.setNOMCLI(CamelCaseS.camelMayuscula(nombres));
//                cliente.setAPECLI(CamelCaseS.camelMayuscula(apellido_paterno + " " + apellido_materno));
                cliente.setNOMCLI(nombres);
                cliente.setAPEPATCLI(apellido_paterno);
                cliente.setAPEMATCLI(apellido_materno);
                cliente.setDOMCLI("");
                cliente.setEMACLI("");
                cliente.setTELCLI("");
                cliente.setCODUBI("Seleccione");
                System.out.println("RESULTADO:\n");
                System.out.println(nombres + "\n" + apellido_paterno + "\n" + apellido_materno + "\n");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "BUSQUEDA", "DNI Exitoso"));
            } else {
                disabled = false;
                cliente.setNOMCLI("");
                cliente.setAPEPATCLI("");
                cliente.setAPEMATCLI("");
                cliente.setDOMCLI("");
                cliente.setEMACLI("");
                cliente.setTELCLI("");
                cliente.setCODUBI("Seleccione");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "DNI no Encontrado o el servidor falló"));
            }
        } catch (NullPointerException e) {
            disabled = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "DNI no Encontrado o el servidor falló"));
            System.out.println("Error en buscarPorDNI_S " + e.getMessage());
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            disabled = false;
        }
    }

    public static void buscarPorDNIEmpleado(Empleado empleado) throws Exception {
        String leerdni = empleado.getDNIEMP();
        String token = "?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp1bGlvLnF1aXNwZUB2YWxsZWdyYW5kZS5lZHUucGUifQ.6M-P2QMMvKFZEeMvTUXvkOooM02N_pWqt0OdlaYW3PM";
        String enlace = "https://dniruc.apisperu.com/api/v1/dni/" + leerdni + token;
        try {
            URL url = new URL(enlace);
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            if (root.isJsonObject()) {
                disabled = true;
                JsonObject rootobj = root.getAsJsonObject();
                String nombres = rootobj.get("nombres").getAsString();
                String apellido_paterno = rootobj.get("apellidoPaterno").getAsString();
                String apellido_materno = rootobj.get("apellidoMaterno").getAsString();
                empleado.setNOMEMP(nombres);
                empleado.setAPEPATEMP(apellido_paterno);
                empleado.setAPEMATEMP(apellido_materno);
                empleado.setEMAEMP("");
                empleado.setTELEMP("");
                empleado.setROLEMP("");
                empleado.setCODUBI("Seleccione");
                System.out.println("RESULTADO:\n");
                System.out.println(nombres + "\n" + apellido_paterno + "\n" + apellido_materno + "\n");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "BUSQUEDA", "DNI Exitoso"));
            } else {
                disabled = false;
                empleado.setNOMEMP("");
                empleado.setAPEPATEMP("");
                empleado.setAPEMATEMP("");
                empleado.setEMAEMP("");
                empleado.setTELEMP("");
                empleado.setROLEMP("");
                empleado.setCODUBI("Seleccione");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "DNI no Encontrado o el servidor falló"));
            }
        } catch (NullPointerException e) {
            disabled = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "DNI no Encontrado o el servidor falló"));
            System.out.println("Error en buscarPorDNI_S " + e.getMessage());
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            disabled = false;
        }
    }
}
