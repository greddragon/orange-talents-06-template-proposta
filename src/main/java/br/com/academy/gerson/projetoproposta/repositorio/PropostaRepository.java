package br.com.academy.gerson.projetoproposta.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.academy.gerson.projetoproposta.entidade.Proposta;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long>{

	Optional<Proposta> findByDocumento(String documento);
	
}
