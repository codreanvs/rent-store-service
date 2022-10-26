CREATE SEQUENCE "#[schema_name]"."TRANSACTIONS_ID_SEQUENCE"
    MINVALUE 1
    MAXVALUE #[sequence_max_value]
    INCREMENT BY 1
    START WITH 1
    NOCYCLE;

CREATE TABLE "#[schema_name]"."TRANSACTIONS" (
    "ID"                NUMBER          DEFAULT "#[schema_name]"."TRANSACTIONS_ID_SEQUENCE".NEXTVAL,
    "UUID"              VARCHAR2(50)    NOT NULL,
    "ORDER_ID"          NUMBER          NOT NULL,
    "STATUS"            VARCHAR(50)     NOT NULL,
    "DESCRIPTION"       VARCHAR(500),

    PRIMARY KEY (ID),
    CONSTRAINT "#[schema_name]_TRANSACTIONS_UUID_UNIQUE_CONSTRAINT" UNIQUE (UUID),
    CONSTRAINT "#[schema_name]_TRANSACTIONS_ORDER_ID_FK_CONSTRAINT" FOREIGN KEY (ORDER_ID) REFERENCES "#[schema_name]"."ORDERS" (ID)
);
