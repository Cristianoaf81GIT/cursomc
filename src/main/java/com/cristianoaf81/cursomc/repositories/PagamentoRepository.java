package com.cristianoaf81.cursomc.repositories;

import com.cristianoaf81.cursomc.domain.Pagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento,Long> {} 
