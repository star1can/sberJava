package org.startsevds.mercedes;

import java.util.Random;

public enum MercedesModel {
    W201, W187, BJ212, GT, E63, C63, G163, EQS, EQE, S600, S500, S580;
    private static final Random random = new Random();

    public static MercedesModel getRandomMercedesModel() {
        MercedesModel[] models = values();
        return models[random.nextInt(models.length)];
    }
}
