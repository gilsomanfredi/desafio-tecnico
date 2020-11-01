package io.github.gilsomanfredi.cadastropessoa.sql.usuario

class RoleSql {

    public static final String select_by_nome_usuario = """
        SELECT 
            u.nome, r.nome 
        FROM 
            public.usuario u
            JOIN public.usuario_role ur ON u.id = ur.id_usuario
            JOIN public.role r ON r.id = ur.id_role
        WHERE 
            u.nome = ?
    """
}
