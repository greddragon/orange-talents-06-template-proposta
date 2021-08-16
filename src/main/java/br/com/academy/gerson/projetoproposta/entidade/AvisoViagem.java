package br.com.academy.gerson.projetoproposta.entidade;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
public class AvisoViagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String numeroCartao;
	@NotBlank
	private String destino;
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	@Future
	private Date validoAte;
	@NotBlank
	private String ipCliente;
	@NotBlank
	private String userAgent;

	private LocalDateTime instanteAviso;

	@Deprecated
	public AvisoViagem() {

	}

	public AvisoViagem(@NotBlank String numeroCartao, @NotBlank String destino, @Future Date validoAte,
			@NotBlank String ipCliente, @NotBlank String userAgent) {
		super();
		this.numeroCartao = numeroCartao;
		this.destino = destino;
		this.validoAte = validoAte;
		this.ipCliente = ipCliente;
		this.userAgent = userAgent;
		this.instanteAviso = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public String getDestino() {
		return destino;
	}

	public Date getValidoAte() {
		return validoAte;
	}

	public String getIpCliente() {
		return ipCliente;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public LocalDateTime getInstanteAviso() {
		return instanteAviso;
	}

}
