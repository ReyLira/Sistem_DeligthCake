package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("rolCV")
public class RolCV implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String Rol = "";
        if (value != null) {
            Rol = (String) value;
            switch (Rol) {
                case "E":
                    Rol = "Empleado";
                    break;
                case "A":
                    Rol = "Administrador";
                    break;
            }
        }
        return Rol;
    }
    
}
