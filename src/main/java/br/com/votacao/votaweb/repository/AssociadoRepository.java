package br.com.votacao.votaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.votacao.votaweb.model.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {}
