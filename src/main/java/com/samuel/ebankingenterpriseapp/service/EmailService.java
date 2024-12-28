package com.samuel.ebankingenterpriseapp.service;

import com.samuel.ebankingenterpriseapp.model.EmailDto;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface EmailService {



    void registrationMail(EmailDto emailDto) throws MessagingException, IOException;

}
