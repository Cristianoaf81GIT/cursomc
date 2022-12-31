package com.cristianoaf81.cursomc.dto;

import java.io.Serializable;

public class CredenciaisDTO implements Serializable {
  private static final long serialVersionUUID = 1L;

  private String email;
  private String senha;

  public CredenciaisDTO(String email, String senha) {
    this.email = email;
    this.senha = senha;
  }

  public CredenciaisDTO() {
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return this.senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

}
