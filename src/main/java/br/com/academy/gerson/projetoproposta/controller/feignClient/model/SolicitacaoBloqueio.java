package br.com.academy.gerson.projetoproposta.controller.feignClient.model;

import javax.validation.constraints.NotBlank;

public class SolicitacaoBloqueio {
	
	@NotBlank
	private String sistemaResponsavel;

	public SolicitacaoBloqueio(@NotBlank String sistemaResponsavel) {
		super();
		this.sistemaResponsavel = sistemaResponsavel;
	}

	public String getSistemaResponsavel() {
		return sistemaResponsavel;
	}
	
	
}
