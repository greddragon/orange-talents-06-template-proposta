package br.com.academy.gerson.projetoproposta.Dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.com.academy.gerson.projetoproposta.entidade.Biometria;

public class BiometriaDto {

	@NotBlank
	String numeroCartao;
	@NotBlank
	String biometria;

	@JsonCreator
	public BiometriaDto(@NotBlank String biometria) {
		super();
		this.biometria = biometria;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public String getBiometria() {
		return biometria;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public Biometria toBiometria(BiometriaDto dto) {

		return new Biometria(numeroCartao, biometria);
	}

}
