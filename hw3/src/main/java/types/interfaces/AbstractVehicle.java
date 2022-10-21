package types.interfaces;


public abstract class AbstractVehicle implements Vehicle {
    private final long vehicleId;
    private final String brand;
    private final String modelName;
    private final int maxVelocity;
    private final int power;
    private final long ownerId;

    public AbstractVehicle(
            long vehicleId,
            String brand,
            String modelName,
            int maxVelocity,
            int power,
            long ownerId
    ) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.modelName = modelName;
        this.maxVelocity = maxVelocity;
        this.power = power;
        this.ownerId = ownerId;
    }

    @Override
    public long id() {
        return vehicleId;
    }

    @Override
    public long ownerId() {
        return ownerId;
    }

    @Override
    public String brand() {
        return brand;
    }

    @Override
    public String modelName() {
        return modelName;
    }

    @Override
    public int maxVelocity() {
        return maxVelocity;
    }

    @Override
    public int power() {
        return power;
    }
}
