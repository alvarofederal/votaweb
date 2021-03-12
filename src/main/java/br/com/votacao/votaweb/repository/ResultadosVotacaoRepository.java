package br.com.votacao.votaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.votacao.votaweb.model.Votacao;

@Repository
public interface ResultadosVotacaoRepository extends JpaRepository<Votacao, Long> {
	
    @Query(value = "SELECT COUNT(*) AS total, COUNT(IF(voto=1,1,null)) as votoSim, COUNT(IF(voto=0,1,null)) as votoNao FROM Votacao where id_pauta = 14;", nativeQuery = true)
    ResultadoVotacao findResultadoVotacaoPorPauta(Long pautaId);

}
