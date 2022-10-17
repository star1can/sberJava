package types;

import types.interfaces.AbstractVehicle;
import types.interfaces.Garage;
import types.interfaces.VehicleUpgrader;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GarageImpl<T extends AbstractVehicle> implements Garage<T> {
    private HashMap<String, HashSet<T>> brandToCarsMap;
    private HashMap<Owner, HashSet<T>> ownerToCarsMap;

    private HashMap<Long, Owner> ownersMap;
    private HashMap<Long, T> carsMap;

    private TreeSet<T> carsByPowerSet;
    private TreeSet<T> carsByVelocitySet;

    public GarageImpl() {
        this.brandToCarsMap = new HashMap<>();
        this.ownerToCarsMap = new HashMap<>();

        this.ownersMap = new HashMap<>();
        this.carsMap = new HashMap<>();

        this.carsByPowerSet = new TreeSet<>((AbstractVehicle lhs, AbstractVehicle rhs) -> {
            if (lhs.id() == rhs.id()) {
                return 0;
            }

            int result = Integer.compare(lhs.power(), rhs.power());
            return result == 0 ? 1 : result;
        });

        this.carsByVelocitySet = new TreeSet<>(Comparator.comparing(T::maxVelocity).reversed().thenComparing(T::id));
    }

    @Override
    public Collection<Owner> allCarsUniqueOwners() {
        return ownersMap.values();
    }

    @Override
    public Collection<T> topThreeCarsByMaxVelocity() {
        return carsByVelocitySet.stream().limit(3).toList();
    }

    @Override
    public Collection<T> allCarsOfBrand(String brand) {
        var result = brandToCarsMap.get(brand);
        return result == null ? Collections.emptyList() : result;
    }

    @Override
    public Collection<T> carsWithPowerMoreThan(int power) {
        return carsByPowerSet.tailSet((T) new AbstractVehicle(-1, "", "", -1, power, -1) {
        });
    }

    @Override
    public Collection<T> allCarsOfOwner(Owner owner) {
        return Optional
                .ofNullable((Collection<T>) ownerToCarsMap.get(owner))
                .orElse(Collections.emptyList());
    }

    @Override
    public int meanOwnersAgeOfCarBrand(String brand) {
        if (brandToCarsMap.get(brand) == null) {
            return 0;
        }

        var wrapper = new Object() {
            public int sum = 0;
            public HashSet<Owner> accountedOwners = new HashSet<>();
        };

        brandToCarsMap.get(brand).forEach(car -> {
            var owner = ownersMap.get(car.ownerId());
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
                .mapToInt(HashSet::size)
                .average().orElse(0);

        return (int) Math.round(mean);
    }

    @Override
    public T removeCar(Object carObj) {
        var car = carsMap.remove(((AbstractVehicle) carObj).id());
        if (car != null) {
            carsByPowerSet.remove(car);
            carsByVelocitySet.remove(car);

            var owner = ownersMap.get(car.ownerId());
            var ownerCars = ownerToCarsMap.get(owner);
            ownerCars.remove(car);

            if (ownerCars.isEmpty()) {
                ownersMap.remove(owner.getOwnerId());
                ownerToCarsMap.remove(owner);
            }

            var brandCars = brandToCarsMap.get(car.brand());
            brandCars.remove(car);
            if (brandCars.isEmpty()) {
                brandToCarsMap.remove(car.brand());
            }

        }

        return car;
    }

    @Override
    public Collection<T> removeCars(List<? extends T> cars) {
        return cars.stream().map(this::removeCar).toList();
    }

    @Override
    public boolean addCars(List<? extends T> cars, List<Owner> owners) {
        if (atLeastOneCarExists(cars)) {
            return false;
        }

        for (int i = 0; i < cars.size(); ++i) {
            if (!addCar(cars.get(i), owners.get(i))) {
                throw new RuntimeException();
            }
        }

        return true;
    }

    @Override
    public List<T> filterCars(Predicate<? super T> predicate) {
        return carsMap.values().stream().filter(predicate).toList();
    }

    @Override
    public List<Object> upgradeCars(VehicleUpgrader upgrader) {
        return carsMap.values().stream().map(upgrader::upgrade).toList();
    }

    private boolean atLeastOneCarExists(List<? extends T> cars) {
        var wrapper = new Object() {
            boolean exists = false;
        };

        cars.forEach(car -> {
            wrapper.exists |= carExists(car);
        });

        return wrapper.exists;
    }

    private boolean carExists(T car) {
        return getCar(car) != null;
    }

    private Object getCar(Object car) {
        return carsMap.get(((AbstractVehicle) car).id());
    }

    @Override
    public boolean addCar(T car, Owner owner) {
        if (carExists(car)) {
            return false;
        }

        return doAdd(car, owner);
    }

    private boolean doAdd(T car, Owner owner) {
        return addOwnerToCarConnection(owner, car)
                && addBrandToCarConnection(car.brand(), car)
                && addCarToSets(car);
    }

    private boolean addCarToSets(T car) {
        if (carsMap.put(car.id(), car) == null) {
            return carsByPowerSet.add(car)
                    && carsByVelocitySet.add(car);
        }
        return false;
    }

    private boolean addBrandToCarConnection(String brand, T car) {
        return putKVInHashMap(
                brand,
                car,
                brandToCarsMap
        );
    }

    private boolean addOwnerToCarConnection(Owner owner, T car) {
        ownersMap.put(owner.getOwnerId(), owner);
        return putKVInHashMap(
                owner,
                car,
                ownerToCarsMap
        );
    }

    private static <K, V> boolean putKVInHashMap(
            K key,
            V value,
            HashMap<K, HashSet<V>> keyToValueSetMap
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
