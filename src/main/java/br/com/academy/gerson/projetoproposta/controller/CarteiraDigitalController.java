package br.com.academy.gerson.projetoproposta.controller;

import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.academy.gerson.projetoproposta.CarteiraDigital;
import br.com.academy.gerson.projetoproposta.Dto.SolicitacaoInclusaoCarteira;
import br.com.academy.gerson.projetoproposta.controller.feignClient.FeignApiCartoes;
import br.com.academy.gerson.projetoproposta.repositorio.CarteiraDigitalRepository;

@RestController
@RequestMapping("api/cartao/carteira")
public class CarteiraDigitalController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FeignApiCartoes cartoes;

	@Autowired
	private CarteiraDigitalRepository repository;

	@PostMapping("paypal/{id_cartao}")
	@Transactional
	public ResponseEntity<?> carteiraPaypal(@PathVariable String id_cartao,
			@RequestBody @Valid SolicitacaoInclusaoCarteira inclusaoCarteira, UriComponentsBuilder builder) {

		try {
			cartoes.consultaNumeroCartao(id_cartao);
		} catch (Exception e) {
			logger.error("Cartão não existe: " + e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		Optional<CarteiraDigital> carteiraJaAssociada = repository.FindCarteiraDigital(id_cartao);
		if (carteiraJaAssociada.isPresent()) {
			logger.error("cartão já esta associado a está carteria");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}

		Map<String, Object> carteiraAssociadaComSucesso = associaCarteira(id_cartao, inclusaoCarteira);

		CarteiraDigital carteira = inclusaoCarteira.toInclusao(id_cartao);

		repository.save(carteira);
		logger.info("Aviso de viagem armazenado no sistema com sucesso.");
		logger.info("Aviso de viagem: " + carteiraAssociadaComSucesso);
		return ResponseEntity.ok().body(carteira);
	}

	private Map<String, Object> associaCarteira(String id_cartao, @Valid SolicitacaoInclusaoCarteira inclusaoCarteira) {

		Map<String, Object> carteiraAssociadaComSucesso = null;
		try {
			carteiraAssociadaComSucesso = cartoes.carteiraDigital(id_cartao, inclusaoCarteira);
		} catch (Exception e) {
			logger.error("não foi possivél associar o cartão a uma carteira digital, tente novamente.");
			throw e;
		}
		return carteiraAssociadaComSucesso;
	}

}
