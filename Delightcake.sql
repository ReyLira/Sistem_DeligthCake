CREATE TABLE CLIENTE (
    IDCLI integer PRIMARY KEY,
    NOMCLI varchar2(50)  NOT NULL,
    APEPATCLI varchar2(50)  NOT NULL,
    APEMATCLI varchar2(50)  NOT NULL,
    DNICLI char(8)  NOT NULL,
    TELCLI char(9)  NOT NULL,
    EMACLI varchar2(60)  NOT NULL,
    ESTCLI char(1)  NOT NULL,
    DOMCLI varchar2(50)  NOT NULL,
    CODUBI char(6)  NOT NULL,
    PWDCLI varchar2(15) NOT NULL
) ;

CREATE TABLE COMPRA (
    IDCOM integer  PRIMARY KEY,
    FECCOM date  NOT NULL,
    IDPROV integer  NOT NULL,
    TOTCOM decimal(5, 2)  NOT NULL,
    IDEMP integer NOT NULL
);

CREATE TABLE COMPRA_DETALLE (
    IDCOMDET integer  PRIMARY KEY,
    CANCOMDET integer  NOT NULL,
    IDCOM integer  NOT NULL,
    SUBCOMDET decimal(5,2)  NOT NULL,
    IDPRO integer NOT NULL
) ;

-- Table: EMPLEADO
CREATE TABLE EMPLEADO (
    IDEMP integer  PRIMARY KEY,
    NOMEMP varchar2(50)  NOT NULL,
    APEPATEMP varchar2(50)  NOT NULL,
    APEMATEMP varchar2(50)  NOT NULL,
    DNIEMP char(8)  NOT NULL,
    TELEMP char(9)  NOT NULL,
    ESTEMP char(1)  NOT NULL,
    EMAEMP varchar2(60)  NOT NULL,
    ROLEMP varchar2(30)  NOT NULL,
    CODUBI char(6)  NOT NULL,
    PWDEMP varchar2(15) NOT NULL
) ;


-- Table: PRODUCTO
CREATE TABLE PRODUCTO (
    IDPRO integer PRIMARY KEY,
    NOMPRO varchar2(50)  NOT NULL,
    PREPRO decimal(5,2)  NOT NULL,
    DESPRO varchar2(250)  NOT NULL,
    ESTPRO char(1)  NOT NULL,
    IDPROV INTEGER NOT NULL,
    STOPRO integer  NOT NULL
) ;

-- Table: PROVEEDOR
CREATE TABLE PROVEEDOR (
    IDPROV integer PRIMARY KEY,
    RAZPROV varchar2(80)  NOT NULL,
    DIRPROV varchar2(80)  NOT NULL,
    CELPROV char(9)  NOT NULL,
    RUCPROV char(11)  NOT NULL,
    EMAPROV varchar2(50)  NOT NULL,
    CODUBI char(6) NOT NULL,
    ESTPROV char(1)  NOT NULL
) ;

-- Table: UBIGEO
CREATE TABLE UBIGEO (
    CODUBI char(6) PRIMARY KEY,
    DEPUBI varchar2(50)  NOT NULL,
    PROUBI varchar2(50)  NOT NULL,
    DISUBI varchar2(70)  NOT NULL
) ;

-- Table: VENTA
CREATE TABLE VENTA (
    IDVEN integer PRIMARY KEY,
    FECVEN date  NOT NULL,
    IDEMP integer  NOT NULL,
    IDCLI integer  NOT NULL,
    TOTVEN decimal(5, 2)  NOT NULL
) ;

-- Table: VENTA_DETALLE
CREATE TABLE VENTA_DETALLE (
    IDVENDET integer PRIMARY KEY,
    CANVENDET integer  NOT NULL,
    IDVEN integer  NOT NULL,
    IDPRO integer  NOT NULL,
    SUBVENDET decimal(5,2)  NOT NULL
) ;

-- foreign keys
-- Reference: CLIENTE_UBIGEO (table: CLIENTE)
ALTER TABLE CLIENTE ADD CONSTRAINT CLIENTE_UBIGEO FOREIGN KEY (CODUBI)REFERENCES UBIGEO (CODUBI);

-- Reference: COMPRA_DETALLE_COMPRA (table: COMPRA_DETALLE)
ALTER TABLE COMPRA_DETALLE ADD CONSTRAINT COMPRA_DETALLE_COMPRA FOREIGN KEY (IDCOM)REFERENCES COMPRA (IDCOM);

-- Reference: COMPRA_DETALLE_PRODUCTO (table: COMPRA_DETALLE)
ALTER TABLE COMPRA_DETALLE ADD CONSTRAINT COMPRA_DETALLE_PRODUCTO FOREIGN KEY (IDPRO) REFERENCES PRODUCTO (IDPRO);

-- Reference: COMPRA_PROVEEDOR (table: COMPRA)
ALTER TABLE COMPRA ADD CONSTRAINT COMPRA_PROVEEDOR FOREIGN KEY (IDPROV) REFERENCES PROVEEDOR (IDPROV);

