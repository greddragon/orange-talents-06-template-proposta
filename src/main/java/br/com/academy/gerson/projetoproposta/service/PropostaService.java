package br.com.academy.gerson.projetoproposta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.academy.gerson.projetoproposta.controller.feignClient.DadosSolicitante;
import br.com.academy.gerson.projetoproposta.controller.feignClient.ResultadoAnalise;
import br.com.academy.gerson.projetoproposta.controller.feignClient.SolicitacaoAnalise;
import br.com.academy.gerson.projetoproposta.entidade.Proposta;
import feign.FeignException;

@Service
public class PropostaService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DadosSolicitante dadosSolicitante;

	public ResultadoAnalise ResultadoAnalisaProposta(Proposta proposta)
			throws JsonMappingException, JsonProcessingException {

		ResultadoAnalise resultadoAnalise = null;

		try {
			SolicitacaoAnalise solicitacaoAnalise = new SolicitacaoAnalise(proposta.getDocumento(), proposta.getNome(),
					String.valueOf(proposta.getId()));
			resultadoAnalise = dadosSolicitante.solicitacaoAnalise(solicitacaoAnalise);

		} catch (FeignException e) {

			String analise = e.contentUTF8();
			resultadoAnalise = new ObjectMapper().readValue(analise, ResultadoAnalise.class);
			logger.warn("Solicitante com restrição: " + e.contentUTF8());
		}

		return resultadoAnalise;

	}

}
