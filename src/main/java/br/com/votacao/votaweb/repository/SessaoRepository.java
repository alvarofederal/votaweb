package br.com.votacao.votaweb.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.votacao.votaweb.model.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    @Query(value = "SELECT * FROM Sessao s ORDER BY s.id DESC LIMIT 1", nativeQuery = true)
    Sessao findUltimoResgistro();
    
    @Query("select max(s) from Sessao s where s.terminoSessao <= :terminoSessao")
    List<Sessao> findAllWithCreationDateTimeBefore(String terminoSessao);


    //    Optional<Sessao> findByVotacao(Votacao votacao);
//    List<Sessao> findByMensagemTerminoFalse();
    List<Sessao> findByMensagemTerminoFalseOrMensagemTermino(Boolean mensagemTermino);
}
