package model;

import java.util.UUID;
import lombok.Data;

@Data

public class Cliente {

    String pass1 = UUID.randomUUID().toString().toUpperCase().substring(2, 5);
    String pass2 = UUID.randomUUID().toString().toLowerCase().substring(2, 5);
    String pass3 = "#+";
    String password = pass1 + pass2 + pass3;

    private int IDCLI;
    private String NOMCLI;
    private String APEPATCLI;
    private String APEMATCLI;
    private String DNICLI;
    private String TELCLI;
    private String EMACLI;
    private String ESTCLI;
    private String DOMCLI;
    private String CODUBI;
    private String DISUBI;
    private String PROUBI;
    private String DEPUBI;
    private String PWDCLI = password;
}
