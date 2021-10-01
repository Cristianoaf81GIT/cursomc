package com.cristianoaf81.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.cristianoaf81.cursomc.domain.Cliente;

import org.hibernate.validator.constraints.Length;

public class ClienteDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer id;
  @NotEmpty(message = "Preenchimento obrigatório")
  @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120")
  private String nome;
  @Email(message = "Email inválido")
  private String email;

  public ClienteDTO() {
  }

  public ClienteDTO(Cliente obj) {
    id = obj.getId();
    nome = obj.getNome();
    email = obj.getEmail();
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
