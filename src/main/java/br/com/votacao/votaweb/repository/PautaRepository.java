package br.com.votacao.votaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.votacao.votaweb.model.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Integer> {
	
    @Query(value = "SELECT * FROM Pauta s ORDER BY s.id DESC LIMIT 1", nativeQuery = true)
    Pauta findUltimaPauta();
}
