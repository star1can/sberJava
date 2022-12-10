package org.startsevds;

import lombok.Getter;
import org.startsevds.interfaces.Order;

public class OrderImpl implements Order {

    private static long nextId = 0;

    @Getter
    private final long id;

    public OrderImpl() {
        this.id = nextId++;
    }

    @Override
    public void execute()  {
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }

        System.out.println("Order number " + id + " executed!");
    }
}
