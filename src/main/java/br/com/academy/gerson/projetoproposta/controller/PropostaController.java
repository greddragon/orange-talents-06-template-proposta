package br.com.academy.gerson.projetoproposta.controller;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.academy.gerson.projetoproposta.Dto.PropostaDto;
import br.com.academy.gerson.projetoproposta.entidade.Proposta;

@RestController
@RequestMapping("/propostas")
public class PropostaController {
	
	@PersistenceContext
	EntityManager em;

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastro(@RequestBody @Valid PropostaDto propostaDto, UriComponentsBuilder uriBuilder) {
		
		Proposta proposta = propostaDto.toProposta();
		em.persist(proposta);
		
		URI uriPropostaComSucesso = uriBuilder.path("/propostas/{id}").build(proposta.getId());
		
		
		
		return ResponseEntity.created(uriPropostaComSucesso).build();
	}

}
