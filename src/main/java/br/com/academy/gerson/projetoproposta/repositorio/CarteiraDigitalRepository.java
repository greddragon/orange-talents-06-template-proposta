package br.com.academy.gerson.projetoproposta.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.academy.gerson.projetoproposta.Enumerated.CarteiraEnum;
import br.com.academy.gerson.projetoproposta.entidade.CarteiraDigital;

@Repository
public interface CarteiraDigitalRepository extends JpaRepository<CarteiraDigital, Long> {

	@Query("SELECT u From CarteiraDigital u WHERE u.numeroCartao = ?1 AND u.carteira = ?2")
	Optional<CarteiraDigital> FindCarteiraDigital(String numeroCartao, CarteiraEnum carteiraEnum);

}
