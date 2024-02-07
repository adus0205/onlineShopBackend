package pl.szupke.onlineShop.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.szupke.onlineShop.security.model.dto.ChangePassword;
import pl.szupke.onlineShop.security.model.dto.EmailObject;
import pl.szupke.onlineShop.security.service.LostPasswordService;

@RestController
@RequiredArgsConstructor
public class LostPasswordController {

    private final LostPasswordService lostPasswordService;

    @PostMapping("/lostPassword")
    public void lostPassword(@RequestBody EmailObject emailObject){
        lostPasswordService.sendLostPasswordLink(emailObject);
    }

    @PostMapping("/changePassword")
    public void changePassword(@RequestBody ChangePassword changePassword){
        lostPasswordService.changePassword(changePassword);
    }
}
