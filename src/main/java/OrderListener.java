import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

public class OrderListener {

    private OrderService orderService;

    public OrderListener(OrderService orderService) {

        this.orderService = orderService;
    }

    public void process(@Valid List<Order> orders) {
        if (orders.isEmpty()) {
            throw new InvalidOrderException();
        }
        try {
            orderService.publish(orders);
        } catch (Exception e) {
            throw new OrderNotProcessedException();
        }
    }
}
