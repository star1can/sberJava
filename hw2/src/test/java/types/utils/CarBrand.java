package types.utils;

import types.Car;

import java.util.Random;

public enum CarBrand {
    BMW {
        @Override
        public Car createCar(
                long carId,
                String modelName,
                int maxVelocity,
                int power,
                long ownerId
        ) {
            return new Car(carId, String.valueOf(BMW), modelName, maxVelocity, power, ownerId);
        }
    },
    FERRARI {
        @Override
        public Car createCar(
                long carId,
                String modelName,
                int maxVelocity,
                int power,
                long ownerId
        ) {
            return new Car(carId, String.valueOf(FERRARI), modelName, maxVelocity, power, ownerId);
        }
    },
    LADA {
        @Override
        public Car createCar(
                long carId,
                String modelName,
                int maxVelocity,
                int power,
                long ownerId
        ) {
            return new Car(carId, String.valueOf(LADA), modelName, maxVelocity, power, ownerId);
        }
    },
    MERCEDES {
        @Override
        public Car createCar(
                long carId,
                String modelName,
                int maxVelocity,
                int power,
                long ownerId
        ) {
            return new Car(carId, String.valueOf(MERCEDES), modelName, maxVelocity, power, ownerId);
        }
    },
    SKODA {
        @Override
        public Car createCar(
                long carId,
                String modelName,
                int maxVelocity,
                int power,
                long ownerId
        ) {
            return new Car(carId, String.valueOf(SKODA), modelName, maxVelocity, power, ownerId);
        }
    };
    private static final Random random = new Random();

    public static CarBrand getRandomCarBrand() {
        CarBrand[] brands = values();
        return brands[random.nextInt(brands.length)];
    }

    public abstract Car createCar(
            long carId,
            String modelName,
            int maxVelocity,
            int power,
            long ownerId
    );
}
