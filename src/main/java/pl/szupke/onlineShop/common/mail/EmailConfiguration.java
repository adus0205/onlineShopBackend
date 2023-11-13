package pl.szupke.onlineShop.common.mail;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;

//@Configuration
public class EmailConfiguration {

    @Bean
    @ConditionalOnProperty(name = "app.email.sender",matchIfMissing = true, havingValue = "emailService")
    public EmailSender emailService(JavaMailSender javaMailSender){
        return new EmailService(javaMailSender);
    }

    @Bean
    @ConditionalOnProperty(name = "app.email.sender",havingValue = "fakeEmailService")
    public EmailSender fakeEmailService(){
        return new FakeEmailService();
    }
}
