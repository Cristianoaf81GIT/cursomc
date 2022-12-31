package com.cristianoaf81.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailDTO implements Serializable {

  private static final long seraialVersionUID = 1;

  @NotEmpty(message = "Preenchimento obrigatório")
  @Email(message = "Email inválido")
  private String email;

  public EmailDTO() {
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
