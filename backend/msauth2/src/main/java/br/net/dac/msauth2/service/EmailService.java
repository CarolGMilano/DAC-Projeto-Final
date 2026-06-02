package br.net.dac.msauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
  @Autowired
  private JavaMailSender emailService;

  public void enviarEmail(String assunto, String mensagem, String emailUsuario){
    SimpleMailMessage email = new SimpleMailMessage();

    email.setFrom("bantads2026@gmail.com");
    email.setTo(emailUsuario);
    email.setSubject(assunto);
    email.setText(mensagem);

    emailService.send(email);

    System.out.println("Email enviado com sucesso!");
  }
}