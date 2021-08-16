package br.com.academy.gerson.projetoproposta.controller.feignClient.model;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.academy.gerson.projetoproposta.entidade.AvisoViagem;

public class SolicitacaoAvisoViagem {
	
	@NotBlank
	private String destino;
	@JsonFormat(pattern="yyyy-MM-dd", shape = Shape.STRING)
	@Future
	private Date validoAte;

	public SolicitacaoAvisoViagem(String destino, Date validoAte) {
		super();
		this.destino = destino;
		this.validoAte = validoAte;
	}

	public String getDestino() {
		return destino;
	}

	public Date getValidoAte() {
		return validoAte;
	}

	public AvisoViagem toAviso(String id_cartao, String userAgent, String ipCliente) {
		
		return new AvisoViagem(id_cartao, destino, validoAte, ipCliente, userAgent);
	}

}
