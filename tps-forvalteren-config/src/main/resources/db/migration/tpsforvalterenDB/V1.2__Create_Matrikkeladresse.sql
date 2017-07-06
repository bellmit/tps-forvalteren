CREATE TABLE T_MATRIKKELADRESSE (
  ID         NUMBER(9) NOT NULL,
  PERSON_ID  NUMBER(9) NOT NULL,
  MELLOMNAVN VARCHAR2(25),
  GARDSNR    VARCHAR2(5),
  BRUKSNR    VARCHAR2(4),
  FESTENR    VARCHAR2(4),
  UNDERNR    VARCHAR2(3),
  POSTNR     VARCHAR2(4),
  KOMMUNENR  VARCHAR2(4),
  FLYTTE_DATO DATE
);

---------------------------------------------------
-- P R I M A R Y   K E Y   C O N S T R A I N T S --
---------------------------------------------------
ALTER TABLE T_MATRIKKELADRESSE
  ADD CONSTRAINT T_MATRIKKELADRESSE_PK PRIMARY KEY (ID);

---------------------------------------------------
-- F O R E I G N   K E Y   C O N S T R A I N T S --
---------------------------------------------------
ALTER TABLE T_MATRIKKELADRESSE
  ADD CONSTRAINT MATRIKKELADRESSE_PERSON_FK FOREIGN KEY (PERSON_ID) REFERENCES T_PERSON (PERSON_ID);