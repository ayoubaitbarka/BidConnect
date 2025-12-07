package com.example.tenderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "AuditAware") // Cette annotation active l'audit JPA, ce qui permet de suivre les modifications des entités, et spécifie le bean "AuditAware" pour fournir les informations sur l'auditeur
public class TenderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenderServiceApplication.class, args);
    }

}
