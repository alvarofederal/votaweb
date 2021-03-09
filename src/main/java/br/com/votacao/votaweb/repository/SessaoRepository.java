package br.com.votacao.votaweb.repository;

import br.com.votacao.votaweb.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    @Query(" FROM Sessao s WHERE s.terminoSessao = :terminoSessao ")
    List<Sessao> verificaELiberaSessao(String terminoSessao);
//    List<Sessao> verificaELiberaSessao(LocalDateTime terminoSessao);


    //    Optional<Sessao> findByVotacao(Votacao votacao);
//    List<Sessao> findByMensagemTerminoFalse();
    List<Sessao> findByMensagemTerminoFalseOrMensagemTermino(Boolean mensagemTermino);
}
