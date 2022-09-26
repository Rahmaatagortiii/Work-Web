package com.wellbeignatwork.backend.service.UserService;

import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;


@Service
    public class MailService {

        @Autowired
        private JavaMailSender emailSender;
        @Autowired
        private UserRepository userRepository;

        @Value("${spring.mail.username}")
        private String from;

        public void sendMail(String to, String subject, String content, Boolean html) {
            try {
                MimeMessage mimeMessage = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
                helper.setText(content, html);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setFrom(from, "Well Been");
                emailSender.send(mimeMessage);

            } catch(Exception e) {
                e.printStackTrace();
            }

        }


        public void sendMailToAllUsers(String subject, String content, Boolean html){
             userRepository.findAll().forEach(user -> {

                sendMail(user.getEmail(),subject,content,html);
            });
        }


        public void sendMailToGroupOfUsers(List<User> users, String subject, String content, Boolean html){
            users.forEach(user -> {
                sendMail(user.getEmail(),subject,content,html);
            });
        }


    }

