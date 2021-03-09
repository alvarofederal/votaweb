package br.com.votacao.votaweb.repository;

import br.com.votacao.votaweb.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    @Query("select s from Sessao s where s.terminoSessao <= :terminoSessao")
    List<Sessao> findAllWithCreationDateTimeBefore(Date terminoSessao);


    //    Optional<Sessao> findByVotacao(Votacao votacao);
//    List<Sessao> findByMensagemTerminoFalse();
    List<Sessao> findByMensagemTerminoFalseOrMensagemTermino(Boolean mensagemTermino);
}
