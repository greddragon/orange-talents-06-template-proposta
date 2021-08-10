package br.com.academy.gerson.projetoproposta.controller.feignClient;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.academy.gerson.projetoproposta.controller.feignClient.model.ResultadoAnalise;
import br.com.academy.gerson.projetoproposta.controller.feignClient.model.ModelFeignProposta;

@FeignClient(name = "dados-solicitante", url = "http://localhost:9999/")
public interface FeignDadosSolicitante {

	@PostMapping(value = "api/solicitacao", consumes = "application/json")
	ResultadoAnalise analiseProposta(@RequestBody @Valid ModelFeignProposta solicitacao);

}
