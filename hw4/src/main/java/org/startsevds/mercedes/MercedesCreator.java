package org.startsevds.mercedes;

import org.startsevds.garage.types.Car;

import java.util.Random;

public class MercedesCreator {
    private static String CAR_NAME = "MERCEDES";
    private static long currentCarID;
    private static final Random random = new Random();


    private static long getNextCarID() {
        return currentCarID++;
    }

    public static Car createMercedes(long ownerId) {
        return new Car(
                getNextCarID(),
                CAR_NAME,
                MercedesModel.getRandomMercedesModel().toString(),
                150 + getRandomUInt() % 250,
                100 + getRandomUInt() % 600,
                ownerId
        );
    }

    private static int getRandomUInt() {
        return random.nextInt(0, Integer.MAX_VALUE);
    }
}
