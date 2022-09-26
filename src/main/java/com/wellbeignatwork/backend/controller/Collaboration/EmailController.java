package com.wellbeignatwork.backend.controller.Collaboration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class EmailController {

    //@Autowired
    //EmailSender emailSender;


    //http://localhost:8080/addOffer
    @PostMapping("/email")
    @ResponseBody

    public void email(@RequestBody String toEmail,@RequestBody String body,@RequestBody String subject,@RequestBody String attchment) throws MessagingException {
        //emailSender.sendMailWithAttachement(toEmail,body,subject,attchment);
    }
}
