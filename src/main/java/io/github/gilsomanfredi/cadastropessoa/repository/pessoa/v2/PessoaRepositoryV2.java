package io.github.gilsomanfredi.cadastropessoa.repository.pessoa.v2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v2.PessoaV2;
import io.github.gilsomanfredi.cadastropessoa.repository.pessoa.v2.mapper.PessoaRowMapperV2;
import io.github.gilsomanfredi.cadastropessoa.repository.template.PostgresRepositoryTemplate;
import io.github.gilsomanfredi.cadastropessoa.sql.pessoa.v2.PessoaV2Sql;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PessoaRepositoryV2 {

    private final @NonNull
    PostgresRepositoryTemplate repositoryTemplate;

    public Page<PessoaV2> findAll(Pageable pageable) {

        return repositoryTemplate.query(PessoaV2Sql.select, new PessoaRowMapperV2(), pageable);
    }

    public Optional<PessoaV2> findById(Long id) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        PessoaV2 pessoa = repositoryTemplate.queryForObject(PessoaV2Sql.select_by_id, parameters, new PessoaRowMapperV2());

        return Optional.ofNullable(pessoa);
    }

    public PessoaV2 insert(PessoaV2 pessoa) {

        Map<String, Object> parameters = getParameters(pessoa);
        parameters.put("data_inclusao", pessoa.getDataInclusao());

        Long id = repositoryTemplate.insert(PessoaV2Sql.insert, parameters, PessoaV2.class);

        pessoa.setId(id);

        return pessoa;
    }

    public PessoaV2 update(Long id, PessoaV2 pessoa) {

        Map<String, Object> parameters = getParameters(pessoa);
        parameters.put("id", id);

        repositoryTemplate.update(PessoaV2Sql.update, parameters);

        pessoa.setId(id);

        return pessoa;
    }

    private Map<String, Object> getParameters(PessoaV2 pessoa) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nome", pessoa.getNome());
        parameters.put("cpf", pessoa.getCpf());
        parameters.put("sexo", pessoa.getSexo() != null ? pessoa.getSexo().getCodigo() : null);
        parameters.put("email", pessoa.getEmail());
        parameters.put("data_nascimento", pessoa.getDataNascimento());
        parameters.put("naturalidade", pessoa.getNaturalidade());
        parameters.put("nacionalidade", pessoa.getNacionalidade());
        parameters.put("endereco", pessoa.getEndereco());
        parameters.put("data_alteracao", pessoa.getDataAlteracao());

        return parameters;
    }
}
