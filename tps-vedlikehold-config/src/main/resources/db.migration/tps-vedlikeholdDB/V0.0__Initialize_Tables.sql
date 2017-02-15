-----------------
-- T A B L E S --
-----------------
CREATE TABLE T_TEST_PERSON (
  FORNAVN        VARCHAR2(100)         NOT NULL,
  SLEKTSNAVN     VARCHAR2(100)         NOT NULL,
  MELLOMNAVN     VARCHAR2(100),
  PERSONNUMMER   VARCHAR2(5)           NOT NULL,
  FODSELSNUMMER  VARCHAR2(6)           NOT NULL
);

-----------------------
-- S E Q U E N C E S --
-----------------------
CREATE SEQUENCE T_PERSON_SEQUENCE     START WITH 100000000;

/*CREATE TABLE T_TEST_PERSON (
  KODE           VARCHAR2(20)           NOT NULL,
  DEKODE         VARCHAR2(200)          NOT NULL,
  DATO_FOM       DATE                   NOT NULL,
  DATO_TOM       DATE,
  DATO_OPPRETTET TIMESTAMP(6)           NOT NULL,
  OPPRETTET_AV   VARCHAR2(20)           NOT NULL,
  DATO_ENDRET    TIMESTAMP(6)           NOT NULL,
  ENDRET_AV      VARCHAR2(20)           NOT NULL,
  ER_GYLDIG      CHAR(1)                NOT NULL
);
*/
