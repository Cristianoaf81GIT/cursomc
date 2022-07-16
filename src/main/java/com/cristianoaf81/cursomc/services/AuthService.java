package com.cristianoaf81.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cristianoaf81.cursomc.domain.Cliente;
import com.cristianoaf81.cursomc.repositories.ClienteRepository;
import com.cristianoaf81.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private BCryptPasswordEncoder pe;

  @Autowired
  private EmailService emailService;

  private Random random;

  public void sendNewPassword(String email) {
    Cliente cliente = clienteRepository.findByEmail(email);
    if (cliente == null) {
      throw new ObjectNotFoundException("Email não encontrado");
    }

    String newPass = newPassword();
    cliente.setSenha(pe.encode(newPass));
    clienteRepository.save(cliente);
    emailService.sendNewPasswordEmail(cliente, newPass);
  }

  private String newPassword() {
    char[] vet = new char[10];
    for (int i = 0; i < 10; i++) {
      vet[i] = randomChar();
    }
    return new String(vet);
  }

  private char randomChar() {
    int opt = random.nextInt(3);
    if (opt == 0) {
      return (char) (random.nextInt(10) + 48);
    } else if (opt == 1) {
      return (char) (random.nextInt(26) + 65);
    } else {
      return (char) (random.nextInt(26) + 97);
    }
  }

}
