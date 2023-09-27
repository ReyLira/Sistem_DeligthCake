package validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import dao.Conexion;
//https://www.youtube.com/watch?v=T4CdPISRHFY

@FacesValidator(value = "celularV")
public class CelularV extends Conexion implements Validator {

    public static boolean duplicadoCelularCliente(String celular) {
        try {
            PreparedStatement ps = Conexion.conectar().prepareStatement("SELECT TELCLI FROM CLIENTE WHERE TELCLI = '" + celular + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Error en duplicadoCelular " + e.getMessage());
        }
        return false;
    }

    public static boolean duplicadoCelularEmpleado(String celular) {
        try {
            PreparedStatement ps = Conexion.conectar().prepareStatement("SELECT TELEMP FROM EMPLEADO WHERE TELEMP = '" + celular + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Error en duplicadoCelular " + e.getMessage());
        }
        return false;
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String numero = value.toString().trim();
        if (numero.length() != 0 && numero.length() < 10) {
            String formato = "^9\\d\\d\\d\\d\\d\\d\\d\\d$";
            boolean val = Pattern.matches(formato, numero);
            if (!val) {
                throw new ValidatorException(new FacesMessage("El formato es 9########"));
            }
        }
        String celular = (String) value;
        if (duplicadoCelularCliente(celular)) {
            throw new ValidatorException(new FacesMessage("Duplicado, el CELULAR ya existe"));
        }
         String celularr = (String) value;
        if (duplicadoCelularEmpleado(celularr)) {
            throw new ValidatorException(new FacesMessage("Duplicado, el CELULAR ya existe"));
        }
    }
}
