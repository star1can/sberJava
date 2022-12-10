package org.startsevds.interfaces;

public interface Dispatcher {
    void notifyAvailable(Taxi taxi);

    void placeOrder(Taxi taxi, Order order);

    void run();

    void submitOrder(Order order);

    void join() throws InterruptedException;
}
