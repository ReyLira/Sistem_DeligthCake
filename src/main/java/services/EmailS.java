package services;

import javax.swing.JOptionPane;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import dao.Conexion;
import static dao.Conexion.conectar;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Cliente;
import model.Empleado;

public class EmailS extends Conexion {

    public static void configuracionEmail(String remitente, String clave, String destinatario, String asunto, String cuerpo) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", clave);
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);

            BodyPart texto = new MimeBodyPart();
            texto.setText(cuerpo);
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            message.setContent(multiParte);

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, clave);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            JOptionPane.showMessageDialog(null, "Email SI Enviado", "CORRECTO", JOptionPane.INFORMATION_MESSAGE);
        } catch (MessagingException ex) {
            System.out.println("Error en configuracionEmail_S " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Email NO Enviado", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void enviarPwdCliente(Cliente cliente) throws Exception {
        String remitente = "reylira38@gmail.com";
        String clave = "cqhkukvnyebvbpyb";

        String destinatario = cliente.getEMACLI();
        String asunto = "Envio de tu Contraseña de DELIGHTCAKE";
        String cuerpo = "BUEN DÍA " + cliente.getNOMCLI() + " " + cliente.getAPEPATCLI()+ " " + cliente.getAPEMATCLI() + "\n"
                + "\n Su usuario es: " + cliente.getDNICLI() + "\n Su contraseña es: " + cliente.getPWDCLI() + "\n"
                + "\n Puedes iniciar sesión aqui: http://localhost:8080/AS211S3_T05_DELIGHTCAKE/faces/Login.xhtml" + "\n"
                + "\n Muchas gracias por registrarse en DELIGHTCAKE";

        try {
            EmailS.configuracionEmail(remitente, clave, destinatario, asunto, cuerpo);
        } catch (MessagingException ex) {
            System.out.println("Error en enviarEmail_S " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void enviarPwdEmpleado(Empleado empleado) throws Exception {
        // El correo gmail de envio
        String remitente = "reylira38@gmail.com";
        String clave = "cqhkukvnyebvbpyb";

        //Destinatario varia en la vista
        String destinatario = empleado.getEMAEMP();

        //Asunto y cuerpo y la contraseña generada desde el modelo
        String asunto = "Envio de tu Contraseña de DELIGHTCAKE";
        String cuerpo = "BUEN DÍA " + empleado.getNOMEMP() + " " + empleado.getAPEPATEMP() + " " + empleado.getAPEMATEMP() + "\n"
                + "\n Su usuario es: " + empleado.getDNIEMP() + "\n Su contraseña es: " + empleado.getPWDEMP() + "\n"
                + "\n Puedes iniciar sesión aqui: http://localhost:8080/AS211S3_T05_DELIGHTCAKE/faces/Login.xhtml" + "\n"
                + "\n Muchas gracias por registrarse en DELIGHTCAKE";

        try {
            EmailS.configuracionEmail(remitente, clave, destinatario, asunto, cuerpo);
        } catch (MessagingException ex) {
            System.out.println("Error en enviarEmail_S " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void restablecerPwdCliente(String email) throws Exception {
        Cliente cliente = null;
        String sql = "SELECT * FROM vPWDCLIENTE WHERE EMACLI = '" + email + "'";

        Statement st = conectar().createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            cliente = new Cliente();
            cliente.setIDCLI(rs.getInt("IDCLI"));
            cliente.setNOMCLI(rs.getString("NOMCLI"));
            cliente.setAPEPATCLI(rs.getString("APEPATCLI"));
            cliente.setAPEMATCLI(rs.getString("APEMATCLI"));
            cliente.setDNICLI(rs.getString("DNICLI"));
            cliente.setEMACLI(rs.getString("EMACLI"));
        }
        rs.close();
        st.close();

        // El correo gmail de envio
        String remitente = "reylira38@gmail.com";
        String clave = "cqhkukvnyebvbpyb";

        //Destinatario segun el usuario en el login
        String destinatario = cliente.getEMACLI();

        //Asunto y cuerpo y la contraseña generada desde el modelo
        String asunto = "Restablecer tu contraseña de la cuenta CONFLOMARK";
        String cuerpo = "BUEN DÍA " + cliente.getNOMCLI() + " " + cliente.getAPEPATCLI()+ " " + cliente.getAPEMATCLI() + "\n"
                + "Has indicado que olvidaste tu contraseña en la cuenta CONFLOMARK: " + cliente.getDNICLI() + "\n"
                + "\n Su nueva contraseña es: " + cliente.getPWDCLI() + "\n"
                + "\n Puedes iniciar sesión aqui: http://localhost:8080/AS211S3_T05_DELIGHTCAKE/faces/Login.xhtml" + "\n"
                + "\n Puedes comunicarte al email: " + remitente + " para mayor ayuda o información." + "\n"
                + "Estaremos encantados de responder las preguntas que puedas tener." + "\n"
                + "\n Gracias, atentamente el equipo de cuentas de DELIGHTCAKE.";

        String sqll = "update CLIENTE set PWDCLI=? where IDCLI=? ";
        try {
            PreparedStatement ps = conectar().prepareStatement(sqll);
            ps.setString(1, cliente.getPWDCLI());
            ps.setInt(2, cliente.getIDCLI());
            ps.executeUpdate();
            ps.close();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "REVISE SU CORREO", "Se Envio su Contraseña"));
        } catch (Exception e) {
            System.out.println("Error en restablecerPwdCli_S " + e.getMessage());
            e.printStackTrace();
        }

        try {
            EmailS.configuracionEmail(remitente, clave, destinatario, asunto, cuerpo);
        } catch (MessagingException ex) {
            System.out.println("Error en restablecerPwdCli_S " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Cliente cliente = new Cliente();
        cliente.setEMACLI("jhanpool.cupe@vallegrande.edu.pe");
        cliente.setDNICLI("76473233");
        cliente.setNOMCLI("Jhanpool");
        cliente.setAPEPATCLI("Cupe");
        cliente.setAPEMATCLI("Perez");
        enviarPwdCliente(cliente);
//        Empleado empleado = new Empleado();
//        empleado.setEMAEMP("jhanpool.cupe@vallegrande.edu.pe");
//        empleado.setDNIEMP("76473233");
//        empleado.setNOMEMP("Jhanpool");
//        empleado.setAPEEMP("Cupe Perez");
//        enviarPwdEmpleado(empleado);
    }
}
