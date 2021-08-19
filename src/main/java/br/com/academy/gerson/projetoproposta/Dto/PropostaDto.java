package br.com.academy.gerson.projetoproposta.Dto;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.security.crypto.encrypt.TextEncryptor;

import br.com.academy.gerson.projetoproposta.entidade.Proposta;
import br.com.academy.gerson.projetoproposta.validacao.CpfCjpj;

public class PropostaDto {

	@NotBlank
	private String nome;
	@NotBlank
	@CpfCjpj
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

	public Proposta toProposta(TextEncryptor encryptor) {

		return new Proposta(nome, encryptor.encrypt(documento), email, salario, endereco);

	}

}
