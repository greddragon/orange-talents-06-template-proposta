package br.com.academy.gerson.projetoproposta.controller;

import java.util.Base64;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.academy.gerson.projetoproposta.controller.feignClient.FeignApiCartoes;
import br.com.academy.gerson.projetoproposta.controller.feignClient.model.SolicitacaoAvisoViagem;
import br.com.academy.gerson.projetoproposta.controller.handlerAdvice.ErroException;
import br.com.academy.gerson.projetoproposta.entidade.AvisoViagem;
import br.com.academy.gerson.projetoproposta.repositorio.AvisoViagemRepository;

@RestController
@RequestMapping("api/cartao/aviso")
public class AvisoViagemController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AvisoViagemRepository repository;

	@Autowired
	private FeignApiCartoes cartoes;

	@PostMapping("/{id_cartao}")
	@Transactional
	public ResponseEntity<?> avisoViagem(@PathVariable String id_cartao,
			@RequestBody @Valid SolicitacaoAvisoViagem solicitacao, HttpServletRequest request)
			throws JsonMappingException, JsonProcessingException {

		try {
			cartoes.consultaNumeroCartao(id_cartao);
		} catch (Exception e) {
			logger.error("Cartão não existe: " + e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		String userAgent = request.getHeader("User-Agent");
		String ipCliente = pegaIpClient(request);

		AvisoViagem aviso = solicitacao.toAviso(id_cartao, userAgent, ipCliente);

		Map<String, Object> avisoViagemComSucesso = avisarViagem(id_cartao, solicitacao);

		try {
			repository.save(aviso);
		} catch (Exception e) {
			logger.error("Ocorreu algum problema: " + e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		logger.info("Aviso de viagem armazenado no sistema com sucesso.");
		logger.info("Aviso de viagem: " + avisoViagemComSucesso);
		return ResponseEntity.ok().build();
	}

	private Map<String, Object> avisarViagem(String id_cartao, @Valid SolicitacaoAvisoViagem solicitacao) {
		Map<String, Object> avisoViagemComSucesso = null;

		try {
			avisoViagemComSucesso = cartoes.avisoViagem(id_cartao, solicitacao);
		} catch (Exception e) {
			logger.error("Não foi possível realizar o aviso de viagem, tente novamente. Talvez a data já esteja cadastrada");
			throw new ErroException(HttpStatus.INTERNAL_SERVER_ERROR, "Data já cadastrada, não pode haver iguais");
		}

		return avisoViagemComSucesso;
	}

	private String pegaIpClient(HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
		String token = request.getHeader("Authorization");

		Base64.Decoder decoder = Base64.getDecoder();

		token = token.substring(7, token.length());

		String[] partesToken = token.split("\\.");

		String payload = new String(decoder.decode(partesToken[1]));

		@SuppressWarnings("unchecked")
		Map<String, Object> objectPayload = (Map<String, Object>) new ObjectMapper().readValue(payload, Object.class);

		return objectPayload.get("clientAddress").toString();
	}
}
