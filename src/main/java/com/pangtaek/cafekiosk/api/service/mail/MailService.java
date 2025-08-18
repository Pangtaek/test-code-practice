package com.pangtaek.cafekiosk.api.service.mail;

import com.pangtaek.cafekiosk.client.mail.MailSendClient;
import com.pangtaek.cafekiosk.domain.history.mail.MailSendHistory;
import com.pangtaek.cafekiosk.domain.history.mail.MailSendHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;

    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {

        boolean result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content);

        if (result) {
            mailSendHistoryRepository.save(MailSendHistory.builder()
                    .fromEmail(fromEmail)
                    .toEmail(toEmail)
                    .subject(subject)
                    .content(content)
                    .build());

            return true;
        }

        return false;
    }
}
