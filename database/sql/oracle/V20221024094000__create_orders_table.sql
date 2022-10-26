CREATE SEQUENCE "#[schema_name]"."ORDERS_ID_SEQUENCE"
    MINVALUE 1
    MAXVALUE #[sequence_max_value]
    INCREMENT BY 1
    START WITH 1
    NOCYCLE;

CREATE TABLE "#[schema_name]"."ORDERS" (
    "ID"                NUMBER          DEFAULT "#[schema_name]"."ORDERS_ID_SEQUENCE".NEXTVAL,
    "UUID"              VARCHAR2(50)    NOT NULL,
    "RENTAL_ID"         NUMBER          NOT NULL,
    "TOTAL_PRICE"       NUMBER(6, 2)    NOT NULL,
    "CURRENCY"          VARCHAR2(20)    NOT NULL,

    PRIMARY KEY (ID),
    CONSTRAINT "#[schema_name]_ORDERS_UUID_UNIQUE_CONSTRAINT" UNIQUE (UUID),
    CONSTRAINT "#[schema_name]_ORDERS_RENTAL_ID_FK_CONSTRAINT" FOREIGN KEY (RENTAL_ID) REFERENCES "#[schema_name]"."RENTALS" (ID)
);
