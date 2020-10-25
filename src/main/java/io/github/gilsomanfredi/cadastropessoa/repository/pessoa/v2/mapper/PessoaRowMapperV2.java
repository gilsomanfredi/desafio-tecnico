package io.github.gilsomanfredi.cadastropessoa.repository.pessoa.v2.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v1.SexoEnum;
import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v2.PessoaV2;

public class PessoaRowMapperV2 implements RowMapper<PessoaV2> {

    @Override
    public PessoaV2 mapRow(ResultSet rs, int i) throws SQLException {

    	PessoaV2 pessoa = new PessoaV2();
        pessoa.setId(rs.getLong("id"));
        pessoa.setNome(rs.getString("nome"));
        pessoa.setCpf(rs.getString("cpf"));
        pessoa.setSexo(SexoEnum.valueOfByCodigo(rs.getString("sexo")));
        pessoa.setEmail(rs.getString("email"));
        pessoa.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
        pessoa.setNaturalidade(rs.getString("naturalidade"));
        pessoa.setNacionalidade(rs.getString("nacionalidade"));
        pessoa.setEndereco(rs.getString("endereco"));
        pessoa.setDataInclusao(rs.getDate("data_inclusao").toLocalDate());
        pessoa.setDataAlteracao(rs.getDate("data_alteracao").toLocalDate());

        return pessoa;
    }
}
