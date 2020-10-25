package io.github.gilsomanfredi.cadastropessoa.sql.pessoa.v1

class PessoaSql {

    public static final String select = """
        SELECT
            id, 
            nome, 
            cpf, 
            sexo, 
            email, 
            data_nascimento, 
            naturalidade, 
            nacionalidade, 
            data_inclusao, 
            data_alteracao
        FROM
            public.pessoa
    """

    public static final String select_by_id = """
        SELECT
            id, 
            nome, 
            cpf, 
            sexo, 
            email, 
            data_nascimento, 
            naturalidade, 
            nacionalidade, 
            data_inclusao, 
            data_alteracao
        FROM
            public.pessoa
        WHERE
            id = :id
    """

    public static final String insert = """
        INSERT INTO public.pessoa(nome, cpf, sexo, email, data_nascimento, 
			naturalidade, nacionalidade, data_inclusao, data_alteracao)
        VALUES (:nome, :cpf, :sexo, :email, :data_nascimento, 
			:naturalidade, :nacionalidade, :data_inclusao, :data_alteracao)
    """

    public static final String update = """
        UPDATE 
            public.pessoa
        SET 
            nome = :nome, 
            cpf = :cpf, 
            sexo = :sexo, 
            email = :email, 
            data_nascimento = :data_nascimento, 
            naturalidade = :naturalidade, 
            nacionalidade = :nacionalidade, 
            data_alteracao = :data_alteracao
        WHERE 
            id = :id
    """

    public static final String delete_by_id = """
        DELETE FROM 
            public.pessoa
        WHERE 
            id = :id
    """

    public static final String exists_by_id = """
        SELECT
            count(*)
        FROM
            public.pessoa
        WHERE
            id = :id
    """

    public static final String exists_cpf = """
        SELECT
            count(*)
        FROM
            public.pessoa
        WHERE
            cpf = :cpf AND 
            (id <> :id OR cast(:id as integer) IS NULL)
    """
}
