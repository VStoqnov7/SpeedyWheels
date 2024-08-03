package com.example.speedywheels.web;

import com.example.speedywheels.model.dtos.ContactDTO;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import javax.mail.internet.MimeMessage;

import static com.example.speedywheels.TestDataUtils.createContactDTO;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ContactControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ContactDTO contactDTO;


    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;
    private GreenMail greenMail;

    @BeforeEach
    public void setup() {
        contactDTO = createContactDTO();
        greenMail = new GreenMail(new ServerSetup(port,host,"smtp"));
        greenMail.setUser(username, password);
        greenMail.start();
    }

    @Test
    public void testShowContactForm() throws Exception {
        mockMvc.perform(get("/contact-us"))
                .andExpect(status().isOk())
                .andExpect(view().name("contact-us"))
                .andExpect(model().attributeExists("contactDTO"));
    }

    @Test
    @Transactional
    public void testSendMessage() throws Exception {
        mockMvc.perform(post("/contact-us/send-message")
                        .param("fullName", contactDTO.getFullName())
                        .param("email", contactDTO.getEmail())
                        .param("subject", contactDTO.getSubject())
                        .param("message", contactDTO.getMessage())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contact-us"))
                .andExpect(flash().attribute("success", true));

        greenMail.waitForIncomingEmail(1000, 1);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        Assertions.assertEquals(1, receivedMessages.length);

        MimeMessage receivedMessage = receivedMessages[0];
        String body = GreenMailUtil.getBody(receivedMessage);

        Assertions.assertNotNull(body);
        Assertions.assertTrue(body.contains(contactDTO.getFullName()));
        Assertions.assertTrue(body.contains(contactDTO.getEmail()));
        Assertions.assertTrue(body.contains(contactDTO.getSubject()));
        Assertions.assertTrue(body.contains(contactDTO.getMessage()));
    }

    @Test
    public void testSendMessageWithEmptyFields() throws Exception {
        mockMvc.perform(post("/contact-us/send-message")
                        .param("fullName", "")
                        .param("email", "")
                        .param("subject", "")
                        .param("message", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contact-us"))
                .andExpect(flash().attribute("error", true));
    }

    @AfterEach
    void cleanUp() {
        greenMail.stop();
    }
}