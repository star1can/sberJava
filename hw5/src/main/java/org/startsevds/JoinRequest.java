package org.startsevds;

import org.startsevds.interfaces.Order;

public class JoinRequest implements Order {

    public static boolean isJoinRequest(Order order) {
        return order instanceof JoinRequest;
    }

    @Override
    public void execute() {
    }
}
