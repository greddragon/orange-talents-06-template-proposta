package br.com.academy.gerson.projetoproposta.Dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import br.com.academy.gerson.projetoproposta.Enumerated.CarteiraEnum;
import br.com.academy.gerson.projetoproposta.entidade.CarteiraDigital;

public class SolicitacaoInclusaoCarteira {

	@Email
	@NotBlank
	private String email;
	@NotBlank
	private String carteira;

	public SolicitacaoInclusaoCarteira(@Email @NotBlank String email, @NotBlank String carteira) {
		super();
		this.email = email;
		this.carteira = carteira;
	}

	public String getEmail() {
		return email;
	}

	public String getCarteira() {
		return carteira;
	}

	public CarteiraEnum getCarteiraConsulta() {
		return CarteiraEnum.valueOf(this.carteira);
	}

	public CarteiraDigital toInclusao(String numeroCartao) {
		CarteiraEnum carteira = CarteiraEnum.valueOf(this.carteira);
		return new CarteiraDigital(numeroCartao, email, carteira);
	}

}
