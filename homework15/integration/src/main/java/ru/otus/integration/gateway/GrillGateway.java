package ru.otus.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.integration.domain.Chicken;

@MessagingGateway
public interface GrillGateway {
    @Gateway(requestChannel = "grillChannel")
    void process(Chicken chicken);
}
