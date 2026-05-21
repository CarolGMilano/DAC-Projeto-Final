package br.net.dac.msconta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.net.dac.msconta.model.entity.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long>{
    
}
