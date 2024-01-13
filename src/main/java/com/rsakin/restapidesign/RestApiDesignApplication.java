package com.rsakin.restapidesign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class RestApiDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiDesignApplication.class, args);
    }

}
