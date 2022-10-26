CREATE SEQUENCE "#[schema_name]"."RENTALS_ID_SEQUENCE"
    MINVALUE 1
    MAXVALUE #[sequence_max_value]
    INCREMENT BY 1
    START WITH 1
    NOCYCLE;

CREATE TABLE "#[schema_name]"."RENTALS" (
    "ID"                NUMBER          DEFAULT "#[schema_name]"."RENTALS_ID_SEQUENCE".NEXTVAL,
    "UUID"              VARCHAR2(50)    NOT NULL,
    "STATUS"            VARCHAR(50)     NOT NULL,

    PRIMARY KEY (ID),
    CONSTRAINT "#[schema_name]_RENTALS_UUID_UNIQUE_CONSTRAINT" UNIQUE (UUID)
);
