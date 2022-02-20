ALTER TABLE public.membros
    ADD COLUMN username varchar(30),
ADD COLUMN seed bytea,
ADD COLUMN pwd bytea;

