package model;

import lombok.Data;

@Data
public class Proveedor {
    
    private Integer IDPROV;
    private String RAZPROV;
    private String DIRPROV;
    private String CELPROV;
    private String RUCPROV;
    private String EMAPROV;
    private String CODUBI;
    private String DISUBI;
    private String PROUBI;
    private String DEPUBI;
    private String ESTPROV;
    private Ubigeo ubigeo = new Ubigeo();
}
