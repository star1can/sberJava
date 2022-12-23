package org.startsevds.garage.types;

import org.startsevds.garage.types.interfaces.Garage;

import java.util.*;

public class GarageImpl implements Garage {
    private Map<String, Set<Car>> brandToCarsMap;
    private Map<Owner, Set<Car>> ownerToCarsMap;

    private Map<Long, Owner> ownersMap;
    private Map<Long, Car> carsMap;

    private TreeSet<Car> carByPowerSet;
    private TreeSet<Car> carByVelocitySet;

    public GarageImpl() {
        this.brandToCarsMap = new HashMap<>();
        this.ownerToCarsMap = new HashMap<>();

        this.ownersMap = new HashMap<>();
        this.carsMap = new HashMap<>();

        this.carByPowerSet = new TreeSet<>((Car lhs, Car rhs) -> {
            if (lhs.getCarId() == rhs.getCarId()) {
                return 0;
            }

            int result = Integer.compare(lhs.getPower(), rhs.getPower());
            return result == 0 ? 1 : result;
        });

        this.carByVelocitySet = new TreeSet<>(Comparator
                .comparing(Car::getMaxVelocity).reversed().thenComparing(Car::getCarId)
        );
    }

    @Override
    public Collection<Owner> allCarsUniqueOwners() {
        return ownersMap.values();
    }

    @Override
    public Collection<Car> topThreeCarsByMaxVelocity() {
        return carByVelocitySet.stream().limit(3).toList();
    }

    @Override
    public Collection<Car> allCarsOfBrand(String brand) {
        var result = brandToCarsMap.get(brand);
        return result == null ? Collections.emptyList() : result;
    }

    @Override
    public Collection<Car> carsWithPowerMoreThan(int power) {
        return carByPowerSet.tailSet(new Car(
                -1,
                "",
                "",
                -1,
                power,
                -1
        ));
    }

    @Override
    public Collection<Car> allCarsOfOwner(Owner owner) {
        var result = ownerToCarsMap.get(owner);
        return result == null ? Collections.emptyList() : result;
    }

    @Override
    public int meanOwnersAgeOfCarBrand(String brand) {
        if (brandToCarsMap.get(brand) == null) {
            return 0;
        }

        var wrapper = new Object() {
            public int sum = 0;
            public Set<Owner> accountedOwners = new HashSet<>();
        };

        brandToCarsMap.get(brand).forEach(car -> {
            var owner = ownersMap.get(car.getOwnerId());
            if (wrapper.accountedOwners.add(owner)) {
                wrapper.sum += owner.getAge();
            }
        });

        return (int) Math.round((double) wrapper.sum / wrapper.accountedOwners.size());
    }

    @Override
    public int meanCarNumberForEachOwner() {
        double mean = ownerToCarsMap
                .values()
                .stream()
                .mapToInt(Set::size)
                .average().orElse(0);

        return (int) Math.round(mean);
    }

    @Override
    public Car removeCar(long carId) {
        var car = carsMap.remove(carId);
        if (car != null) {
            carByPowerSet.remove(car);
            carByVelocitySet.remove(car);

            var owner = ownersMap.get(car.getOwnerId());
            var ownerCars = ownerToCarsMap.get(owner);
            ownerCars.remove(car);

            if (ownerCars.isEmpty()) {
                ownersMap.remove(owner.getOwnerId());
                ownerToCarsMap.remove(owner);
            }

            var brandCars = brandToCarsMap.get(car.getBrand());
            brandCars.remove(car);
            if (brandCars.isEmpty()) {
                brandToCarsMap.remove(car.getBrand());
            }

        }

        return car;
    }

    @Override
    public boolean addCar(Car car, Owner owner) {
        return doAdd(car, owner);
    }

    private boolean doAdd(Car car, Owner owner) {
        return addOwnerToCarConnection(owner, car)
                && addBrandToCarConnection(car.getBrand(), car)
                && addCarToSets(car);
    }

    private boolean addCarToSets(Car car) {
        if (carsMap.put(car.getCarId(), car) == null) {
            return carByPowerSet.add(car)
                    && carByVelocitySet.add(car);
        }
        return false;
    }

    private boolean addBrandToCarConnection(String brand, Car car) {
        return putKVInHashMap(
                brand,
                car,
                brandToCarsMap
        );
    }

    private boolean addOwnerToCarConnection(Owner owner, Car car) {
        ownersMap.put(owner.getOwnerId(), owner);
        return putKVInHashMap(
                owner,
                car,
                ownerToCarsMap
        );
    }

    private <K, V> boolean putKVInHashMap(
            K key,
            V value,
            Map<K, Set<V>> keyToValueSetMap
    ) {
        var oldSet = keyToValueSetMap.get(key);

        if (oldSet == null) {
            oldSet = new HashSet<>();
            oldSet.add(value);
            return keyToValueSetMap.put(key, oldSet) == null;
        }

        return oldSet.add(value);
    }

}
