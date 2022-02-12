-- Table: public.alunos
-- DROP TABLE IF EXISTS public.alunos;

CREATE TABLE IF NOT EXISTS public.alunos
(
    matricula   bigint                                             NOT NULL,
    fpk         numeric(8, 0),
    cbk         numeric(8, 0),
    nome        character(40) COLLATE pg_catalog."default"         NOT NULL,
    apelido     character(10) COLLATE pg_catalog."default"         NOT NULL,
    dt_nasc     date                                               NOT NULL,
    altura_cm   smallint,
    peso_kg     smallint,
    endereco    character varying(40) COLLATE pg_catalog."default" NOT NULL,
    rg          character varying(12) COLLATE pg_catalog."default",
    cpf         numeric(11, 0),
    pai         character varying(40) COLLATE pg_catalog."default",
    mae         character varying(40) COLLATE pg_catalog."default" NOT NULL,
    st_civil    character(3) COLLATE pg_catalog."default"          NOT NULL,
    profissao   character(20) COLLATE pg_catalog."default"         NOT NULL DEFAULT 'estudante'::bpchar,
    tel_resid   numeric(11, 0),
    tel_com     numeric(11, 0),
    tel_celular numeric(11, 0),
    email       character varying(40) COLLATE pg_catalog."default",
    dt_examemed date,
    dose_covid  smallint,
    dt_dose     date,
    obs         character varying(100) COLLATE pg_catalog."default",
    foto        path,
    CONSTRAINT pkalunos PRIMARY KEY (matricula)
)
    TABLESPACE pg_default;

-- Index: apelido

-- DROP INDEX IF EXISTS public.apelido;

CREATE INDEX IF NOT EXISTS apelido
    ON public.alunos USING btree
        (apelido COLLATE pg_catalog."C" bpchar_pattern_ops ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: nome

-- DROP INDEX IF EXISTS public.nome;

CREATE INDEX IF NOT EXISTS nome
    ON public.alunos USING btree
        (nome COLLATE pg_catalog."C" bpchar_pattern_ops ASC NULLS LAST)
    TABLESPACE pg_default;

-- Table: public.turmas

-- DROP TABLE IF EXISTS public.turmas;

CREATE TABLE IF NOT EXISTS public.turmas
(
    cd_turma smallint                                   NOT NULL,
    descr    character(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT turmas_pkey PRIMARY KEY (cd_turma)
)
    TABLESPACE pg_default;


-- Table: public.turmas_alunos

-- DROP TABLE IF EXISTS public.turmas_alunos;

CREATE TABLE IF NOT EXISTS public.turmas_alunos
(
    matricula bigint   NOT NULL,
    cd_turma  smallint NOT NULL,
    CONSTRAINT turmas_alunos_pkey PRIMARY KEY (matricula, cd_turma),
    CONSTRAINT fk_matricula FOREIGN KEY (matricula)
        REFERENCES public.alunos (matricula) MATCH FULL
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_turma FOREIGN KEY (cd_turma)
        REFERENCES public.turmas (cd_turma) MATCH FULL
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    TABLESPACE pg_default;

-- Index: fki_fk_turma

-- DROP INDEX IF EXISTS public.fki_fk_turma;

CREATE INDEX IF NOT EXISTS fki_fk_turma
    ON public.turmas_alunos USING btree
        (cd_turma ASC NULLS LAST)
    TABLESPACE pg_default;

-- Table: public.exames_alunos

-- DROP TABLE IF EXISTS public.exames_alunos;

CREATE TABLE IF NOT EXISTS public.exames_alunos
(
    matricula  bigint       NOT NULL,
    dt_exame   date         NOT NULL,
    kyu_dan    character(2) NOT NULL,
    kihon      numeric(3, 2),
    kata       numeric(3, 2),
    shiai      numeric(3, 2),
    examinador character(30) COLLATE pg_catalog."default",
    obs        character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT exames_alunos_pkey PRIMARY KEY (matricula, dt_exame),
    CONSTRAINT fk_matricula FOREIGN KEY (matricula)
        REFERENCES public.alunos (matricula) MATCH FULL
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    TABLESPACE pg_default;

-- Index: fki_fk_matricula

-- DROP INDEX IF EXISTS public.fki_fk_matricula;

CREATE INDEX IF NOT EXISTS fki_fk_matricula
    ON public.exames_alunos USING btree
        (matricula ASC NULLS LAST)
    TABLESPACE pg_default;
