package com.example.pass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class PassApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(PassApplication.class, args);
    }
}