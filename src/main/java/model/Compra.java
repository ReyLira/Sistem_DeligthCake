package model;

import java.util.Date;
import java.util.GregorianCalendar;
import lombok.Data;

@Data

public class Compra {
    
    private int IDCOM;
    private Date FECCOM = GregorianCalendar.getInstance().getTime();
    private int IDPROV;
    private Double TOTCOM;
    private Proveedor proveedor = new Proveedor();
    private int IDEMP;
    private String NOMEMP;
    private Empleado empleado = new Empleado();
}
