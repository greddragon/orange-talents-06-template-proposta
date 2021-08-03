package br.com.academy.gerson.projetoproposta.Dto;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.academy.gerson.projetoproposta.entidade.Proposta;
import br.com.academy.gerson.projetoproposta.validacao.CpfCjpj;

public class PropostaDto {

	@NotBlank
	private String nome;
	@CpfCjpj
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

	public PropostaDto(@NotBlank String nome, @NotBlank String documento, @Email @NotBlank String email,
			@NotNull @Positive BigDecimal salario, @NotBlank String endereco) {
		super();
		this.nome = nome;
		this.documento = documento;
		this.email = email;
		this.salario = salario;
		this.endereco = endereco;
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

	public Proposta toProposta() {

		return new Proposta(nome, documento, email, salario, endereco);

	}

}
