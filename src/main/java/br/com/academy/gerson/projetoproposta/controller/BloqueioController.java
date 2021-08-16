package br.com.academy.gerson.projetoproposta.controller;

import java.util.Base64;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.academy.gerson.projetoproposta.controller.feignClient.FeignApiCartoes;
import br.com.academy.gerson.projetoproposta.controller.feignClient.model.SolicitacaoBloqueio;
import br.com.academy.gerson.projetoproposta.entidade.BloqueioCartao;
import br.com.academy.gerson.projetoproposta.repositorio.BloqueioCartaoRepository;

@RestController
@RequestMapping("api/cartao/bloqueio")
public class BloqueioController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BloqueioCartaoRepository repository;

	@Autowired
	private FeignApiCartoes cartoes;

	@PostMapping("/{id_cartao}")
	@Transactional
	public ResponseEntity<?> bloqueioCartao(@PathVariable String id_cartao, HttpServletRequest request)
			throws JsonMappingException, JsonProcessingException {

		try { 
					cartoes.consultaNumeroCartao(id_cartao);
		} catch (Exception e) {
			logger.error("Cartão não existe: " + e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		Optional<BloqueioCartao> cartaoBloqueado = repository.findByNumeroCartao(id_cartao);
		if (cartaoBloqueado.isPresent()) {
			logger.error("Cartão já está bloqueado");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}

		String userAgent = request.getHeader("User-Agent");
		String ipCliente = pegaIpClient(request);
		BloqueioCartao bloqueioCartao = new BloqueioCartao(id_cartao, ipCliente, userAgent);

		Map<String, Object> cartaoBloqueadoComSucesso = bloquearCartao(bloqueioCartao);
		
		repository.save(bloqueioCartao);
		logger.info("bloqueio do cartão: " + cartaoBloqueadoComSucesso);
		return ResponseEntity.ok().build();
	}

	private Map<String, Object> bloquearCartao(BloqueioCartao bloqueioCartao) {
		
		SolicitacaoBloqueio solicitacao = new SolicitacaoBloqueio(bloqueioCartao.getUserAgent());
		Map<String, Object> cartaoBloqueadoComSucesso = null;
		try {

			cartaoBloqueadoComSucesso = cartoes.bloqueioCartao(bloqueioCartao.getNumeroCartao(),solicitacao);

		} catch (Exception e) {
			 logger.error("Não foi possível notificar o sistema legado do banco, tente novamente.");
			 throw e;
			
		}
		
		return cartaoBloqueadoComSucesso;
		
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
