package br.com.academy.gerson.projetoproposta.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academy.gerson.projetoproposta.entidade.BloqueioCartao;

public interface BloqueioCartaoRepository extends JpaRepository<BloqueioCartao, Long>{

	Optional<BloqueioCartao> findByNumeroCartao(String id_cartao);

}
