package ru.otus.integration.service;

import ru.otus.integration.domain.Chick;
import ru.otus.integration.domain.Chicken;
import ru.otus.integration.domain.Egg;
import ru.otus.integration.domain.Grill;

public interface TransformationService {
    Chick transformToChick(Egg egg);

    Chicken transformToChicken(Chick chick);

    Grill transformToGrill(Chicken chicken);

    Chicken growingChicken(Chicken chicken);

    void toCook(Chicken chicken);
}
