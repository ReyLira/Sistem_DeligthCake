package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import dao.Conexion;

@FacesValidator(value = "emailVV")
public class EmailVV extends Conexion implements Validator {

     String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object value) throws ValidatorException {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        String mail = ((String) value).trim();
        if (mail.isEmpty()) {  
        } else {
            Matcher matcher = pattern.matcher(mail);
            if (!matcher.matches()) {
                FacesMessage msg = new FacesMessage("Email Incorrecto");
                throw new ValidatorException(msg);
            }
        }
    }
}
