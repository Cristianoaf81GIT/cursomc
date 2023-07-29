package com.cristianoaf81.cursomc.dto;

import com.cristianoaf81.cursomc.domain.Estado;
import java.io.Serializable;

public class EstadoDTO implements Serializable {
  private static final long serialVersionUID = 1;
  
  private Integer id;
  private String nome;

  public EstadoDTO(){}

  public EstadoDTO(Estado obj) {
    id = obj.getId();
    nome = obj.getNome();
  }

  public Integer getId() {
    return this.id;
  }

  public String getNome() {
    return this.nome;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setNome(String nome) {
    this.nome = nome; 
  }
}
