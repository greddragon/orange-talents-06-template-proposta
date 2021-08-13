package br.com.academy.gerson.projetoproposta.entidade;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Biometria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String numeroCartao;
	@NotBlank
	private String biometria;

	private LocalDateTime data;

	public Biometria(@NotBlank String numeroCartao, @NotBlank String biometria) {
		super();
		this.numeroCartao = numeroCartao;
		this.biometria = biometria;
		this.data = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public String getBiometria() {
		return biometria;
	}

	public LocalDateTime getData() {
		return data;
	}

}
