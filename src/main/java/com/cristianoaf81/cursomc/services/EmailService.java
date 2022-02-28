package com.cristianoaf81.cursomc.services;

import com.cristianoaf81.cursomc.domain.Pedido;

import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

  void sendOrderConfirmationEmail(Pedido obj);

  void sendEmail(SimpleMailMessage msg);

  void sendOrderConfirmationHtmlEmail(Pedido obj);

  void sendHtmlEmail(MimeMessage msg);

}
