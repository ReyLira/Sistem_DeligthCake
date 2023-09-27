package model;

import lombok.Data;

@Data
public class CompraDetalle {
    private int IDCOMDET;
    private int CANCOMDET ;
    private int IDCOM;
    private int IDPRO;
    private Double SUBCOMDET;
    private Producto producto = new Producto();
    private Compra compra = new Compra();
    private String NOMPRO;
    private Double PREPRO;
    private String DESPRO;
    private int STOPRO;
}
