DROP TABLE public.exames_alunos;

CREATE TABLE public.avaliacoes
(
    matricula     bigint   NOT NULL,
    dt_avaliacao  date     NOT NULL,
    graduacao     smallint NOT NULL,
    examinador    bigint   NOT NULL,
    kihon         numeric(3, 2)   DEFAULT 0.0,
    kata          numeric(3, 2)   DEFAULT 0.0,
    kumite        numeric(3, 2)   DEFAULT 0.0,
    obs           varchar(140),
    conferente    bigint,
    foi_conferido smallint default 0,
    CONSTRAINT avaliacoes_pkey PRIMARY KEY (matricula, dt_avaliacao, graduacao, examinador),
    CONSTRAINT fk_matricula FOREIGN KEY (matricula) REFERENCES public.membros (matricula),
    CONSTRAINT fk_examinador FOREIGN KEY (examinador) REFERENCES public.membros (matricula),
    CONSTRAINT fk_conferente FOREIGN KEY (conferente) REFERENCES public.membros (matricula)
);

