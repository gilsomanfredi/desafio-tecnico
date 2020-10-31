package io.github.gilsomanfredi.cadastropessoa.service.pessoa.v2;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v2.PessoaV2;
import io.github.gilsomanfredi.cadastropessoa.repository.pessoa.v1.PessoaRepository;
import io.github.gilsomanfredi.cadastropessoa.repository.pessoa.v2.PessoaRepositoryV2;
import io.github.gilsomanfredi.cadastropessoa.validation.pessoa.PessoaValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PessoaServiceV2 {

    private final @NonNull
    PessoaRepositoryV2 pessoaRepositoryV2;

    private final @NonNull
    PessoaRepository pessoaRepository;

    private final @NonNull
    PessoaValidator pessoaValidator;

    public Page<PessoaV2> findAll(Pageable pageable) {

        return pessoaRepositoryV2.findAll(pageable);
    }

    public Optional<PessoaV2> findById(Long id) {

        return pessoaRepositoryV2.findById(id);
    }

    public PessoaV2 insert(PessoaV2 pessoa) {

        pessoa.setDataInclusao(LocalDate.now());
        pessoa.setDataAlteracao(LocalDate.now());
        
        pessoaValidator.valida(pessoa);

        return pessoaRepositoryV2.insert(pessoa);
    }

    public PessoaV2 update(Long id, PessoaV2 pessoa) {

        pessoa.setDataAlteracao(LocalDate.now());
        
        pessoaValidator.valida(pessoa);

        return pessoaRepositoryV2.update(id, pessoa);
    }

    public void delete(Long id) {

    	pessoaRepository.delete(id);
    }

    public boolean existsById(Long id) {

        return pessoaRepository.existsById(id);
    }
}
