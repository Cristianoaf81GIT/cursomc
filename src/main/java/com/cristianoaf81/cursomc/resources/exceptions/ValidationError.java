package com.cristianoaf81.cursomc.resources.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

  public static final long serialVersionUID = 1L;

  private List<FieldMessage> errors = new ArrayList<>();

  public ValidationError(Integer status, String msg, LocalDateTime dateTime) {
    super(status, msg, dateTime);
  }

  public List<FieldMessage> getErrors() {
    return this.errors;
  }

  public void addError(String fieldName, String message) {
    this.errors.add(new FieldMessage(fieldName, message));
  }

}
