package com.example.speedywheels.scheduler;

import com.example.speedywheels.model.dtos.UserEmailDTO;
import com.example.speedywheels.service.interfaces.EmailService;
import com.example.speedywheels.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MondayEmailSchedulerTest {

    @Mock
    private UserService userService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private MondayEmailScheduler mondayEmailScheduler;

    @Test
    void sendEmailToAllUsers() {
        UserEmailDTO user1 = new UserEmailDTO()
                .setFirstName("User1")
                .setContactEmail("user1@example.com");
        UserEmailDTO user2 = new UserEmailDTO()
                .setFirstName("User2")
                .setContactEmail("user2@example.com");
        List<UserEmailDTO> users = Arrays.asList(user1, user2);

        when(userService.getAllUserEmails()).thenReturn(users);

        mondayEmailScheduler.sendEmailToAllUsers();

        verify(emailService, times(2)).sendEmail(anyString(), eq("Exciting News from Speedy Wheels!"), anyString());
    }

    @Test
    void createEmailBody_ShouldCreateCorrectBody() {
        String name = "CurrentUser";

        String emailBody = mondayEmailScheduler.createEmailBody(name);

        String expectedBody = "Hello CurrentUser,\n\n" +
                "We have some great news for you!\n\n" +
                "🚘 **Speedy Wheels News:** Check out our latest car models and find your favorite one.\n" +
                "📲 **Visit our site:** Learn more about the latest updates and special offers.\n\n" +
                "Head over to our site and see what’s new!\n\n" +
                "Best regards,\n" +
                "The Speedy Wheels Team";

        assertEquals(expectedBody, emailBody);
    }
}