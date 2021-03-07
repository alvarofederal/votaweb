package br.com.votacao.votaweb.repository;

import br.com.votacao.votaweb.model.Sessao;
import br.com.votacao.votaweb.model.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    @Modifying
    @Query("update Sessao s set s.inicioSessao = :inicioVotacao where s.id = :id")
    Sessao abrirSessao(@Param("id") Long id,
                    @Param("inicioSessao") LocalDateTime inicioSessao);

    Optional<Sessao> findByVotacao(Votacao votacao);
    List<Sessao> findByProduceMessageFalse();
    List<Sessao> findByProduceMessageFalseOrProduceMessage(Boolean produceMessage);
}
