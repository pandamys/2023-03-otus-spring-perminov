package ru.otus.integration.service;

import ru.otus.integration.domain.Egg;
import org.springframework.stereotype.Service;
import ru.otus.integration.gateway.EggsGateway;
import java.util.concurrent.ForkJoinPool;

@Service
public class LifeChickenServiceImpl implements LifeChickenService {
    private final EggsGateway eggsGateway;

    public LifeChickenServiceImpl(EggsGateway eggsGateway) {
        this.eggsGateway = eggsGateway;
    }
    @Override
    public void generate() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        for (int i = 0; i < 3; i++) {
            delay();
            pool.execute(() -> {
                eggsGateway.process(new Egg());
            });
        }
    }

    private void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
