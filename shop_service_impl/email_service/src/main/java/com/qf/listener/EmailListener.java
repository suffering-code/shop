package com.qf.listener;

import com.qf.entity.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class EmailListener {
    private ExecutorService service = Executors.newFixedThreadPool(5);

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender mailSender;

    @RabbitListener(queues = "emailQueue")
    public void sendEmail(Email email){

        service.submit(new Runnable() {
            @Override
            public void run() {
                MimeMessage mimeMessage = mailSender.createMimeMessage();


                try {
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
                    helper.setTo(email.getTo());
                    helper.setFrom(username);
                    System.out.println(username);
                    helper.setSubject(email.getTilte());
                    helper.setText(email.getContent(),true);
                    System.out.println(email);
                    mailSender.send(mimeMessage);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });

}}
