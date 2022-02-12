ALTER table public.usrs
ALTER COLUMN seed TYPE bytea USING seed::bytea,
ALTER COLUMN pass TYPE bytea USING pass::bytea;