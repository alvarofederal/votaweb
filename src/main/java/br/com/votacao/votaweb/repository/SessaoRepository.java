package br.com.votacao.votaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.votacao.votaweb.model.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Integer> {

    @Query(value = "SELECT * FROM Sessao s ORDER BY s.id DESC LIMIT 1", nativeQuery = true)
    Sessao findUltimaSessao();
}
