package io.github.gilsomanfredi.cadastropessoa.repository.pessoa.v1.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v1.Pessoa;
import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v1.SexoEnum;

public class PessoaRowMapper implements RowMapper<Pessoa> {

    @Override
    public Pessoa mapRow(ResultSet rs, int i) throws SQLException {

        Pessoa pessoa = new Pessoa();
        pessoa.setId(rs.getLong("id"));
        pessoa.setNome(rs.getString("nome"));
        pessoa.setCpf(rs.getString("cpf"));
        pessoa.setSexo(SexoEnum.valueOfByCodigo(rs.getString("sexo")));
        pessoa.setEmail(rs.getString("email"));
        pessoa.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
        pessoa.setNaturalidade(rs.getString("naturalidade"));
        pessoa.setNacionalidade(rs.getString("nacionalidade"));
        pessoa.setDataInclusao(rs.getDate("data_inclusao").toLocalDate());
        pessoa.setDataAlteracao(rs.getDate("data_alteracao").toLocalDate());

        return pessoa;
    }
}
