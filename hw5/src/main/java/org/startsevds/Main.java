package org.startsevds;

import org.startsevds.interfaces.Dispatcher;
import org.startsevds.interfaces.Taxi;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Taxi> createTaxis(Dispatcher dispatcher, int count) {
        List<Taxi> taxis = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) {
            taxis.add(new TaxiImpl(dispatcher));
        }

        return taxis;
    }

    private static void runTaxis(List<Taxi> taxis) {
        for (var taxi : taxis) {
            taxi.run();
        }
    }

    private static void stopTaxis(List<Taxi> taxis) throws InterruptedException {
        for (var taxi : taxis) {
            taxi.join();
        }
    }

    // usage example
    public static void main(String[] args) throws InterruptedException {
        int ordersCount = 120;

        var dispatcher = new DispatcherImpl();
        var taxis = createTaxis(dispatcher, 50);
        dispatcher.run();
        runTaxis(taxis);

        for (int i = 0; i < ordersCount; ++i) {
            var order = new OrderImpl();
            dispatcher.submitOrder(order);
        }

        dispatcher.join();
        stopTaxis(taxis);
    }
}