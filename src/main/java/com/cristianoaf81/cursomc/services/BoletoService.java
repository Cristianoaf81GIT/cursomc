package com.cristianoaf81.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import com.cristianoaf81.cursomc.domain.PagamentoComBoleto;

import org.springframework.stereotype.Service;

@Service
public class BoletoService {

  public void preencherPagamentoComBoleto(PagamentoComBoleto pgto, Date instanteDoPedido) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(instanteDoPedido);
    cal.add(Calendar.DAY_OF_MONTH, 7);
    pgto.setDataPagamento(cal.getTime());
  }

}
