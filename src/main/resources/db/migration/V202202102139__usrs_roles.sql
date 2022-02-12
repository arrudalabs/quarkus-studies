-- Table: public.usrs

-- DROP TABLE IF EXISTS public.usrs;
CREATE TABLE IF NOT EXISTS public.usrs
(
    usr  character(15) COLLATE pg_catalog."default"  NOT NULL,
    pass character(256) COLLATE pg_catalog."default" NOT NULL,
    seed character(15) COLLATE pg_catalog."default"  NOT NULL,
    CONSTRAINT usr_pkey PRIMARY KEY (usr)
    )
    TABLESPACE pg_default;

-- Table: public.roles

-- DROP TABLE IF EXISTS public.roles;
CREATE TABLE IF NOT EXISTS public.roles
(
    usr  character(15) COLLATE pg_catalog."default" NOT NULL,
    role_name character(3) COLLATE pg_catalog."default"  NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (usr, role_name),
    CONSTRAINT fk_user FOREIGN KEY (usr) REFERENCES public.usrs (usr) MATCH FULL
    ON UPDATE CASCADE
    ON DELETE CASCADE
    )
    TABLESPACE pg_default;
