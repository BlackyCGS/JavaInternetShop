package com.myshop.internetshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class InternetShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternetShopApplication.class, args);
    }

}
