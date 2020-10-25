package io.github.gilsomanfredi.cadastropessoa.sql.pessoa.v2

class PessoaV2Sql {

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
			endereco,
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
			endereco,
            data_inclusao, 
            data_alteracao
        FROM
            public.pessoa
        WHERE
            id = :id
    """

    public static final String insert = """
        INSERT INTO public.pessoa(nome, cpf, sexo, email, data_nascimento, 
			naturalidade, nacionalidade, endereco, data_inclusao, data_alteracao)
        VALUES (:nome, :cpf, :sexo, :email, :data_nascimento, 
			:naturalidade, :nacionalidade, :endereco, :data_inclusao, :data_alteracao)
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
			endereco = :endereco,
            data_alteracao = :data_alteracao
        WHERE 
            id = :id
    """
}
