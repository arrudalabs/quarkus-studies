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

-- Table: public.roleNames

-- DROP TABLE IF EXISTS public.roleNames;
CREATE TABLE IF NOT EXISTS public.roleNames
(
    usr  character(15) COLLATE pg_catalog."default" NOT NULL,
    roleName character(3) COLLATE pg_catalog."default"  NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (usr, roleName),
    CONSTRAINT fk_user FOREIGN KEY (usr) REFERENCES public.usrs (usr) MATCH FULL
    ON UPDATE CASCADE
    ON DELETE CASCADE
    )
    TABLESPACE pg_default;
