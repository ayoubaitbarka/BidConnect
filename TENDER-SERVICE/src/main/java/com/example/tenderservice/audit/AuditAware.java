package com.example.tenderservice.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("AuditAware") // Cette annotation permet à Spring de détecter cette classe comme un composant, ce qui est nécessaire pour l'injection de dépendances
public class AuditAware  implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ms_tender"); // Remplacez "ms_tender" par la logique pour obtenir l'utilisateur actuel, la chose qui sera réaliser lorsque l'on aura une authentification
    }

}
