ALTER TABLE public.pessoa
    ADD COLUMN endereco text;
    
UPDATE public.pessoa 
SET endereco = ''
WHERE endereco IS NULL;

ALTER TABLE public.pessoa
    ALTER COLUMN endereco SET NOT NULL;
