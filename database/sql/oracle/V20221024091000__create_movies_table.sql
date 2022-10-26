CREATE SEQUENCE "#[schema_name]"."MOVIES_ID_SEQUENCE"
    MINVALUE 1
    MAXVALUE #[sequence_max_value]
    INCREMENT BY 1
    START WITH 1
    NOCYCLE;

CREATE TABLE "#[schema_name]"."MOVIES" (
    "ID"                NUMBER          DEFAULT "#[schema_name]"."MOVIES_ID_SEQUENCE".NEXTVAL,
    "UUID"              VARCHAR2(50)    NOT NULL,
    "MOVIE_TYPE"        VARCHAR2(50)    NOT NULL,
    "NAME"              VARCHAR2(50)    NOT NULL,
    "STOCK_QUANTITY"    NUMBER          NOT NULL,
    "UNIT_PRICE"        NUMBER(6, 2)    NOT NULL,
    "CURRENCY"          VARCHAR2(20)    NOT NULL,

    PRIMARY KEY (ID),
    CONSTRAINT "#[schema_name]_MOVIES_UUID_UNIQUE_CONSTRAINT" UNIQUE (UUID)
);
