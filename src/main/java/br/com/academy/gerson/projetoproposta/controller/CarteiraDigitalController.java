package br.com.academy.gerson.projetoproposta.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.academy.gerson.projetoproposta.Dto.SolicitacaoInclusaoCarteira;
import br.com.academy.gerson.projetoproposta.controller.feignClient.FeignApiCartoes;
import br.com.academy.gerson.projetoproposta.entidade.CarteiraDigital;
import br.com.academy.gerson.projetoproposta.repositorio.CarteiraDigitalRepository;
import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
@RequestMapping("api/cartao/carteira")
public class CarteiraDigitalController {

	private final Tracer tracer;

	public CarteiraDigitalController(Tracer tracer) {
		this.tracer = tracer;
	}

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FeignApiCartoes cartoes;

	@Autowired
	private CarteiraDigitalRepository repository;

	@PostMapping(value = { "paypal/{id_cartao}", "samsung_pay/{id_cartao}" })
	@Transactional
	public ResponseEntity<?> carteiraPaypal(@PathVariable String id_cartao,
			@RequestBody @Valid SolicitacaoInclusaoCarteira inclusaoCarteira, HttpServletRequest request,
			UriComponentsBuilder builder) throws URISyntaxException {

		Span span = tracer.activeSpan();
		span.setTag("user.email", "carteiraDigital@post.com");
		span.setBaggageItem("user.email", "carteiraDigital@post.com");

		try {
			cartoes.consultaNumeroCartao(id_cartao);
		} catch (Exception e) {
			logger.error("Cartão não existe: " + e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		Optional<CarteiraDigital> carteiraJaAssociada = repository.FindCarteiraDigital(id_cartao,
				inclusaoCarteira.getCarteiraConsulta());
		if (carteiraJaAssociada.isPresent()) {
			logger.error("cartão já esta associado a está carteria");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}

		Map<String, Object> carteiraAssociadaComSucesso = associaCarteira(id_cartao, inclusaoCarteira);

		CarteiraDigital carteira = inclusaoCarteira.toInclusao(id_cartao);

		repository.save(carteira);

		URI uri = pegaURL(request, builder, carteira);

		logger.info("Carteira associada armazenada no sistema com sucesso.");
		logger.info("Cateria Associada: " + carteiraAssociadaComSucesso);
		return ResponseEntity.created(uri).build();
	}

	private URI pegaURL(HttpServletRequest request, UriComponentsBuilder builder, CarteiraDigital carteira) {

		String[] partesUrl = request.getRequestURI().toString().split("\\/");
		int contador = 0;
		String url = "";
		while (contador < partesUrl.length - 1) {

			url += partesUrl[contador];
			url += "/";
			contador++;

		}

		return builder.path(url + "{id}").build(carteira.getId());
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
