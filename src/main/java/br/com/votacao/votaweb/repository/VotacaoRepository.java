package br.com.votacao.votaweb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.votacao.votaweb.model.Votacao;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Long> {

//    @Modifying
//    @Query(
//            value ="insert into Votacao (id_associado, id_pauta, votoSim, votoNao) values (:id_associado, :id_pauta, :votoSim, :votoNao)",
//            nativeQuery = true)
//    Votacao votar(@Param("associadoId") Long associadoId,
//                    @Param("pautaId") Long pautaId,
//                    @Param("voto") Long votoSim,
//                    @Param("voto") Long votoNao);
}
