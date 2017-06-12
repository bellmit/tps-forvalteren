-----------------
-- T A B L E S --
-----------------
CREATE TABLE T_PERSON (
  PERSON_ID       NUMBER(9)              NOT NULL,
  IDENT           VARCHAR2(11)           NOT NULL,
  IDENTTYPE       VARCHAR2(3)            NOT NULL,
  KJONN           VARCHAR2(1)            NOT NULL,
  FORNAVN         VARCHAR2(50)           NOT NULL,
  MELLOMNAVN      VARCHAR2(50),
  ETTERNAVN       VARCHAR2(50)           NOT NULL,
  STATSBORGERSKAP VARCHAR2(3),
  REGDATO         DATE                   NOT NULL,
  OPPRETTET_DATO  DATE                   NOT NULL,
  OPPRETTET_AV    VARCHAR2(20)           NOT NULL,
  ENDRET_DATO     DATE                   NOT NULL,
  ENDRET_AV       VARCHAR2(20)           NOT NULL,
  SPESREG         VARCHAR2(4),
  SPESREG_DATO    DATE
);

CREATE TABLE T_GATEADRESSE (
  GATEADRESSE_ID  NUMBER(9)              NOT NULL,
  K_PERSON_ID     NUMBER(9)              NOT NULL,
  BO_GATEADRESSE  VARCHAR2(50),
  BO_HUSNUMMER    VARCHAR2(4),
  BO_GATEKODE     VARCHAR2(5),
  BO_POSTNUMMER   VARCHAR2(4),
  BO_KOMMUNENR    VARCHAR2(4),
  BO_FLYTTE_DATO  DATE
);

CREATE TABLE T_POSTADRESSE (
  POSTADRESSE_ID  NUMBER(9)              NOT NULL,
  K_PERSON_ID     NUMBER(9)              NOT NULL,
  POST_LINJE_1    VARCHAR2(30),
  POST_LINJE_2    VARCHAR2(30),
  POST_LINJE_3    VARCHAR2(30),
  POST_LAND       VARCHAR2(3)
);

---------------------------------------------------
-- P R I M A R Y   K E Y   C O N S T R A I N T S --
---------------------------------------------------
ALTER TABLE T_PERSON      ADD CONSTRAINT T_PERSON_PK      PRIMARY KEY (PERSON_ID);
ALTER TABLE T_GATEADRESSE ADD CONSTRAINT T_GATEADRESSE_PK PRIMARY KEY (GATEADRESSE_ID);
ALTER TABLE T_POSTADRESSE ADD CONSTRAINT T_POSTADRESSE_PK PRIMARY KEY (POSTADRESSE_ID);

---------------------------------------------------
-- F O R E I G N   K E Y   C O N S T R A I N T S --
---------------------------------------------------
ALTER TABLE T_GATEADRESSE ADD CONSTRAINT GATEADRESSE_PERSON_FK FOREIGN KEY (K_PERSON_ID) REFERENCES T_PERSON (PERSON_ID);
ALTER TABLE T_POSTADRESSE ADD CONSTRAINT POSTADRESSE_PERSON_FK FOREIGN KEY (K_PERSON_ID) REFERENCES T_PERSON (PERSON_ID);

-----------------------
-- S E Q U E N C E S --
-----------------------
CREATE SEQUENCE T_PERSON_SEQ          START WITH 100000000;
CREATE SEQUENCE T_GATEADRESSE_SEQ     START WITH 100000000;
CREATE SEQUENCE T_POSTADRESSE_SEQ     START WITH 100000000;