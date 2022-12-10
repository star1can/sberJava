package org.startsevds;

import lombok.NonNull;
import org.startsevds.interfaces.Dispatcher;
import org.startsevds.interfaces.Order;
import org.startsevds.interfaces.Taxi;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


public class DispatcherImpl implements Dispatcher {
    private final Thread dispatcherLine;
    private final BlockingQueue<Order> orderQueue;
    private final BlockingQueue<Taxi> availableTaxiQueue;
    private final AtomicBoolean isJoined;


    public DispatcherImpl() {

        this.orderQueue = new LinkedBlockingQueue<>();
        this.availableTaxiQueue = new LinkedBlockingDeque<>();
        this.isJoined = new AtomicBoolean(false);

        this.dispatcherLine = new Thread(() -> {
            while (true) {
                try {
                    var nextOrder = orderQueue.take();

                    if (JoinRequest.isJoinRequest(nextOrder)) {
                        break;
                    }

                    placeOrder(availableTaxiQueue.take(), nextOrder);
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
            }
        });
    }

    @Override
    public void notifyAvailable(@NonNull Taxi taxi) {
        availableTaxiQueue.add(taxi);
    }

    @Override
    public void placeOrder(@NonNull Taxi taxi, @NonNull Order order) {
        taxi.placeOrder(order);
    }

    @Override
    public void run() {
        dispatcherLine.start();
    }

    @Override
    public void submitOrder(@NonNull Order order) {
        if (!isJoined.get()) {
            orderQueue.add(order);
        }
    }

    @Override
    public void join() throws InterruptedException {
        if (isJoined.get()) {
            throw new RuntimeException("Already joined!");
        }

        isJoined.set(true);
        orderQueue.add(new JoinRequest());

        dispatcherLine.join();
    }
}
