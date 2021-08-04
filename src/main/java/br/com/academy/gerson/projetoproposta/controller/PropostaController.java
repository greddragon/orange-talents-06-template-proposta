package br.com.academy.gerson.projetoproposta.controller;

import java.net.URI;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.academy.gerson.projetoproposta.Dto.PropostaDto;
import br.com.academy.gerson.projetoproposta.entidade.Proposta;
import br.com.academy.gerson.projetoproposta.repositorio.PropostaRepository;

@RestController
@RequestMapping("/propostas")
public class PropostaController {
	
	//@PersistenceContext
	//EntityManager em;
	
	@Autowired
	PropostaRepository repository;

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastro(@RequestBody @Valid PropostaDto propostaDto, UriComponentsBuilder uriBuilder) {
		
		Proposta proposta = propostaDto.toProposta();
		
		Optional<Proposta> PropostaJaExiste = repository.findByDocumento(propostaDto.getDocumento());
		
		if(PropostaJaExiste.isPresent()) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "documento ja dacastrado");
			//return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(proposta);
		}
		
		repository.save(proposta);
		
		URI uriPropostaComSucesso = uriBuilder.path("/propostas/{id}").build(proposta.getId());
		
		
		
		return ResponseEntity.created(uriPropostaComSucesso).build();
	}

}
