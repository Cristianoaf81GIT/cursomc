package com.cristianoaf81.cursomc.services.validators;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cristianoaf81.cursomc.domain.enums.TipoCliente;
import com.cristianoaf81.cursomc.dto.ClienteNewDTO;
import com.cristianoaf81.cursomc.resources.exceptions.FieldMessage;
import com.cristianoaf81.cursomc.services.validators.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
  @Override
  public void initialize(ClienteInsert ann) {
  }

  @Override
  public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
    List<FieldMessage> list = new ArrayList<>();

    if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
      list.add(new FieldMessage("cpfOuCnpj", "Cpf inválido"));
    }

    if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
      list.add(new FieldMessage("cpfOuCnpj", "Cnpj inválido"));
    }

    for (FieldMessage e : list) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
          .addConstraintViolation();
    }
    return list.isEmpty();
  }

}