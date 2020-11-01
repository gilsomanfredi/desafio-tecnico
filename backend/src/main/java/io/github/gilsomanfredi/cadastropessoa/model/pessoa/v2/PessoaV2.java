package io.github.gilsomanfredi.cadastropessoa.model.pessoa.v2;

import javax.validation.constraints.NotEmpty;

import groovy.transform.EqualsAndHashCode;
import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v1.Pessoa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PessoaV2 extends Pessoa {

	@NotEmpty
	private String endereco;
	
}