-- Reference: COMPRA_EMPLEADO (table: COMPRA)
ALTER TABLE COMPRA ADD CONSTRAINT COMPRA_EMPLEADO FOREIGN KEY (IDEMP) REFERENCES EMPLEADO (IDEMP);

-- Reference: EMPLEADO_UBIGEO (table: EMPLEADO)
ALTER TABLE EMPLEADO ADD CONSTRAINT EMPLEADO_UBIGEO FOREIGN KEY (CODUBI) REFERENCES UBIGEO (CODUBI);

-- Reference: PROVEEDOR_UBIGEO (table: PROVEEDOR)
ALTER TABLE PROVEEDOR ADD CONSTRAINT PROVEEDOR_UBIGEO FOREIGN KEY (CODUBI) REFERENCES UBIGEO (CODUBI);

-- Reference: VENTA_CLIENTE (table: VENTA)
ALTER TABLE VENTA ADD CONSTRAINT VENTA_CLIENTE FOREIGN KEY (IDCLI) REFERENCES CLIENTE (IDCLI);

-- Reference: VENTA_DETALLE_VENTA (table: VENTA_DETALLE)
ALTER TABLE VENTA_DETALLE ADD CONSTRAINT VENTA_DETALLE_VENTA FOREIGN KEY (IDVEN) REFERENCES VENTA (IDVEN);

-- Reference: VENTA_Empleado (table: VENTA)
ALTER TABLE VENTA ADD CONSTRAINT VENTA_Empleado FOREIGN KEY (IDEMP) REFERENCES EMPLEADO (IDEMP);

-- Reference: Venta_Detalle_PRODUCTO (table: VENTA_DETALLE)
ALTER TABLE VENTA_DETALLE ADD CONSTRAINT Venta_Detalle_PRODUCTO FOREIGN KEY (IDPRO) REFERENCES PRODUCTO (IDPRO);

-- Secuencias

CREATE SEQUENCE ID_CLIENTE START WITH 1 INCREMENT BY 1 ORDER;
CREATE SEQUENCE ID_COMPRA START WITH 1 INCREMENT BY 1 ORDER;
CREATE SEQUENCE ID_COMPRADETALLE START WITH 1 INCREMENT BY 1 ORDER;
CREATE SEQUENCE ID_EMPLEADO START WITH 1 INCREMENT BY 1 ORDER;
CREATE SEQUENCE ID_PRODUCTO START WITH 1 INCREMENT BY 1 ORDER;
CREATE SEQUENCE ID_PROVEEDOR START WITH 1 INCREMENT BY 1 ORDER;
CREATE SEQUENCE ID_VENTA START WITH 1 INCREMENT BY 1 ORDER;
CREATE SEQUENCE ID_VENTADETALLE START WITH 1 INCREMENT BY 1 ORDER;


-- TRIGER

CREATE OR REPLACE TRIGGER ID_CLIENTE BEFORE INSERT ON CLIENTE FOR EACH ROW BEGIN SELECT ID_CLIENTE.NEXTVAL INTO :NEW.IDCLI FROM DUAL;
END;
/
CREATE OR REPLACE TRIGGER ID_COMPRA BEFORE INSERT ON COMPRA FOR EACH ROW BEGIN SELECT ID_COMPRA.NEXTVAL INTO :NEW.IDCOM FROM DUAL;
END;
/
CREATE OR REPLACE TRIGGER ID_COMPRADETALLE BEFORE INSERT ON COMPRA_DETALLE FOR EACH ROW BEGIN SELECT ID_COMPRADETALLE.NEXTVAL INTO :NEW.IDCOMDET FROM DUAL;
END;
/
CREATE OR REPLACE TRIGGER ID_EMPLEADO BEFORE INSERT ON EMPLEADO FOR EACH ROW BEGIN SELECT ID_EMPLEADO.NEXTVAL INTO :NEW.IDEMP FROM DUAL;
END;
/
CREATE OR REPLACE TRIGGER ID_PRODUCTO BEFORE INSERT ON PRODUCTO FOR EACH ROW BEGIN SELECT ID_PRODUCTO.NEXTVAL INTO :NEW.IDPRO FROM DUAL;
END;
/
CREATE OR REPLACE TRIGGER ID_PROVEEDOR BEFORE INSERT ON PROVEEDOR FOR EACH ROW BEGIN SELECT ID_PROVEEDOR.NEXTVAL INTO :NEW.IDPROV FROM DUAL;
END;
/
CREATE OR REPLACE TRIGGER ID_VENTA BEFORE INSERT ON VENTA FOR EACH ROW BEGIN SELECT ID_VENTA.NEXTVAL INTO :NEW.IDVEN FROM DUAL;
END;
/
CREATE OR REPLACE TRIGGER ID_VENTADETALLE BEFORE INSERT ON VENTA_DETALLE FOR EACH ROW BEGIN SELECT ID_VENTADETALLE.NEXTVAL INTO :NEW.IDVENDET FROM DUAL;
END;
/


