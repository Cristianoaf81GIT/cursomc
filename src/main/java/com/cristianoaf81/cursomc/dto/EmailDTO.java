package com.cristianoaf81.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class EmailDTO implements Serializable {

  private static final long seraialVersionUID = 1;

  @NotEmpty(message = "Preenchimento obrigatório")
  @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120")
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
