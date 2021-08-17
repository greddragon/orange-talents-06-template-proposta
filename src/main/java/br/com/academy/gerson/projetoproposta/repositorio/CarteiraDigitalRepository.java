package br.com.academy.gerson.projetoproposta.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.academy.gerson.projetoproposta.CarteiraDigital;

@Repository
public interface CarteiraDigitalRepository extends JpaRepository<CarteiraDigital, Long>{
	
	@Query("SELECT u From CarteiraDigital u WHERE u.numeroCartao = ?1")
	Optional<CarteiraDigital> FindCarteiraDigital(String numeroCartao);

}
