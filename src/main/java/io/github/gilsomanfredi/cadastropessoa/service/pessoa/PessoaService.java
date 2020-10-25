package io.github.gilsomanfredi.cadastropessoa.service.pessoa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.gilsomanfredi.cadastropessoa.config.exception.ValidacaoException;
import io.github.gilsomanfredi.cadastropessoa.model.pessoa.Pessoa;
import io.github.gilsomanfredi.cadastropessoa.repository.pessoa.PessoaRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PessoaService {

    private final @NonNull
    PessoaRepository pessoaRepository;

    public List<Pessoa> findAll() {

        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> findById(Long id) {

        return pessoaRepository.findById(id);
    }

    public Pessoa insert(Pessoa pessoa) {

        pessoa.setDataInclusao(LocalDate.now());
        pessoa.setDataAlteracao(LocalDate.now());
        
        valida(pessoa);

        return pessoaRepository.insert(pessoa);
    }

    public Pessoa update(Long id, Pessoa pessoa) {

        pessoa.setDataAlteracao(LocalDate.now());
        
        valida(pessoa);

        return pessoaRepository.update(id, pessoa);
    }

    public void delete(Long id) {

        pessoaRepository.delete(id);
    }

    public boolean existsById(Long id) {

        return pessoaRepository.existsById(id);
    }

	private void valida(Pessoa pessoa) {

		if (pessoa.getDataNascimento() == null || pessoa.getDataNascimento().isAfter(LocalDate.now())) {
			throw new ValidacaoException("data.nascimento.invalida");
		}
		
		if (pessoaRepository.existsCpf(pessoa)) {
			throw new ValidacaoException("cpf.ja.cadastrado");
		}
	}	
}
