package com.br.net.dac.mscontaquery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.net.dac.mscontaquery.model.entity.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long>{
    List<Movimentacao> findByOrigemOrDestinoOrderByDataAsc(String origem, String destino);
}
