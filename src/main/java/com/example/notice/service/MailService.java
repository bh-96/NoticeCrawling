package com.example.notice.service;

public interface MailService {

    boolean sendMail(String receiver, String subject, String content);

}
