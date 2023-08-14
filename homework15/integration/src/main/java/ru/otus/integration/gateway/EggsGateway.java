package ru.otus.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.integration.domain.Chick;
import ru.otus.integration.domain.Egg;

@MessagingGateway
public interface EggsGateway {
    @Gateway(requestChannel = "eggsChannel", replyChannel = "chickChannel")
    Chick process(Egg egg);
}
