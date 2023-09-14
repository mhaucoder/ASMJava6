package edu.poly.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
    String from = "Nguyễn Minh Hậu <haunguyenvn2k3@gmail.com>";
    String to;
    String[] cc;
    String[] bcc;
    String subject;
    String content;
    List<File> attachments = new ArrayList<>();

    public Mail(String to, String subject, String content) {
        this.from = "Nguyễn Minh Hậu";
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

}