-- TRIGGER PARA RESTAR EL STOCK DE PRODUCTO
CREATE OR REPLACE TRIGGER RESTAR_STOCK AFTER INSERT ON VENTA_DETALLE FOR EACH ROW DECLARE BEGIN
UPDATE PRODUCTO SET STOPRO = STOPRO - :NEW.CANVENDET WHERE IDPRO = :NEW.IDPRO;
END;
/

-- TRIGGER PARA SUMAR EL STOCK DE PRODUCTO
CREATE OR REPLACE TRIGGER SUMAR_STOCK AFTER INSERT ON COMPRA_DETALLE FOR EACH ROW DECLARE BEGIN
UPDATE PRODUCTO SET STOPRO = STOPRO + :NEW.CANCOMDET WHERE IDPRO = :NEW.IDPRO;
END;
/

-- INSERTAR REGISTROS DE UBIGEO

INSERT INTO UBIGEO (CODUBI,DEPUBI,PROUBI,DISUBI) VALUES ('150501','LIMA','CA�ETE','SAN VICENTE');
INSERT INTO UBIGEO (CODUBI,DEPUBI,PROUBI,DISUBI) VALUES ('150502','LIMA','CA�ETE','ASIA');
INSERT INTO UBIGEO (CODUBI,DEPUBI,PROUBI,DISUBI) VALUES ('150503','LIMA','CA�ETE','CALANGO');
INSERT INTO UBIGEO (CODUBI,DEPUBI,PROUBI,DISUBI) VALUES ('150504','LIMA','CA�ETE','CERRO AZUL');
INSERT INTO UBIGEO (CODUBI,DEPUBI,PROUBI,DISUBI) VALUES ('150505','LIMA','CA�ETE','CHILCA');
INSERT INTO UBIGEO (CODUBI,DEPUBI,PROUBI,DISUBI) VALUES ('150506','LIMA','CA�ETE','COAYLLO');
INSERT INTO UBIGEO (CODUBI,DEPUBI,PROUBI,DISUBI) VALUES ('150507','LIMA','CA�ETE','IMPERIAL');
INSERT INTO UBIGEO (CODUBI,DEPUBI,PROUBI,DISUBI) VALUES ('150508','LIMA','CA�ETE','LUNAHUANA');
INSERT INTO UBIGEO (CODUBI,DEPUBI,PROUBI,DISUBI) VALUES ('150509','LIMA','CA�ETE','MALA');
INSERT INTO UBIGEO (CODUBI,DEPUBI,PROUBI,DISUBI) VALUES ('150510','LIMA','CA�ETE','NUEVO IMPERIAL');
INSERT INTO UBIGEO (CODUBI,DEPUBI,PROUBI,DISUBI) VALUES ('150511','LIMA','CA�ETE','PACARAN');
INSERT INTO UBIGEO (CODUBI,DEPUBI,PROUBI,DISUBI) VALUES ('150512','LIMA','CA�ETE','QUILMANA');
INSERT INTO UBIGEO (CODUBI,DEPUBI,PROUBI,DISUBI) VALUES ('150513','LIMA','CA�ETE','SAN ANTONIO');
INSERT INTO UBIGEO (CODUBI,DEPUBI,PROUBI,DISUBI) VALUES ('150514','LIMA','CA�ETE','SAN LUIS');
INSERT INTO UBIGEO (CODUBI,DEPUBI,PROUBI,DISUBI) VALUES ('150515','LIMA','CA�ETE','SANTA CRUZ DE FLORES');
INSERT INTO UBIGEO (CODUBI,DEPUBI,PROUBI,DISUBI) VALUES ('150516','LIMA','CA�ETE','ZU?IGA');

-- INSERTAR REGISTROS DE CLIENTE

INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('DANIEL','LOPEZ','CANCHARI','76234902','920087382','Daniel_lopez@hotmail.com','A','150501','Av. 28 de Julio','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('MIGYEL','FLORES','MIX','62386386','973821232','Migyel.Flores@hotmail.com','A','150502','Av Jose Olaya','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('MARIO', 'MAMADI','GUTIERRES','57367287','914215788','MamadiGutierre@hotmail.com','A','150502','Av Jose Olaya','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('JOSE','MAXMA','LINGA','56486287','996545788','Maxmalinga@hotmail.com','I','150503','Jr. Santa Maria','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('MARIA','BROS','GONZALES','74623287','996215248','Maria_10@hotmail.com','A','150505','Av. Arequipa','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('LUIS','MELAZCO','MARUIN','65402343','912215788','Melazco12@hotmail.com','I','150503','Jr. Santa Maria','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('FERNANDO','REINA','MIMADI','78728723','996546788','Fernando.Mimadi@hotmail.com','I','150505','Av. Arequipa','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('ESTEBAN','PIXILIONGO','MIZERO','45232302','995615788','Pixiliongo@hotmail.com','I','150503','Jr. Santa Maria','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('PEDRO', 'CANCHARI','MAXMA','42105213','964561718','Canchari_Maxma@hotmail.com','A','150501','Miguel Grau','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('FRANCO','LOREX','HUAMAN','4051902','996215745','Lorex.12@hotmail.com','A','150502','Av Jose Olaya','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('MARCO', 'DIAZ','MARTINEZ','34309021','976215732','MarcoMartinez@hotmail.com','I','150504','Jorge Chavez','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('STAR','VALIAS','FLORES','67228702','944575785','Flores.123@hotmail.com','A','150505','Av. Ricardo Palma','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('BLANCA','FLORES','ACEVEDO','98326102','996223586','BlancaFlores@hotmail.com','I','150506','Jr. Bolognesi','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('ROSA','MAXI','MARUA','76422102','996248478','maruaRosa@hotmail.com','A','150502','Av Jose Olaya','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('MANUEL','LOPEZ','MAXIMA','34234533','996254788','MaximaManuel@hotmail.com','A','150503','Jir�n Real','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('DIEGO','CANCHARI','MATAMA','68089027','946815783','Canchari@hotmail.com','A','150504','Jorge Chavez','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('MARIO','BERROCAL','ZENTENO','40845501','996286468','BerrocalMario@hotmail.com','I','150505','Av. San Francisco','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('LUIS','LOPEZ','SANCHEZ','51234902','993155782','LopezSanchez@hotmail.com','I','150507','28 de Julio','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('XIMENA','LOPEZ','CANCHARI','40254502','965415718','Ximena@hotmail.com','I','150507','2 DE MAYO','Java@123');
INSERT INTO CLIENTE (NOMCLI,APEPATCLI,APEMATCLI,DNICLI,TELCLI,EMACLI,ESTCLI,CODUBI,DOMCLI,PWDCLI) VALUES ('MAXIMO','CANCHARI','SIERRA','77343233','985215788','Maximo@hotmail.com','I','150501','Av. Los Libertadores','Java@123');


-- INSERTAR REGISTROS DE EMPLEADO  

INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('MARIA','EMILIA','LOPEZ','74247242','998756524','A','MariaEmilia@hotmail.com','E','150501','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('JUAN','HERNANDEZ','LOPEZ','86568655','965454224','A','Hernadez@hotmail.com','E','150502','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('MARIO','LOZAR','LANLI','74854242','998754224','I','Lanli@hotmail.com','E','150501','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('LUISA','HERNAN','LONZI','01854242','998747224','A','Luisa@hotmail.com','E','150503','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('JIAMPIER','ZAMBIA','LOZAR','65414242','941754224','I','Lozar@hotmail.com','E','150502','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('GEORGE','MARIANO','MARCOS','32554242','985124224','A','Marcos@hotmail.com', 'E','150503','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('MIGUEL','JUAZMAN','LAZANO','12454242','936144224','A','Miguel@hotmail.com','E','150514','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('XIMENA','LANZAR','LOPEZ','18654222','998755644','A','Ximena@hotmail.com','E','150504','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('PEDRO','JARMEN','MARINAL','85854142','998232224','A','Marinal@hotmail.com','E','150503','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('JUAN','PADRETION','MADRINO','96554242','946554224','A','Juan@hotmail.com','E','150501','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('MANUEL','LAZARO','ROSAS','01114242','944554224','I','Lazaro@hotmail.com','E','150501','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('CARLOS','PARIONAS','ROSAS','23354242','933354224','I','Rosas@hotmail.com','E','150501','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('BEN','TENISON','MADERE','45554242','998788824','A','ben@hotmail.com','E','150514','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('MORTY','JAZMEN','LISPER','65454242','998753333','A','Morty@hotmail.com','A','150502','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('RICK','CANCHARI','MIGUEL','88665542','998788664','A','Canchari@hotmail.com','E','150501','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('MEG','GRIFFING','LAZARO','31134242','998144224','I','Lazaro@hotmail.com','E','150503','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('HUGO','ROSAS','MIGUEL','35254242','998711524','A','Rosas@hotmail.com','E','150503','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('JESUS','HUAPYA','RIBERAS','01254242','944422224','I','Jesus@hotmail.com','E','150502','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('MARCOS','MANZI','LOPEZ','16654142','998733234','A','Marcos@hotmail.com','E','150501','Java@123');
INSERT INTO EMPLEADO(NOMEMP,APEPATEMP,APEMATEMP,DNIEMP,TELEMP,ESTEMP,EMAEMP,ROLEMP,CODUBI,PWDEMP) VALUES ('MARIA','ZENTENO','PEREZ','24253242','996554124','I','Perez@hotmail.com','E','150501','Java@123');

-- INSERTAR REGISTROS DE PROVEEDOR

INSERT INTO PROVEEDOR(RAZPROV,DIRPROV,CELPROV,RUCPROV,EMAPROV,CODUBI,ESTPROV) VALUES ('ABBA S.A.C.','Calle Perusa 1480','958675645','20108983583','abba@hotmail.com','150501','A');
INSERT INTO PROVEEDOR(RAZPROV,DIRPROV,CELPROV,RUCPROV,EMAPROV,CODUBI,ESTPROV) VALUES ('CORPORACI?N JOS? R.LINDLEY','Jr. Cajamarca 371 Rimac','968749506','20108968796','jrlindley@incakola.com.pe','150502','I');
INSERT INTO PROVEEDOR(RAZPROV,DIRPROV,CELPROV,RUCPROV,EMAPROV,CODUBI,ESTPROV) VALUES ('ALICORP S.A.','Av. Avenida Argentina #4793','958947564','20108678678','asilva@alicorp.com.pe','150503','A');
INSERT INTO PROVEEDOR(RAZPROV,DIRPROV,CELPROV,RUCPROV,EMAPROV,CODUBI,ESTPROV) VALUES ('BRAEDT','Calle Michael Faraday 111 ? Urb. Sta. Rosa','969478543','20106984756','braedt@hotmail.com','150504','I');
INSERT INTO PROVEEDOR(RAZPROV,DIRPROV,CELPROV,RUCPROV,EMAPROV,CODUBI,ESTPROV) VALUES ('SAN FERNANDO','Av. La Paz Lt. 30 ? Huachipa','958745689','20189789687','informes@san-fernando.com.pe','150505','A');
INSERT INTO PROVEEDOR(RAZPROV,DIRPROV,CELPROV,RUCPROV,EMAPROV,CODUBI,ESTPROV) VALUES ('GLORIA S.A.','Av. Rep?blica de Panam? 2461 ? Lima 13 ','989678569','20698796896','gloria@hotmail.com','150506','I');
INSERT INTO PROVEEDOR(RAZPROV,DIRPROV,CELPROV,RUCPROV,EMAPROV,CODUBI,ESTPROV) VALUES ('MAYORSA','Comunidad Industria, 240 ? CHORRILLOS ? Lima','958697867','20687968795','ventas@mayorsa.com.pe','150507','A');
INSERT INTO PROVEEDOR(RAZPROV,DIRPROV,CELPROV,RUCPROV,EMAPROV,CODUBI,ESTPROV) VALUES ('PROLIMERSA','Jr. San Diego 225, Surquillo ? Lima','979867896','20697867986','prolimersa@hotmail.com','150508','I');
INSERT INTO PROVEEDOR(RAZPROV,DIRPROV,CELPROV,RUCPROV,EMAPROV,CODUBI,ESTPROV) VALUES ('NESTLE','Av. Los Castillos, cuadra 3 ? Ate','985786987','20589678567','nestle@hotmail.com','150509','A');
INSERT INTO PROVEEDOR(RAZPROV,DIRPROV,CELPROV,RUCPROV,EMAPROV,CODUBI,ESTPROV) VALUES ('CORPORACI?N CUSTER','C. Central Km 10.8 Sta. Clara ? ATE VITARTE ? Lima','986785678','20196876786','custer@hotmail.com','150510','I');


-- INSERTAR REGISTROS DE PRODUCTO 
    
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('TORTA DE CHOCOLATE', '57,0', 'SI DESEA ENDULZAR SU VIDA COMPRE SU RICA TORTA DE CHOCOLATE.','A','1','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('TORTA DE VAINILLA', '54,0','ES UNA TORTA ESPONJOSA','A','2','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('TORTA MARMOLEADO', '55,0','ES UN PASTEL DULCE CONTINE COCOA Y CACAO.','A','3','10'); 
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('TORTA DE TRES LECHES', '50,0','CONSISTE EN UN BISCOCHO BA?ADO CON TRES TIPOS DE LECHE ','A','4','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('TORTA DE NARANJA', '40,0','CONTIENE LOS SABORES DEL JUGO DE NARANJA, RALLADURA DE LA CASCARA Y ESENCIA DE NARANJA.','A','5','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('TORTA HELADA', '45,0','TORTA HELADA ES TANTO UN HELADO CON FORMA DE TORTA.','A','6','10');  
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('TORTA DE SELVA NEGRA', '53,0','LA TORTA SELVA NEGRA ES UNA TARTA TIPICA DE COCINA DE BADEN.','A','7','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('QUEQUE DE NARANJA',' 41,0', 'ES UN DELICIOSO Y CONTIENE NARANJA QUE TIENE PROPIEDADES BENIFICIOSOS.','A','8','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('QUEQUE DE LIMON', '43,0', 'ES UN QUEQUE FORMADA POR UNA BASE DE CREMA DE LIMON.','A','9','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('QUEQUE DE PI?A', '35,0', 'ESTE ES UN DELICIOSO QUEQUE CON UN CUBIERTO DE CARAMELO.','A','10','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('QUEQUE INGLES',' 43,0','ESTA PREPARADA CON FRUTAS CON FRUTAS SECAS Y NUECES.','A','1','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('QUEQUE CHIFFON', '45,0','ES UN QUEQUE MUY LIGERO.','I','2','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('PIE DE MANZANA', '50,0','CONSISTE EN UNA MASA SUAVE Y CRUJIENTE DE MANZANAS.','A','3','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('ALFAJORES', '16,0','ESTA RELLENAS DE JALEAS.','I','4','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('CUPCAKES', '21,0', 'ES UNA PEQUE?A TARTA INDIVIDUAL.','I','5','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('EMPANADA','23,0','ESTA EMPANADA INCLUYE FRUTAS.','I','6','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('QUEQUE DE CAFE', '54,0','ESTE TIPO DE QUEQUE INCLUYE EL FUERTE SABOR AL CAFE COMO PARTE DE SU PERFIL DE SABOR.','I','7','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('QUEQUE MARMOLADO', '23,0','ESTA COMPUESTO POR UN BIZCOCHO DE VAINILLA Y UNA DE CHOCOLATE.','A','8','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('QUEQUE DE MANDARINA','15,0','SE TRATA DE UN QUEQUE FACIL Y ESPONJOSO.','I','9','10');
INSERT INTO PRODUCTO(NOMPRO,PREPRO,DESPRO,ESTPRO,IDPROV,STOPRO) VALUES ('QUEQUE CON LECHE CONDENSADA','25,0','ESTE QUEQUE CON LECHE CONDESADA ES SENCILLA Y DELICIOSA.','A','10','10');


-- INSERTAR REGISTROS DE venta

INSERT INTO VENTA(FECVEN,IDEMP,IDCLI,TOTVEN) VALUES ('10/01/2022','1','1','114');
INSERT INTO VENTA(FECVEN,IDEMP,IDCLI,TOTVEN) VALUES ('15/01/2022','3','2','108');
INSERT INTO VENTA(FECVEN,IDEMP,IDCLI,TOTVEN) VALUES ('12/01/2022','2','3','55');
INSERT INTO VENTA(FECVEN,IDEMP,IDCLI,TOTVEN) VALUES ('03/02/2022','3','4','114');
INSERT INTO VENTA(FECVEN,IDEMP,IDCLI,TOTVEN) VALUES ('21/03/2022','1','5','162');
INSERT INTO VENTA(FECVEN,IDEMP,IDCLI,TOTVEN) VALUES ('19/03/2022','2','6','165');
INSERT INTO VENTA(FECVEN,IDEMP,IDCLI,TOTVEN) VALUES ('18/03/2022','3','7','57');
INSERT INTO VENTA(FECVEN,IDEMP,IDCLI,TOTVEN) VALUES ('16/04/2022','5','8','108');
INSERT INTO VENTA(FECVEN,IDEMP,IDCLI,TOTVEN) VALUES ('14/04/2022','2','9','165');
INSERT INTO VENTA(FECVEN,IDEMP,IDCLI,TOTVEN) VALUES ('05/05/2022','2','10','57');


-- INSERTAR REGISTROS DE venta detalle

INSERT INTO VENTA_DETALLE (CANVENDET,IDVEN,IDPRO,SUBVENDET) VALUES ('2','1','1','114');
INSERT INTO VENTA_DETALLE (CANVENDET,IDVEN,IDPRO,SUBVENDET) VALUES ('2','2','2','108');
INSERT INTO VENTA_DETALLE (CANVENDET,IDVEN,IDPRO,SUBVENDET) VALUES ('1','3','3','55');
INSERT INTO VENTA_DETALLE (CANVENDET,IDVEN,IDPRO,SUBVENDET) VALUES ('2','4','1','114');
INSERT INTO VENTA_DETALLE (CANVENDET,IDVEN,IDPRO,SUBVENDET) VALUES ('3','5','2','162');
INSERT INTO VENTA_DETALLE (CANVENDET,IDVEN,IDPRO,SUBVENDET) VALUES ('2','6','3','165');
INSERT INTO VENTA_DETALLE (CANVENDET,IDVEN,IDPRO,SUBVENDET) VALUES ('1','7','1','57');
INSERT INTO VENTA_DETALLE (CANVENDET,IDVEN,IDPRO,SUBVENDET) VALUES ('2','8','2','108');
INSERT INTO VENTA_DETALLE (CANVENDET,IDVEN,IDPRO,SUBVENDET) VALUES ('3','9','3','165');
INSERT INTO VENTA_DETALLE (CANVENDET,IDVEN,IDPRO,SUBVENDET) VALUES ('1','10','1','57');

-- INSERTAR REGISTROS DE COMPRA

INSERT INTO COMPRA (FECCOM,IDPROV,TOTCOM,IDEMP) VALUES ('20/05/2022','1','114','5');
INSERT INTO COMPRA (FECCOM,IDPROV,TOTCOM,IDEMP) VALUES ('21/05/2022','2','275','4');
INSERT INTO COMPRA (FECCOM,IDPROV,TOTCOM,IDEMP) VALUES ('11/06/2022','3','108','2');
INSERT INTO COMPRA (FECCOM,IDPROV,TOTCOM,IDEMP) VALUES ('13/06/2022','4','120','1');
INSERT INTO COMPRA (FECCOM,IDPROV,TOTCOM,IDEMP) VALUES ('15/06/2022','5','43','3');
INSERT INTO COMPRA (FECCOM,IDPROV,TOTCOM,IDEMP) VALUES ('20/06/2022','6','228','6');
INSERT INTO COMPRA (FECCOM,IDPROV,TOTCOM,IDEMP) VALUES ('21/06/2022','7','275','8');
INSERT INTO COMPRA (FECCOM,IDPROV,TOTCOM,IDEMP) VALUES ('03/07/2022','8','160','7');
INSERT INTO COMPRA (FECCOM,IDPROV,TOTCOM,IDEMP) VALUES ('07/07/2022','9','135','9');
INSERT INTO COMPRA (FECCOM,IDPROV,TOTCOM,IDEMP) VALUES ('10/07/2022','10','106','10');

-- INSERTAR REGISTROS DE COMPRA detalle

INSERT INTO COMPRA_DETALLE (CANCOMDET,IDCOM,IDPRO,SUBCOMDET) VALUES ('2','1','1','114');
INSERT INTO COMPRA_DETALLE (CANCOMDET,IDCOM,IDPRO,SUBCOMDET) VALUES ('5','2','3','275');
INSERT INTO COMPRA_DETALLE (CANCOMDET,IDCOM,IDPRO,SUBCOMDET) VALUES ('2','3','2','108');
INSERT INTO COMPRA_DETALLE (CANCOMDET,IDCOM,IDPRO,SUBCOMDET) VALUES ('3','4','5','120');
INSERT INTO COMPRA_DETALLE (CANCOMDET,IDCOM,IDPRO,SUBCOMDET) VALUES ('1','5','9','43');
INSERT INTO COMPRA_DETALLE (CANCOMDET,IDCOM,IDPRO,SUBCOMDET) VALUES ('4','6','1','228');
INSERT INTO COMPRA_DETALLE (CANCOMDET,IDCOM,IDPRO,SUBCOMDET) VALUES ('5','7','3','275');
INSERT INTO COMPRA_DETALLE (CANCOMDET,IDCOM,IDPRO,SUBCOMDET) VALUES ('4','8','5','160');
INSERT INTO COMPRA_DETALLE (CANCOMDET,IDCOM,IDPRO,SUBCOMDET) VALUES ('3','9','6','135');
INSERT INTO COMPRA_DETALLE (CANCOMDET,IDCOM,IDPRO,SUBCOMDET) VALUES ('2','10','7','106');


-- Vista para CLIENTE 
CREATE VIEW vCLIENTE
AS
SELECT
	 C.IDCLI AS IDCLI,
	 INITCAP(C.NOMCLI) AS NOMCLI,
	 INITCAP(C.APEPATCLI) AS APEPATCLI,
     INITCAP(C.APEMATCLI) AS APEMATCLI,
	 INITCAP(C.DOMCLI) AS DOMCLI,
	 C.DNICLI AS DNICLI,
	 C.EMACLI AS EMACLI,
	 C.TELCLI AS TELCLI,
	 C.ESTCLI AS ESTCLI,
	 U.CODUBI AS CODUBI,
	 INITCAP(U.DISUBI) AS DISUBI,
	 INITCAP(U.PROUBI) AS PROUBI,
	 INITCAP(U.DEPUBI) AS DEPUBI,
	 ROW_NUMBER() OVER (ORDER BY C.IDCLI DESC) AS ORDEN
FROM 
     CLIENTE C
	 INNER JOIN UBIGEO U ON
	 C.CODUBI = U.CODUBI;

-- Vista para EMPLEADO 
CREATE VIEW vEMPLEADO
AS
SELECT
	 E.IDEMP AS IDEMP,
     INITCAP(E.NOMEMP) AS NOMEMP,
     INITCAP(E.APEPATEMP) AS APEPATEMP,
     INITCAP(E.APEMATEMP) AS APEMATEMP,
	 E.DNIEMP AS DNIEMP,
	 E.TELEMP AS TELEMP,
	 E.EMAEMP AS EMAEMP,
	 E.ROLEMP AS ROLEMP,
	 E.ESTEMP AS ESTEMP,
	 U.CODUBI AS CODUBI,
	 INITCAP(U.DISUBI) AS DISUBI,
	 INITCAP(U.PROUBI) AS PROUBI,
	 INITCAP(U.DEPUBI) AS DEPUBI,
	 ROW_NUMBER() OVER (ORDER BY E.IDEMP DESC) AS ORDEN
FROM 
     EMPLEADO E
	 INNER JOIN UBIGEO U ON
	 E.CODUBI = U.CODUBI;


-- Vista para PRODUCTO
CREATE VIEW vPRODUCTO
AS
SELECT
	 P.IDPRO AS IDPRO,
     INITCAP(P.NOMPRO) AS NOMPRO,
     P.PREPRO AS PREPRO,
     INITCAP(P.DESPRO) AS DESPRO,
	 P.STOPRO AS STOPRO,
     PV.IDPROV AS IDPROV,
     PV.RAZPROV AS RAZPROV,
     P.ESTPRO AS ESTPRO,
	 ROW_NUMBER() OVER (ORDER BY P.IDPRO DESC) AS ORDEN
FROM 
     PRODUCTO P INNER JOIN PROVEEDOR PV ON
     P.IDPROV = PV.IDPROV;
     

-- Vista para PROVEEDOR
CREATE VIEW vPROVEEDOR
AS
SELECT
	 P.IDPROV AS IDPROV,
	 INITCAP(P.RAZPROV) AS RAZPROV,
     INITCAP(P.DIRPROV) AS DIRPROV,
     P.CELPROV AS CELPROV,
     P.RUCPROV AS RUCPROV,
     P.EMAPROV AS EMAPROV,
     U.CODUBI AS CODUBI,
	 INITCAP(U.DISUBI) AS DISUBI,
	 INITCAP(U.PROUBI) AS PROUBI,
	 INITCAP(U.DEPUBI) AS DEPUBI,
     P.ESTPROV AS ESTPROV,
	 ROW_NUMBER() OVER (ORDER BY P.IDPROV DESC) AS ORDEN
FROM 
     PROVEEDOR P
	 INNER JOIN UBIGEO U ON
	 P.CODUBI = U.CODUBI;

-- Vista para venta 
CREATE VIEW vVENTA
AS
SELECT
	V.IDVEN AS IDVEN,
	V.FECVEN AS FECVEN,
	V.IDCLI AS IDCLI,
	C.DNICLI AS DNICLI,
    INITCAP((C.APEPATCLI) || ' ' || (C.APEMATCLI) || ', ' || (C.NOMCLI)) AS NOMCLI,
	V.TOTVEN AS TOTVEN,
    ROW_NUMBER() OVER (ORDER BY V.IDVEN DESC) AS ORDEN
FROM VENTA V
	INNER JOIN CLIENTE C
	ON V.IDCLI = C.IDCLI;


-- Vista para Compra
CREATE VIEW vCOMPRA
AS
SELECT
	C.IDCOM AS IDCOM,
	C.FECCOM AS FECCOM,
	P.IDPROV AS IDPROV,
	P.RUCPROV AS RUCPROV,
    P.RAZPROV AS RAZPROV,
    E.IDEMP AS IDEMP,
	C.TOTCOM AS TOTCOM,
    INITCAP((E.APEPATEMP)|| ' ' || (E.APEMATEMP) || ', ' || (E.NOMEMP)) AS NOMEMP,
    ROW_NUMBER() OVER (ORDER BY C.IDCOM DESC) AS ORDEN
FROM COMPRA C
	INNER JOIN PROVEEDOR P
	ON C.IDPROV = P.IDPROV
    INNER JOIN EMPLEADO E
	ON C.IDEMP = E.IDEMP;
    
    
    
-- Vista para venta Detalle
CREATE VIEW vVENTADETALLE
AS
SELECT  
V.IDVEN AS IDVEN,
V.FECVEN AS FECVEN,
V.TOTVEN AS TOTVEN,
INITCAP((C.APEPATCLI) || ' ' || (C.APEMATCLI) || ', ' || (C.NOMCLI)) AS NOMBRE,
C.DNICLI AS DNICLI,
P.NOMPRO AS NOMPRO,
P.PREPRO AS PREPRO,
VD.CANVENDET AS CANVENDET,
VD.SUBVENDET AS SUBVENDET

FROM VENTA_DETALLE VD
INNER JOIN PRODUCTO P ON P.IDPRO = VD.IDPRO
INNER JOIN VENTA V ON V.IDVEN = VD.IDVEN
INNER JOIN CLIENTE C ON C.IDCLI = V.IDCLI;

--Vista para COMPRADETALLE
CREATE VIEW vCOMPRADETALLE
AS
SELECT  
CO.IDCOM AS IDCOM,
CO.FECCOM AS FECCOM,
CO.TOTCOM AS TOTCOM,
INITCAP((E.APEPATEMP)|| ' ' || (E.APEMATEMP) || ', ' || (E.NOMEMP)) AS NOMEMP,
PR.IDPROV AS IDPROV,
PR.RAZPROV AS RAZPROV,
PR.RUCPROV AS RUCPROV,
PR.DIRPROV AS DIRPROV,
PR.CELPROV AS CELPROV,
PR.EMAPROV AS EMAPROV,
E.DNIEMP AS DNIEMP,
P.NOMPRO AS NOMPRO,
P.PREPRO AS PREPRO,
CD.CANCOMDET AS CANCOMDET,
CD.SUBCOMDET AS SUBCOMDET

FROM COMPRA_DETALLE CD
INNER JOIN PRODUCTO P ON P.IDPRO = CD.IDPRO
INNER JOIN COMPRA CO ON CO.IDCOM = CD.IDCOM
INNER JOIN PROVEEDOR PR ON PR.IDPROV = CO.IDPROV
INNER JOIN EMPLEADO E ON E.IDEMP = CO.IDEMP;



	
