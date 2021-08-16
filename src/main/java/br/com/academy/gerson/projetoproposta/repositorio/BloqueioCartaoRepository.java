package br.com.academy.gerson.projetoproposta.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.academy.gerson.projetoproposta.entidade.BloqueioCartao;

@Repository
public interface BloqueioCartaoRepository extends JpaRepository<BloqueioCartao, Long>{

	Optional<BloqueioCartao> findByNumeroCartao(String id_cartao);

}
