package org.startsevds;

import org.startsevds.interfaces.Dispatcher;
import org.startsevds.interfaces.Order;
import org.startsevds.interfaces.Taxi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaxiImpl implements Taxi {
    private final Thread taxiLine;
    private final Dispatcher dispatcher;
    private final List<Order> executedOrders;
    private final BlockingQueue<Order> activeOrder;
    private final AtomicBoolean isJoined;


    public TaxiImpl(Dispatcher dispatcher) {
        this.isJoined = new AtomicBoolean(false);

        this.activeOrder = new LinkedBlockingQueue<>();
        this.executedOrders = new ArrayList<>();

        this.dispatcher = dispatcher;

        this.taxiLine = new Thread(() -> {
            this.dispatcher.notifyAvailable(this);
            while (true) {
                try {
                    var nextOrder = activeOrder.take();

                    if (JoinRequest.isJoinRequest(nextOrder)) {
                        break;
                    }

                    nextOrder.execute();
                    synchronized (this) {
                        executedOrders.add(nextOrder);
                    }

                    this.dispatcher.notifyAvailable(this);

                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
            }
        });
    }

    @Override
    public void run() {
        taxiLine.start();
    }

    @Override
    public void join() throws InterruptedException {
        if (isJoined.get()) {
            throw new RuntimeException("Already joined!");
        }

        isJoined.set(true);
        activeOrder.put(new JoinRequest());
        taxiLine.join();
    }

    @Override
    public void placeOrder(Order order) {
        if (!isJoined.get()) {
            activeOrder.add(order);
        }
    }

    @Override
    public synchronized List<Order> getExecutedOrders() {
        return new ArrayList<>(executedOrders);
    }
}
