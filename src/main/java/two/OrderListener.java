package two;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderListener {

    private OrderService orderService;

    public OrderListener(OrderService orderService) {

        this.orderService = orderService;
    }

    public void process(List<Order> orders) {
        if (orders.isEmpty()) {
            throw new InvalidOrderException();
        }
        List<Order> validOrders = orders.stream().filter(order -> !order.isFutureOrder()).collect(Collectors.toList());
        if (!validOrders.isEmpty()) {
            try {
                orderService.publish(validOrders);
            } catch (PublishFailedException exception) {
                throw new OrderIsNotProcessed();
            }
        }
    }
}
