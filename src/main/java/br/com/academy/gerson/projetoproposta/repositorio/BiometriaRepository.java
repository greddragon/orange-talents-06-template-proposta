package br.com.academy.gerson.projetoproposta.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.academy.gerson.projetoproposta.entidade.Biometria;

@Repository
public interface BiometriaRepository extends JpaRepository<Biometria, Long> {

}
