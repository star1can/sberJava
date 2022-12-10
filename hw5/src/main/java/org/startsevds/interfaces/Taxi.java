package org.startsevds.interfaces;

import java.util.List;

public interface Taxi {
    void run();

    void join() throws InterruptedException;

    void placeOrder(Order order);

    List<Order> getExecutedOrders();
}
