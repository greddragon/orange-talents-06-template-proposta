package br.com.academy.gerson.projetoproposta.entidade;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class BloqueioCartao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String numeroCartao;
	@NotBlank
	private String ipCliente;
	@NotBlank
	private String userAgent;

	private LocalDateTime instanteBloqueio;
	
	@Deprecated
	public BloqueioCartao() {
		
	}

	public BloqueioCartao(String numeroCartao, String ipCliente, String userAgent) {
		super();
		this.numeroCartao = numeroCartao;
		this.ipCliente = ipCliente;
		this.userAgent = userAgent;
		this.instanteBloqueio = LocalDateTime.now();
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public String getIpCliente() {
		return ipCliente;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public LocalDateTime getInstanteBloqueio() {
		return instanteBloqueio;
	}

}
