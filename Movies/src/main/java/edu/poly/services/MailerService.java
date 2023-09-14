package edu.poly.services;

import edu.poly.dao.MailerInterface;
import edu.poly.entity.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailerService implements MailerInterface {
    @Autowired
    JavaMailSender sender;
    List<Mail> list = new ArrayList<>();

    @Override
    public boolean send(Mail mail) throws MessagingException {
        try {
            // Tạo message
            MimeMessage message = sender.createMimeMessage();
            // Sử dụng Helper để thiết lập các thông tin cần thiết cho message
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(mail.getFrom());
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getContent(), true);
            helper.setReplyTo(mail.getFrom());
            String[] cc = mail.getCc();
            if (cc != null && cc.length > 0) {
                helper.setCc(cc);
            }
            String[] bcc = mail.getBcc();
            if (bcc != null && bcc.length > 0) {
                helper.setBcc(bcc);
            }
            // Mảng file
            List<File> files = mail.getAttachments();
            if (files.size() > 0) {
                for (File file : files) {
                    helper.addAttachment(file.getName(), file);
                }
            }
            // Gửi message đến SMTP server
            sender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void send(String to, String subject, String body) throws MessagingException {
        this.send(new Mail(to, subject, body));
    }

    @Override
    public void queue(Mail mail) {
        list.add(mail);
    }

    @Override
    public void queue(String to, String subject, String body) {
        queue(new Mail(to, subject, body));
    }

    @Scheduled(fixedDelay = 5000)
    public void run() {
        while (!list.isEmpty()) {
            Mail mail = list.remove(0);
            try {
                this.send(mail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
