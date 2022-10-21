package types.utils;

import types.vehicle.Car;

import java.util.Random;

public enum CarBrand {
    BMW {
        @Override
        public Car createCar(
                long carId,
                String modelName,
                int maxVelocity,
                int power,
                long ownerId,
                int doorsCount
        ) {
            return new Car(carId, String.valueOf(BMW), modelName, maxVelocity, power, ownerId, doorsCount);
        }
    },
    FERRARI {
        @Override
        public Car createCar(
                long carId,
                String modelName,
                int maxVelocity,
                int power,
                long ownerId,
                int doorsCount
        ) {
            return new Car(carId, String.valueOf(FERRARI), modelName, maxVelocity, power, ownerId, doorsCount);
        }
    },
    LADA {
        @Override
        public Car createCar(
                long carId,
                String modelName,
                int maxVelocity,
                int power,
                long ownerId,
                int doorsCount
        ) {
            return new Car(carId, String.valueOf(LADA), modelName, maxVelocity, power, ownerId, doorsCount);
        }
    },
    MERCEDES {
        @Override
        public Car createCar(
                long carId,
                String modelName,
                int maxVelocity,
                int power,
                long ownerId,
                int doorsCount
        ) {
            return new Car(carId, String.valueOf(MERCEDES), modelName, maxVelocity, power, ownerId, doorsCount);
        }
    },
    SKODA {
        @Override
        public Car createCar(
                long carId,
                String modelName,
                int maxVelocity,
                int power,
                long ownerId,
                int doorsCount
        ) {
            return new Car(carId, String.valueOf(SKODA), modelName, maxVelocity, power, ownerId, doorsCount);
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
            long ownerId,
            int doorsCount
    );
}
