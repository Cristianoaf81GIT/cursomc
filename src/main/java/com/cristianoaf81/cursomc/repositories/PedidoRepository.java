package com.cristianoaf81.cursomc.repositories;

import com.cristianoaf81.cursomc.domain.Cliente;
import com.cristianoaf81.cursomc.domain.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

  @Transactional(readOnly = true)
  Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
}
