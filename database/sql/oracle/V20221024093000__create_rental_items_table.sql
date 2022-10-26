CREATE SEQUENCE "#[schema_name]"."RENTAL_ITEMS_ID_SEQUENCE"
    MINVALUE 1
    MAXVALUE #[sequence_max_value]
    INCREMENT BY 1
    START WITH 1
    NOCYCLE;

CREATE TABLE "#[schema_name]"."RENTAL_ITEMS" (
    "ID"                NUMBER          DEFAULT "#[schema_name]"."RENTAL_ITEMS_ID_SEQUENCE".NEXTVAL,
    "RENTAL_ID"         NUMBER          NOT NULL,
    "MOVIE_ID"          NUMBER          NOT NULL,
    "QUANTITY"          NUMBER          NOT NULL,
    "UNIT_PRICE"        NUMBER(6, 2)    NOT NULL,
    "CURRENCY"          VARCHAR2(20)    NOT NULL,
    "START_DATE"        TIMESTAMP       DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "END_DATE"          TIMESTAMP       NOT NULL,
    "STATUS"            VARCHAR(50)     NOT NULL,

    PRIMARY KEY (ID),
    CONSTRAINT "#[schema_name]_RENTAL_ITEMS_RENTAL_ID_FK_CONSTRAINT" FOREIGN KEY (RENTAL_ID) REFERENCES "#[schema_name]"."RENTALS" (ID),
    CONSTRAINT "#[schema_name]_RENTAL_ITEMS_MOVIE_ID_FK_CONSTRAINT" FOREIGN KEY (MOVIE_ID) REFERENCES "#[schema_name]"."MOVIES" (ID)
);
