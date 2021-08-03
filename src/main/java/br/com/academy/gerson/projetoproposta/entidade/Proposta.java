package br.com.academy.gerson.projetoproposta.entidade;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
	
	
}
