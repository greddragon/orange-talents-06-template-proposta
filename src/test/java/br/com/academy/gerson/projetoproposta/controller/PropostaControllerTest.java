package br.com.academy.gerson.projetoproposta.controller;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.academy.gerson.projetoproposta.Enumerated.ResultadoSolicitacao;
import br.com.academy.gerson.projetoproposta.controller.feignClient.DadosSolicitante;
import br.com.academy.gerson.projetoproposta.controller.feignClient.ResultadoAnalise;
import br.com.academy.gerson.projetoproposta.controller.feignClient.SolicitacaoAnalise;
import feign.FeignException;

@RunWith(SpringRunner.class)
@SpringBootTest
class PropostaControllerTest {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DadosSolicitante dadosSolicitante;

	@Test
	void test() throws JsonMappingException, JsonProcessingException {
		String documento = "36465665395";
		String nome = "gerson dos santos";
		Long id = 1L;

		ResultadoAnalise resultadoAnalise = null;

		try {
			SolicitacaoAnalise solicitacaoAnalise = new SolicitacaoAnalise(documento, nome, String.valueOf(id));
			resultadoAnalise = dadosSolicitante.solicitacaoAnalise(solicitacaoAnalise);
		} catch (FeignException e) {

			String analise = e.contentUTF8();
			resultadoAnalise = new ObjectMapper().readValue(analise, ResultadoAnalise.class);
			System.out.println("\n\nPeguei: " + resultadoAnalise + "\n\n");
			logger.error("Response Body: " + e.contentUTF8());
		}

		SolicitacaoAnalise solicitacaoAnalise = new SolicitacaoAnalise(documento, nome, String.valueOf(id));
		resultadoAnalise = dadosSolicitante.solicitacaoAnalise(solicitacaoAnalise);

		Assert.assertNotNull(resultadoAnalise);
		Assert.assertEquals(resultadoAnalise.getResultadoSolicitacao(), ResultadoSolicitacao.COM_RESTRICAO);
	}

}
