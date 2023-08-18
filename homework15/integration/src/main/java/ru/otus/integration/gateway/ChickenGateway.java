package ru.otus.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.integration.domain.Chicken;
import ru.otus.integration.domain.Grill;

@MessagingGateway
public interface ChickenGateway {
    @Gateway(requestChannel = "chickenChannel", replyChannel = "grillChannel")
    Grill process(Chicken chicken);
}
