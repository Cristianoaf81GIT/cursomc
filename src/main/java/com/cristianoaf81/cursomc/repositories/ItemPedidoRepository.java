package com.cristianoaf81.cursomc.repositories;

import com.cristianoaf81.cursomc.domain.ItemPedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {}
