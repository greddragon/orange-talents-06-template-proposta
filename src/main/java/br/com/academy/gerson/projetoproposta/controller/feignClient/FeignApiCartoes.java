package br.com.academy.gerson.projetoproposta.controller.feignClient;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.academy.gerson.projetoproposta.controller.feignClient.model.ModelFeignProposta;

@FeignClient(name = "api-cartao", url = "http://localhost:8888/")
public interface FeignApiCartoes {
	
	@GetMapping(value = "api/cartoes", consumes = "application/json")
	Map<String, Object> consultaCartao(@RequestParam(name = "idProposta") Long id);
	
	@PostMapping(value = "api/cartoes", consumes = "application/json")
	void NovoCartao(@RequestBody @Valid ModelFeignProposta novo);

}