package br.com.academy.gerson.projetoproposta.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.academy.gerson.projetoproposta.controller.feignClient.FeignApiCartoes;
import br.com.academy.gerson.projetoproposta.controller.feignClient.FeignDadosSolicitante;
import br.com.academy.gerson.projetoproposta.controller.feignClient.model.ModelFeignProposta;
import br.com.academy.gerson.projetoproposta.controller.feignClient.model.ResultadoAnalise;
import br.com.academy.gerson.projetoproposta.entidade.Proposta;
import feign.FeignException;

@Service
public class PropostaService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	FeignApiCartoes cartaoes;

	@Autowired
	FeignDadosSolicitante dadosSolicitante;

	public ResultadoAnalise ResultadoAnalisaProposta(Proposta proposta)
			throws JsonMappingException, JsonProcessingException {

		ResultadoAnalise resultadoAnalise = null;

		try {
			ModelFeignProposta model = new ModelFeignProposta(proposta.getDocumento(), proposta.getNome(),
					String.valueOf(proposta.getId()));
			resultadoAnalise = dadosSolicitante.analiseProposta(model);

		} catch (FeignException e) {

			String analise = e.contentUTF8();
			resultadoAnalise = new ObjectMapper().readValue(analise, ResultadoAnalise.class);
			logger.warn("Solicitante com restrição: " + e.contentUTF8());
		}

		return resultadoAnalise;

	}

	public  String associaCartao(Proposta proposta) {
		
		ModelFeignProposta model = new ModelFeignProposta(proposta.getDocumento(), proposta.getNome(),
				String.valueOf(proposta.getId()));
		
		cartaoes.NovoCartao(model);

		
		Map<String, Object> cartao = cartaoes.consultaCartao(proposta.getId());
		String numeroCartao = cartao.get("id").toString();
		
		return numeroCartao;
	}

}
