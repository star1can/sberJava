package types.interfaces;

public interface Vehicle {
    long id();
    long ownerId();
    String brand();
    String modelName();
    int maxVelocity();
    int power();
}
