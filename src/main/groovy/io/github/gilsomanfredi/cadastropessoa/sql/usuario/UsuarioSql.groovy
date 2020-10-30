package io.github.gilsomanfredi.cadastropessoa.sql.usuario

class UsuarioSql {

    public static final String select_by_nome = """
        SELECT 
            nome, 
            senha, 
            ativo 
        FROM 
            public.usuario 
        WHERE 
            nome = ?
    """
}
