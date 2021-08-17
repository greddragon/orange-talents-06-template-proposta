package br.com.academy.gerson.projetoproposta;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import br.com.academy.gerson.projetoproposta.Enumerated.CarteiraEnum;

@Entity
public class CarteiraDigital {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String numeroCartao;
	@Email
	@NotBlank
	private String email;
	@Enumerated(EnumType.STRING)
	private CarteiraEnum carteira;

	@Deprecated
	public CarteiraDigital() {

	}

	public CarteiraDigital(@NotBlank String numeroCartao, @Email @NotBlank String email,
			 CarteiraEnum carteira) {
		super();
		this.numeroCartao = numeroCartao;
		this.email = email;
		this.carteira = carteira;
	}

	public Long getId() {
		return id;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public String getEmail() {
		return email;
	}

	public CarteiraEnum getCarteira() {
		return carteira;
	}

}
