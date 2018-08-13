CREATE TABLE T_SKD_MELDINGER_AVSPILLERDATA (
  ID            NUMBER(10) PRIMARY KEY,
  SKD_MELDING   VARCHAR2(1600 CHAR) NOT NULL,
  TESTPERSON_ID NUMBER(9),
  GRUPPE_ID     NUMBER(9)           NOT NULL,
  SEKVENSNUMMER NUMBER(10)          NOT NULL,
  AARSAKSKODE   NUMBER(2)           NOT NULL
);

CREATE TABLE T_AVSPILLERGRUPPE (
  GRUPPE_ID      NUMBER(9)    PRIMARY KEY,
  NAVN           VARCHAR2(50) NOT NULL,
  BESKRIVELSE    VARCHAR2(200),
  OPPRETTET_DATO TIMESTAMP    NOT NULL,
  OPPRETTET_AV   VARCHAR2(20) NOT NULL,
  ENDRET_DATO    TIMESTAMP    NOT NULL,
  ENDRET_AV      VARCHAR2(20) NOT NULL
);

ALTER TABLE T_SKD_MELDINGER_AVSPILLERDATA
  ADD CONSTRAINT AVSPILLERGRUPPE_ID_FK FOREIGN KEY (GRUPPE_ID) REFERENCES T_AVSPILLERGRUPPE (GRUPPE_ID);

CREATE SEQUENCE T_SKD_AVSPILLERDATA_SEQ
  START WITH 1000000000;