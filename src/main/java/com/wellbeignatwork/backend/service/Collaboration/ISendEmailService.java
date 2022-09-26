package com.wellbeignatwork.backend.service.Collaboration;

import javax.mail.MessagingException;

public interface ISendEmailService {
    void sendSimpleEmail( String toEmail,
                          String body,
                          String subject);
    void sendMailWithAttachment( String toEmail,
                          String body,
                          String subject,
                          String fileToAttach) throws MessagingException;
}
