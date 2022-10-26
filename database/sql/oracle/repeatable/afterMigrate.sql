BEGIN
    FOR obj IN (SELECT object_name, object_type
        FROM all_objects
        WHERE
            owner = '#[schema_name]'
            AND object_type IN ('SEQUENCE', 'TABLE', 'VIEW')
            AND object_name NOT LIKE 'flyway_schema_history'
        )
        LOOP
            IF obj.object_type IN ('TABLE', 'VIEW') THEN
                EXECUTE IMMEDIATE 'GRANT SELECT, INSERT, UPDATE, DELETE ON #[schema_name].' || obj.object_name || ' TO #[schema_user]';
            ELSIF obj.object_type IN ('SEQUENCE') THEN
                EXECUTE IMMEDIATE 'GRANT SELECT, ALTER ON #[schema_name].' || obj.object_name || ' TO #[schema_user]';
            END IF;
            EXECUTE IMMEDIATE 'CREATE OR REPLACE SYNONYM #[schema_user].' || obj.object_name || ' FOR #[schema_name].' || obj.object_name;
        END LOOP;

    EXECUTE IMMEDIATE 'GRANT SELECT ON "#[schema_name]"."flyway_schema_history" TO "#[schema_user]"';
END;
