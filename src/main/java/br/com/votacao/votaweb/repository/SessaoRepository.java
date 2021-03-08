package br.com.votacao.votaweb.repository;

import br.com.votacao.votaweb.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

//    @Modifying
//    @Query("update Sessao s set s.inicioSessao = :inicioVotacao where s.id = :id")
//    Sessao abrirSessao(@Param("id") Long id,
//                    @Param("inicioSessao") LocalDateTime inicioSessao);

//    Optional<Sessao> findByVotacao(Votacao votacao);
//    List<Sessao> findByMensagemTerminoFalse();
    List<Sessao> findByMensagemTerminoFalseOrMensagemTermino(Boolean mensagemTermino);
}
