package com.wellbeignatwork.backend.service.Collaboration;


import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class SendEmailService implements ISendEmailService {
    private JavaMailSender mailSender;

    public SendEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleEmail( String toEmail,
                                String body,
                                String subject) {
        System.out.println("sending Email");
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("wellbeingatwork66@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail Sent successfully......");
    }
    public void sendMailWithAttachment(String to, String subject, String body, String fileToAttach)
    {
        MimeMessagePreparator preparator = new MimeMessagePreparator()
        {
            public void prepare(MimeMessage mimeMessage) throws Exception
            {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                mimeMessage.setFrom(new InternetAddress("admin@gmail.com"));
                mimeMessage.setSubject(subject);
                mimeMessage.setText(body);

                FileSystemResource file = new FileSystemResource(new File(fileToAttach));
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.addAttachment("logo.jpg", file);
            }
        };

        try {
            mailSender.send(preparator);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
}
