package com.cristianoaf81.cursomc.repositories;

import com.cristianoaf81.cursomc.domain.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface PedidoRepository extends JpaRepository<Pedido,Integer> {}
