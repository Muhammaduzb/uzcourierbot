package com.example.pass.controller;

import com.example.pass.entity.Users;
import com.example.pass.repository.UsersRepository;
import com.example.pass.telegramController.TelegramController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController{
    @Autowired
    UsersRepository usersRepository;

    private TelegramController telegramController;

    WebController(UsersRepository usersRepository,TelegramController telegramController) {
        this.usersRepository = usersRepository;
        this.telegramController = telegramController;
    }

    @PostMapping("/sendMessage")
    public Users addMessage(@RequestBody Users newUsers) {
        telegramController.sendMessage(newUsers.getMessage());
//        System.out.println(newUsers.getPhoneNumber() + "   " + newUsers.getMessage());
        usersRepository.save(newUsers);
        return newUsers;
    }

}