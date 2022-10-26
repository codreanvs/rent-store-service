CREATE SEQUENCE "#[schema_name]"."ORDER_ITEMS_ID_SEQUENCE"
    MINVALUE 1
    MAXVALUE #[sequence_max_value]
    INCREMENT BY 1
    START WITH 1
    NOCYCLE;

CREATE TABLE "#[schema_name]"."ORDER_ITEMS" (
    "ID"                NUMBER          DEFAULT "#[schema_name]"."ORDER_ITEMS_ID_SEQUENCE".NEXTVAL,
    "ORDER_ID"          NUMBER          NOT NULL,
    "MOVIE_ID"          NUMBER          NOT NULL,
    "QUANTITY"          NUMBER          NOT NULL,
    "UNIT_PRICE"        NUMBER(6, 2)    NOT NULL,
    "CURRENCY"          VARCHAR2(20)    NOT NULL,

    PRIMARY KEY (ID),
    CONSTRAINT "#[schema_name]_ORDER_ITEMS_ORDER_ID_FK_CONSTRAINT" FOREIGN KEY (ORDER_ID) REFERENCES "#[schema_name]"."ORDERS" (ID),
    CONSTRAINT "#[schema_name]_ORDER_ITEMS_MOVIE_ID_FK_CONSTRAINT" FOREIGN KEY (MOVIE_ID) REFERENCES "#[schema_name]"."MOVIES" (ID)
);
