package com.teleprovider.services;

import com.teleprovider.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by olegs on 02.05.2017.
 */

@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender mailSender;

    @Override
    public void sendInsufficientFundsEmail(String recipientMail, Account acc) {
        try {
        String content = "Hello, dear customer!" +
                "\nSorry, but tariff renew failed for your account." +
                "\nInsufficient funds. Your ballance is $" + acc.getFunds() +
                "\nPlease, charge your account to continue using our services";

            mailSender.send(new MimeMessagePreparator() {
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    mimeMessage.setFrom(new InternetAddress("teleprovider12345@gmail.com", "TELEPROVIDER"));
                    mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientMail));
                    mimeMessage.setSubject("Teleprovider. Tariff renew failed.", "utf-8");
                    mimeMessage.setContent(content, "text/html; charset=utf-8");
                }
            });
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
}