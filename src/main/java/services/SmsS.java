package services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.Cliente;
import model.Empleado;

public class SmsS {

    public static void main(String[] args) throws Exception {
        Cliente cliente = new Cliente();
        cliente.setTELCLI("991692530");
        cliente.setDNICLI("76473233");
        cliente.setNOMCLI("");
        cliente.setAPEPATCLI("Cupe");
        cliente.setAPEMATCLI("Perez");
        enviarSMSCliente(cliente);
        
//        Empleado empleado = new Empleado();
//        empleado.setTELEMP("997533646");
//        empleado.setDNIEMP("76473233");
//        empleado.setNOMEMP("Jhanpool");
//        empleado.setAPEEMP("Cupe Perez");
//        enviarSMSEmpleado(empleado);
    }

    public static void enviarSMSCliente(Cliente cliente) throws UnirestException {
        System.out.println("Se envio el mensaje al celular = " + cliente.getTELCLI());

        String mensaje = "BUEN DÍA " + cliente.getNOMCLI() + " " + cliente.getAPEPATCLI()+ " " + cliente.getAPEMATCLI() + " Su usuario es: " + cliente.getDNICLI() + " Su contraseña es: " + cliente.getPWDCLI() + " Muchas gracias por registrarse en DELIGHTCAKE";

        try {
            String autenticacion = "cmV5LmdvbnphbGVzZGVscmllZ29AdmFsbGVncmFuZGUuZWR1LnBlOjlCQTZEM0YyLTg2NEQtMjY0Qy00NkVELTYxQzAxQkIyMEY0Rg==";
            String RapiKey = "ce2fba1bd8msh511e977834b19f6p10912ejsnbcb9ac35fb53";

            HttpResponse<String> response = Unirest.post("https://clicksend.p.rapidapi.com/sms/send")
                    .header("content-type", "application/json")
                    .header("Authorization", "Basic " + autenticacion)
                    .header("Content-Type", "application/json")
                    .header("X-RapidAPI-Key", RapiKey)
                    .header("X-RapidAPI-Host", "clicksend.p.rapidapi.com")
                    .body("{\n  \"messages\": [\n    {\n    "
                            + "  \"source\": \"mashape\",\n    "
                            + "  \"from\": \"Test\",\n    "
                            + "  \"body\": \"" + mensaje + "\",\n  "
                            + "    \"to\": \"+" + "+51" + cliente.getTELCLI() + "\",\n     "
                            + " \"schedule\": \"1452244637\",\n    "
                            + "  \"custom_string\": \"this is a test\"\n   "
                            + " }\n  ]\n}")
                    .asString();
        } catch (UnirestException e) {
            System.out.println("Error en enviar el mensaje :v " + e.getMessage());
        }
    }
    
    public static void enviarSMSEmpleado(Empleado empleado) throws UnirestException {
        System.out.println("Se envio el mensaje al celular = " + empleado.getTELEMP());

        String mensaje = "BUEN DÍA " + empleado.getNOMEMP()+ " " + empleado.getAPEPATEMP()+ " " + empleado.getAPEMATEMP()+ " Su usuario es: " + empleado.getDNIEMP()+ " Su contraseña es: " + empleado.getPWDEMP()+ " Muchas gracias por registrarse en DELIGHTCAKE";

        try {
            String autenticacion = "cmV5LmdvbnphbGVzZGVscmllZ29AdmFsbGVncmFuZGUuZWR1LnBlOjlCQTZEM0YyLTg2NEQtMjY0Qy00NkVELTYxQzAxQkIyMEY0Rg==";
            String RapiKey = "ce2fba1bd8msh511e977834b19f6p10912ejsnbcb9ac35fb53";

            HttpResponse<String> response = Unirest.post("https://clicksend.p.rapidapi.com/sms/send")
                    .header("content-type", "application/json")
                    .header("Authorization", "Basic " + autenticacion)
                    .header("Content-Type", "application/json")
                    .header("X-RapidAPI-Key", RapiKey)
                    .header("X-RapidAPI-Host", "clicksend.p.rapidapi.com")
                    .body("{\n  \"messages\": [\n    {\n    "
                            + "  \"source\": \"mashape\",\n    "
                            + "  \"from\": \"Test\",\n    "
                            + "  \"body\": \"" + mensaje + "\",\n  "
                            + "    \"to\": \"+" + "+51" + empleado.getTELEMP()+ "\",\n     "
                            + " \"schedule\": \"1452244637\",\n    "
                            + "  \"custom_string\": \"this is a test\"\n   "
                            + " }\n  ]\n}")
                    .asString();
        } catch (UnirestException e) {
            System.out.println("Error en enviar el mensaje :v " + e.getMessage());
        }
    }
}
