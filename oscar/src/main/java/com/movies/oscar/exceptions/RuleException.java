package com.movies.oscar.exceptions;

import lombok.Getter;

public class RuleException extends RuntimeException {

	@Getter
	private String titulo;

	public RuleException() {
		super();
	}

	public RuleException(String message) {
		super(message);
	}

	public RuleException(Throwable cause) {
		super(cause);
	}

	public RuleException(String message, Throwable cause) {
		super(message, cause);
	}

	public RuleException(String titulo, String message) {
		super(message);
		this.titulo = titulo;
	}

}
