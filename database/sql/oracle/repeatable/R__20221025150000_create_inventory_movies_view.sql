CREATE OR REPLACE VIEW "#[schema_name]"."INVENTORY_MOVIES" (
    "UUID",
    "START_DATE",
    "END_DATE",
    "MOVIE_TYPE",
    "NAME",
    "DAYS",
    "QUANTITY",
    "UNIT_PRICE",
    "TOTAL_PRICE",
    "CURRENCY"
)
AS
SELECT
    m.UUID,
    ri.START_DATE,
    ri.END_DATE ,
    m.MOVIE_TYPE,
    m.NAME,
    (TO_DATE(SUBSTR(ri.END_DATE, 0, 17), 'dd-mm-yyyy hh24:mi:ss')
        - TO_DATE(SUBSTR(ri.START_DATE, 0, 17), 'dd-mm-yyyy hh24:mi:ss')
    ) DAYS,
    ri.QUANTITY,
    ri.UNIT_PRICE,
    (ri.UNIT_PRICE * ri.QUANTITY *
        (TO_DATE(SUBSTR(ri.END_DATE, 0, 17), 'dd-mm-yyyy hh24:mi:ss')
        - TO_DATE(SUBSTR(ri.START_DATE, 0, 17), 'dd-mm-yyyy hh24:mi:ss')
        )
    ) TOTAL_PRICE,
    ri.CURRENCY
FROM
    "#[schema_name]".RENTAL_ITEMS ri
    INNER JOIN "#[schema_name]".MOVIES m ON ri.MOVIE_ID = m.ID
    AND ri.STATUS = 'RENT_INIT';
