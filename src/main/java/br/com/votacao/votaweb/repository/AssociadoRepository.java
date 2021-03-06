package br.com.votacao.votaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.votacao.votaweb.model.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Integer> {
	
    @Query(value = "SELECT * FROM Associado a where a.cpf = ?;", nativeQuery = true)
	Associado findAssociadoCPF(String cpf);
	
    @Query(value = "SELECT * FROM Associado s ORDER BY s.id DESC LIMIT 1", nativeQuery = true)
    Associado findUltimoAssociado();
}
