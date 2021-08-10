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

@RestController
@RequestMapping("/propostas")
public class PropostaController {

	private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

	@Autowired
	PropostaRepository repository;

	@Autowired
	PropostaService propostaService;

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastro(@RequestBody @Valid PropostaDto propostaDto, UriComponentsBuilder uriBuilder)
			throws JsonMappingException, JsonProcessingException {

		Proposta proposta = propostaDto.toProposta();
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

}
