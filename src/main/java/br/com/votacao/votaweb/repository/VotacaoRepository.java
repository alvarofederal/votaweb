package br.com.votacao.votaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.votacao.votaweb.model.Votacao;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Integer> {

    @Query(value = "SELECT * FROM Votacao v inner join Pauta p on p.id = v.id_pauta inner join Associado a on a.id = v.id_associado where a.id = ? and p.id = ?;", nativeQuery = true)
	Votacao verificarVotoAssociado(int idAssociado, int idPauta);
    
    @Query(value = "SELECT * FROM Votacao s ORDER BY s.id DESC LIMIT 1", nativeQuery = true)
    Votacao findUltimaVotacao();
}
