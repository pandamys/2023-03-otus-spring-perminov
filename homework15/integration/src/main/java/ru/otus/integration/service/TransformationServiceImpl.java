package ru.otus.integration.service;

import ru.otus.integration.domain.Chick;
import ru.otus.integration.domain.Chicken;
import ru.otus.integration.domain.Egg;
import ru.otus.integration.domain.Grill;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TransformationServiceImpl implements TransformationService {
    @Override
    public Chick transformToChick(Egg egg) {
        return new Chick();
    }

    @Override
    public Chicken transformToChicken(Chick chick) {
        Random random = new Random();
        int max = 4;
        int min = 1;
        int weight = random.nextInt(max - min) + min;
        Chicken chicken = new Chicken(weight);
        System.out.println("Выросла курица " + chicken + " weight " + chicken.getWeight());
        return chicken;
    }

    @Override
    public Grill transformToGrill(Chicken chicken) {
        return new Grill();
    }

    @Override
    public Chicken growingChicken(Chicken chicken) {
        chicken.addWeight(1);
        return chicken;
    }

    @Override
    public void toCook(Chicken chicken) {
        System.out.println("Chicken to cook: " + chicken.toString());
    }
}
