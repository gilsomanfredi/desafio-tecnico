package io.github.gilsomanfredi.cadastropessoa.service.pessoa.v1;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v1.Pessoa;
import io.github.gilsomanfredi.cadastropessoa.repository.pessoa.v1.PessoaRepository;
import io.github.gilsomanfredi.cadastropessoa.validation.pessoa.PessoaValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PessoaService {

    private final @NonNull
    PessoaRepository pessoaRepository;

    private final @NonNull
    PessoaValidator pessoaValidator;

    public List<Pessoa> findAll() {

        return pessoaRepository.findAll();
    }

    public Page<Pessoa> findPaginado(Pageable pageable) {

        return pessoaRepository.findPaginado(pageable);
    }

    public Optional<Pessoa> findById(Long id) {

        return pessoaRepository.findById(id);
    }

    public Pessoa insert(Pessoa pessoa) {

        pessoa.setDataInclusao(LocalDate.now());
        pessoa.setDataAlteracao(LocalDate.now());
        
        pessoaValidator.valida(pessoa);

        return pessoaRepository.insert(pessoa);
    }

    public Pessoa update(Long id, Pessoa pessoa) {

        pessoa.setDataAlteracao(LocalDate.now());
        
        pessoaValidator.valida(pessoa);

        return pessoaRepository.update(id, pessoa);
    }

    public void delete(Long id) {

        pessoaRepository.delete(id);
    }

    public boolean existsById(Long id) {

        return pessoaRepository.existsById(id);
    }
}
