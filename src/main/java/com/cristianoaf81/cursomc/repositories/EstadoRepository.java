package com.cristianoaf81.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.cristianoaf81.cursomc.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{
  
  @Transactional(readOnly=true)
  public List<Estado> findAllByOrderByNome();
}
