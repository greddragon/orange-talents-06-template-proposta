package br.com.academy.gerson.projetoproposta.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.academy.gerson.projetoproposta.Dto.PropostaDto;
import br.com.academy.gerson.projetoproposta.controller.handlerAdvice.ErroException;
import br.com.academy.gerson.projetoproposta.entidade.Proposta;
import br.com.academy.gerson.projetoproposta.repositorio.PropostaRepository;
import br.com.academy.gerson.projetoproposta.service.PropostaService;
import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
@RequestMapping("api/propostas")
public class PropostaController {

	private final Tracer tracer;
	private final TextEncryptor encryptor = Encryptors.text("password", "5c0744940b5c369b");

	public PropostaController(Tracer tracer) {
		this.tracer = tracer;
	}

	private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

	@Autowired
	PropostaRepository repository;

	@Autowired
	PropostaService propostaService;

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastro(@RequestBody @Valid PropostaDto propostaDto, UriComponentsBuilder uriBuilder)
			throws JsonMappingException, JsonProcessingException {

		Span activeSpan = tracer.activeSpan();
		activeSpan.setTag("user.email", "proposta@post.com");
		activeSpan.setBaggageItem("user.email", "proposta@post.com");

		Proposta proposta = propostaDto.toProposta(encryptor);
		Optional<Proposta> propostaJaExiste = repository.findByDocumento(proposta.getDocumento());
		if (propostaJaExiste.isPresent()) {
			throw new ErroException(HttpStatus.UNPROCESSABLE_ENTITY, "documento já dacastrado");
		}

		repository.save(proposta);

		proposta.analiseProposta(propostaService);

		URI uriPropostaComSucesso = uriBuilder.path("/propostas/{id}").build(proposta.getId());

		logger.info("Proposta documento={} salário={} criada com sucesso!", proposta.getDocumento(),
				proposta.getSalario());

		return ResponseEntity.created(uriPropostaComSucesso).build();

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> consultaProposta(@PathVariable Long id) {

		Span activeSpan = tracer.activeSpan();
		activeSpan.setTag("user.email", "proposta@get.com");
		activeSpan.setBaggageItem("user.email", "proposta@get.com");

		Optional<Proposta> proposta = repository.findById(id);

		if (!proposta.isPresent()) {
			logger.error("proposta não existe");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		String decryptor = encryptor.decrypt(proposta.get().getDocumento());
		proposta.get().setDocumento(decryptor);
		return ResponseEntity.ok().body(proposta);
	}

}
