package ru.otus.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.integration.domain.Chick;
import ru.otus.integration.domain.Chicken;

@MessagingGateway
public interface ChickGateway {
    @Gateway(requestChannel = "chickChannel", replyChannel = "chickenChannel")
    Chicken process(Chick chick);
}
