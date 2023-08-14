package ru.otus.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import ru.otus.integration.domain.Chicken;
import ru.otus.integration.service.TransformationService;

@Configuration
public class ChannelConfig {
    private final TransformationService transformationService;

    public ChannelConfig(TransformationService transformationService){
        this.transformationService = transformationService;
    }
    @Bean
    public QueueChannel eggsChannel(){
        return MessageChannels.queue(30).getObject();
    }

    @Bean
    public PublishSubscribeChannel chickChannel(){
        return MessageChannels.publishSubscribe().getObject();
    }

    @Bean
    public QueueChannel chickenChannel(){
        return MessageChannels.queue().getObject();
    }

    @Bean
    public PublishSubscribeChannel grillChannel(){
        return MessageChannels.publishSubscribe().getObject();
    }

    @Bean PublishSubscribeChannel growingChannel(){
        return MessageChannels.publishSubscribe().getObject();
    }

    @Bean
    public IntegrationFlow eggsFlow(){
        return IntegrationFlow.from(eggsChannel())
                .split()
                .log(m -> "Появилось яйцо: " + m.getPayload())
                .handle(transformationService, "transformToChick")
                .aggregate()
                .channel(chickChannel())
                .get();
    }

    @Bean
    public IntegrationFlow chickFlow(){
        return IntegrationFlow.from(chickChannel())
                .split()
                .log(m -> "Вылупился птенец: " + m.getPayload())
                .handle(transformationService, "transformToChicken")
                .aggregate()
                .channel(chickenChannel())
                .get();
    }

    @Bean
    public IntegrationFlow chickenFlow(){
        return IntegrationFlow.from(chickenChannel())
                .split()
                .split()
                .log(m -> "Проверка курицы по весу: " + m.getPayload())
                .<Chicken, Boolean>route(chicken -> chicken.getWeight() > 2,
                    sf -> sf.channelMapping(true, grillChannel())
                            .channelMapping(false, growingChannel()))
                .get();
    }

    @Bean
    public IntegrationFlow grillFlow(){
        return IntegrationFlow.from(grillChannel())
                .split()
                .log(m -> "Отправляется в гриль: " + m.getPayload())
                .handle(transformationService, "transformToGrill")
                .log(m -> "Приготовлен гриль: " + m.getPayload())
                .aggregate()
                .get();
    }

    @Bean
    public IntegrationFlow growingFlow(){
        return IntegrationFlow.from(growingChannel())
                .split()
                .log(m -> "Отправляется расти дальше " + m.getPayload())
                .handle(transformationService, "growingChicken")
                .log(m -> "Немного подрос отправляем снова на проверку " + m.getPayload())
                .channel(chickenChannel())
                .get();
    }
}
