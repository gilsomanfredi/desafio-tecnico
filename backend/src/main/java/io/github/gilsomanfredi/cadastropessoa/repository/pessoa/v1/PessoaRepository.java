package io.github.gilsomanfredi.cadastropessoa.repository.pessoa.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v1.Pessoa;
import io.github.gilsomanfredi.cadastropessoa.repository.pessoa.v1.mapper.PessoaRowMapper;
import io.github.gilsomanfredi.cadastropessoa.repository.template.PostgresRepositoryTemplate;
import io.github.gilsomanfredi.cadastropessoa.sql.pessoa.v1.PessoaSql;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PessoaRepository {

    private final @NonNull
    PostgresRepositoryTemplate repositoryTemplate;

    public List<Pessoa> findAll() {

        return repositoryTemplate.query(PessoaSql.select, new PessoaRowMapper());
    }

    public Page<Pessoa> findPaginado(Pageable pageable) {

        return repositoryTemplate.query(PessoaSql.select, new PessoaRowMapper(), pageable);
    }

    public Optional<Pessoa> findById(Long id) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        Pessoa pessoa = repositoryTemplate.queryForObject(PessoaSql.select_by_id, parameters, new PessoaRowMapper());

        return Optional.ofNullable(pessoa);
    }

    public Pessoa insert(Pessoa pessoa) {

        Map<String, Object> parameters = getParameters(pessoa);
        parameters.put("data_inclusao", pessoa.getDataInclusao());

        Long id = repositoryTemplate.insert(PessoaSql.insert, parameters, Pessoa.class);

        pessoa.setId(id);

        return pessoa;
    }

    public Pessoa update(Long id, Pessoa pessoa) {

        Map<String, Object> parameters = getParameters(pessoa);
        parameters.put("id", id);

        repositoryTemplate.update(PessoaSql.update, parameters);

        pessoa.setId(id);

        return pessoa;
    }

    public void delete(Long id) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        repositoryTemplate.delete(PessoaSql.delete_by_id, parameters);
    }

    public boolean existsById(Long id) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        return repositoryTemplate.queryForObject(PessoaSql.exists_by_id, parameters, Long.class) > 0;
    }

	public boolean existsCpf(Pessoa pessoa) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", pessoa.getId());
        parameters.put("cpf", pessoa.getCpf());

        return repositoryTemplate.queryForObject(PessoaSql.exists_cpf, parameters, Long.class) > 0;
	}

    private Map<String, Object> getParameters(Pessoa pessoa) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nome", pessoa.getNome());
        parameters.put("cpf", pessoa.getCpf());
        parameters.put("sexo", pessoa.getSexo() != null ? pessoa.getSexo().getCodigo() : null);
        parameters.put("email", pessoa.getEmail());
        parameters.put("data_nascimento", pessoa.getDataNascimento());
        parameters.put("naturalidade", pessoa.getNaturalidade());
        parameters.put("nacionalidade", pessoa.getNacionalidade());
        parameters.put("data_alteracao", pessoa.getDataAlteracao());

        return parameters;
    }
}
