package br.com.academy.gerson.projetoproposta.controller.handlerAdvice;

import org.springframework.http.HttpStatus;

public class ErroException extends RuntimeException{


	private static final long serialVersionUID = 1L;
	
	private final HttpStatus httpStatus;
	
	private final String reason;
	
	public ErroException(HttpStatus status, String reason) {
		super(reason);
		this.httpStatus = status;
		this.reason = reason;
		
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getReason() {
		return reason;
	}
	
	

}
