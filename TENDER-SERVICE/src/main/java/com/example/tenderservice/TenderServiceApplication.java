package com.example.tenderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableFeignClients // Cette annotation active les clients Feign dans l'application, permettant la communication avec d'autres services via des appels HTTP déclaratifs
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "AuditAware") // Cette annotation active l'audit JPA, ce qui permet de suivre les modifications des entités, et spécifie le bean "AuditAware" pour fournir les informations sur l'auditeur
public class TenderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenderServiceApplication.class, args);
    }

}
