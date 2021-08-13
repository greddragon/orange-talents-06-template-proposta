package br.com.academy.gerson.projetoproposta.controller.handlerAdvice;

import java.util.Collection;

public class ErroMensagem {

	private Collection<String> mensagens;

	public ErroMensagem(Collection<String> mensagens) {
		super();
		this.mensagens = mensagens;
	}

	public Collection<String> getMensagens() {
		return mensagens;
	}

}
