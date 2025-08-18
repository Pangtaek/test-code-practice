package com.pangtaek.cafekiosk.api.service.mail;

import com.pangtaek.cafekiosk.client.mail.MailSendClient;
import com.pangtaek.cafekiosk.domain.history.mail.MailSendHistory;
import com.pangtaek.cafekiosk.domain.history.mail.MailSendHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(value = "test")
class MailServiceTest {

    //    @Mock
    @Spy
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @Test
    @DisplayName("메일 전송 성공 시 이력 저장하고 true 반환")
    void sendMail_success() {
        // given
//        when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
//                .thenReturn(true);
        doReturn(true)
                .when(mailSendClient)
                .sendEmail(anyString(), anyString(), anyString(), anyString());


        // when
        boolean result = mailService.sendMail("to", "sub", "body", "from");

        // then
        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
        assertThat(result).isTrue();
    }
}