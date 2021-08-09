package br.com.academy.gerson.projetoproposta.entidade;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.academy.gerson.projetoproposta.Enumerated.ResultadoSolicitacao;
import br.com.academy.gerson.projetoproposta.Enumerated.StatusProposta;
import br.com.academy.gerson.projetoproposta.controller.feignClient.ResultadoAnalise;
import br.com.academy.gerson.projetoproposta.service.PropostaService;

@Entity
public class Proposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String documento;
	@Email
	@NotBlank
	private String email;
	@NotNull
	@Positive
	private BigDecimal salario;
	@NotBlank
	private String endereco;
	@Enumerated(EnumType.STRING)
	private StatusProposta status;

	@Deprecated
	public Proposta() {

	}

	public Proposta(@NotBlank String nome, @NotBlank String documento, @Email @NotBlank String email,
			@NotNull @Positive BigDecimal salario, @NotBlank String endereco) {
		super();
		this.nome = nome;
		this.documento = documento;
		this.email = email;
		this.salario = salario;
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDocumento() {
		return documento;
	}

	public String getEmail() {
		return email;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public String getEndereco() {
		return endereco;
	}

	public StatusProposta getStatus() {
		return status;
	}

	public void setStatus(StatusProposta status) {
		this.status = status;
	}

	public void analiseProposta(PropostaService propostaService) throws JsonMappingException, JsonProcessingException {

		ResultadoAnalise resultado = propostaService.ResultadoAnalisaProposta(this);

		if (resultado.getResultadoSolicitacao() == ResultadoSolicitacao.COM_RESTRICAO) {
			this.status = StatusProposta.NAO_ELEGIVEL;
		} else {
			this.status = StatusProposta.ELEGIVEL;
		}

	}

}
