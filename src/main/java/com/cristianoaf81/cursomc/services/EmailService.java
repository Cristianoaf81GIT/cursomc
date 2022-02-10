package com.cristianoaf81.cursomc.services;

import com.cristianoaf81.cursomc.domain.Pedido;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
  void sendOrderConfirmationEmail(Pedido obj);

  void sendEmail(SimpleMailMessage msg);

}
