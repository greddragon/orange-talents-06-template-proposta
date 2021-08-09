package br.com.academy.gerson.projetoproposta.controller.feignClient;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "dados-solicitante", url = "http://localhost:9999/")
public interface DadosSolicitante {

	@PostMapping(value = "api/solicitacao", consumes = "application/json")
	ResultadoAnalise solicitacaoAnalise(@RequestBody @Valid SolicitacaoAnalise solicitacao);

}
