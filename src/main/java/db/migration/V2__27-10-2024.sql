select constraint_name from information_schema.constraint_column_usage 
where table_name = 'usuarios_acessos' and column_name = 'acesso_id'
and constraint_name <> 'unique_acesso_user';


DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'ukqs91qokws6i46m1vnsoakivh1') THEN
        ALTER TABLE usuarios_acessos DROP CONSTRAINT "ukqs91qokws6i46m1vnsoakivh1";
    END IF;
END $$;
