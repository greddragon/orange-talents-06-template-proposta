package br.com.academy.gerson.projetoproposta.controller.feignClient.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import br.com.academy.gerson.projetoproposta.Enumerated.ResultadoSolicitacao;

public class ResultadoAnalise {
	@NotBlank
	private String documento;
	@NotBlank
	private String nome;
	@Enumerated(EnumType.STRING)
	@NotBlank
	private ResultadoSolicitacao resultadoSolicitacao;
	@NotBlank
	private String idProposta;

	public ResultadoAnalise() {
		super();
	}

	public ResultadoAnalise(@NotBlank String documento, @NotBlank String nome,
			@NotBlank ResultadoSolicitacao resultadoSolicitacao, @NotBlank String idProposta) {
		super();
		this.documento = documento;
		this.nome = nome;
		this.resultadoSolicitacao = resultadoSolicitacao;
		this.idProposta = idProposta;
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public ResultadoSolicitacao getResultadoSolicitacao() {
		return resultadoSolicitacao;
	}

	public String getIdProposta() {
		return idProposta;
	}

}
