package io.github.gilsomanfredi.cadastropessoa.validation.pessoa;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.gilsomanfredi.cadastropessoa.config.exception.ValidacaoException;
import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v1.Pessoa;
import io.github.gilsomanfredi.cadastropessoa.repository.pessoa.v1.PessoaRepository;
import io.github.gilsomanfredi.cadastropessoa.validation.AbstractValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PessoaValidator extends AbstractValidator<Pessoa> {
	
	private final @NonNull
	PessoaRepository pessoaRepository;

	public void valida(Pessoa pessoa) {
		
		if (pessoa.getDataNascimento() == null || pessoa.getDataNascimento().isAfter(LocalDate.now())) {
			throw new ValidacaoException("data.nascimento.invalida");
		}
		
		if (pessoaRepository.existsCpf(pessoa)) {
			throw new ValidacaoException("cpf.ja.cadastrado");
		}
	};
	
}
