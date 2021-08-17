package br.com.academy.gerson.projetoproposta.controller.feignClient;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.academy.gerson.projetoproposta.Dto.SolicitacaoInclusaoCarteira;
import br.com.academy.gerson.projetoproposta.controller.feignClient.model.ModelFeignProposta;
import br.com.academy.gerson.projetoproposta.controller.feignClient.model.SolicitacaoAvisoViagem;
import br.com.academy.gerson.projetoproposta.controller.feignClient.model.SolicitacaoBloqueio;

@FeignClient(name = "api-cartao", url = "http://localhost:8888/", configuration = FeignConfig.class)
public interface FeignApiCartoes {

	@GetMapping(value = "api/cartoes", consumes = "application/json")
	Map<String, Object> consultaCartaoProposta(@RequestParam(name = "idProposta") Long id);

	@PostMapping(value = "api/cartoes", consumes = "application/json")
	void NovoCartao(@RequestBody @Valid ModelFeignProposta novo);

	@GetMapping(value = "api/cartoes/{id}", consumes = "application/json")
	Map<String, Object> consultaNumeroCartao(@PathVariable String id);
	
	@PostMapping(value = "api/cartoes/{id}/bloqueios", consumes = "application/json")
	Map<String, Object> bloqueioCartao(@PathVariable String id, @RequestBody @Valid SolicitacaoBloqueio solicitacaoBloqueio);
	
	@PostMapping(value = "api/cartoes/{id}/avisos", consumes="application/json")
	Map<String, Object> avisoViagem(@PathVariable String id, @RequestBody @Valid  SolicitacaoAvisoViagem aviso);
	
	@PostMapping(value = "api/cartoes/{id}/carteiras", consumes="application/json")
	Map<String, Object> carteiraDigital(@PathVariable String id, @RequestBody @Valid  SolicitacaoInclusaoCarteira carteira);
	
	

}