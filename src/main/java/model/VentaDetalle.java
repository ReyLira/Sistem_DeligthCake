package model;

import lombok.Data;

@Data

public class VentaDetalle {

    private int IDVENDET;
    private int CANVENDET = 1;
    private int IDVEN;
    private int IDPRO;
    private Double SUBVENDET;
    private Producto producto = new Producto();
    private Venta venta = new Venta();
    private String NOMPRO;
    private Double PREPRO;
    private String DESPRO;
    private String INGPRO;
    private int STOPRO;
}
