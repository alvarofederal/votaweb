package br.com.votacao.votaweb.repository;

import br.com.votacao.votaweb.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    @Modifying
    @Query("update Sessao s set s.inicioVotacao = :inicioVotacao where s.id = :id")
    Sessao abrirSessao(@Param("id") Long id,
                    @Param("inicioVotacao") LocalDateTime inicioVotacao);
}
