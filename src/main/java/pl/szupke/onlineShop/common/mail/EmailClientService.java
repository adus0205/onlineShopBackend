package pl.szupke.onlineShop.common.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailClientService {

    @Value("${app.email.sender}")
    private String isFakeProperties;
    private final Map<String,EmailSender> emailSenderMap;

    public EmailSender getInstance(){
        if (isFakeProperties.equals("fakeEmailService")){
            return emailSenderMap.get("fakeEmailService");
        }
        return emailSenderMap.get("emailService");
    }

    public EmailSender getInstance(String beanName){
        if (isFakeProperties.equals("fakeEmailService")){
            return emailSenderMap.get("fakeEmailService");
        }
        return emailSenderMap.get(beanName);
    }


}
