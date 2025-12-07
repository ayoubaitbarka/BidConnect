package com.example.soumissionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.soumissionservice.feignclients")
@EnableDiscoveryClient
public class SoumissionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoumissionServiceApplication.class, args);
    }

}
