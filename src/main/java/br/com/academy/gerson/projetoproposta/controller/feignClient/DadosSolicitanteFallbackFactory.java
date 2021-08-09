package br.com.academy.gerson.projetoproposta.controller.feignClient;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;

@Component
public class DadosSolicitanteFallbackFactory implements FallbackFactory<DadosSolicitante> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	ResultadoAnalise resultadoAnalise = null;

	@Override
	public DadosSolicitante create(Throwable cause) {

		String dados = cause instanceof FeignException ? ((FeignException) cause).contentUTF8() : "";

		try {
			resultadoAnalise = new ObjectMapper().readValue(dados, ResultadoAnalise.class);
		} catch (JsonMappingException e) {

			logger.error(e.getMessage());

		} catch (JsonProcessingException e) {

			logger.error(e.getMessage());
		}

		return new DadosSolicitante() {

			@Override
			public ResultadoAnalise solicitacaoAnalise(@Valid SolicitacaoAnalise solicitacao) {
				return new ResultadoAnalise(resultadoAnalise.getDocumento(), resultadoAnalise.getNome(),
						resultadoAnalise.getResultadoSolicitacao(), resultadoAnalise.getIdProposta());
			}
		};
	}

}
