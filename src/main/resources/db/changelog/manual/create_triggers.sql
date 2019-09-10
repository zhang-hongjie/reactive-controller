CREATE OR REPLACE FUNCTION log_into_datachangelog() RETURNS trigger AS $$
begin
    insert into public.datachangelog(action_type, table_name, row_id, json_old, json_new)
    select TG_OP, TG_TABLE_NAME,
           case
               when TG_OP='DELETE' then OLD.oid
               when TG_OP='INSERT' or TG_OP='UPDATE' then new.oid
               else null
               end,
           case
               when TG_OP='DELETE' or TG_OP='UPDATE' then row_to_json(OLD.*)
               else null
               end,
           case
               when TG_OP='INSERT' or TG_OP='UPDATE' then row_to_json(NEW.*)
               else null
               end;
    RETURN NULL;
END;
$$  LANGUAGE plpgsql;


DO $$
    DECLARE
        tableName text;
        query text;
    begin
        FOR tableName IN select table_name from information_schema.tables where table_schema='public' and table_name not like '%_aud' and table_name not in ('databasechangelog', 'databasechangeloglock', 'datachangelog', 'revinfo')
            loop
                RAISE INFO 'tableName = %', tableName;

                query := format('alter table %1$I set with oids;', quote_ident(tableName));
                RAISE INFO 'query = %', query;
                EXECUTE query;

                query := format('DROP TRIGGER IF EXISTS trigger_log_into_datachangelog_%1$s ON %1$I restrict;', quote_ident(tableName));
                RAISE INFO 'query = %', query;
                EXECUTE query;

                query := format('CREATE TRIGGER trigger_log_into_datachangelog_%1$s after delete or insert or update ON %1$I FOR EACH ROW EXECUTE PROCEDURE log_into_datachangelog();
', quote_ident(tableName));
                RAISE INFO 'query = %', query;
                EXECUTE query;

            END LOOP;
    END
$$;