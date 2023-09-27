package model;
import lombok.Data;
@Data
public class Producto {

    private int IDPRO;
    private String NOMPRO;
    private Double PREPRO;
    private String DESPRO;
    private String ESTPRO;
    private int STOPRO = 1;
    private String IMGPRO;
    private int CANTVENDPRO;
    
}
