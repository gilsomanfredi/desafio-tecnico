package io.github.gilsomanfredi.cadastropessoa.config.exception;

import lombok.Getter;

public class ValidacaoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	@Getter
	private Object[] args;

	public ValidacaoException(String key, Object... args) {
		super(key);
		this.args = args;
	}
}
