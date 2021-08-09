package br.com.academy.gerson.projetoproposta.controller.handlerAdvice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerAdvice {

	@Autowired
	private MessageSource message;

	@ExceptionHandler(ErroException.class)
	public ResponseEntity<Map<String, String>> handlerException(ErroException erroException) {

		Map<String, String> mensage = Map.of("mensagem", erroException.getReason());
		return ResponseEntity.status(erroException.getHttpStatus()).body(mensage);

	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Collection<Map<String, String>> validException(MethodArgumentNotValidException validException) {

		Collection<Map<String, String>> listaMensagens = new ArrayList<>();
		Map<String, String> mensagem = new HashMap<String, String>();
		List<FieldError> errors = validException.getBindingResult().getFieldErrors();
		errors.forEach(e -> {
			mensagem.put(e.getField(), message.getMessage(e, LocaleContextHolder.getLocale()));

		});
		listaMensagens.add(mensagem);
		return listaMensagens;
	}

}
