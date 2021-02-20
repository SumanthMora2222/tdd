import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class OrderListenerTest {
    @Test(expected = InvalidOrderException.class)
    public void shouldThrowExceptionWhenOrdersAreEmpty() {
        OrderListener orderListener = new OrderListener(null);
        List<Order> orders = Collections.emptyList();

        orderListener.process(orders);
    }

    @Test
    public void shouldPublishOrder() {
        OrderService orderService = Mockito.mock(OrderService.class);
        OrderListener orderListener = new OrderListener(orderService);
        Order expectedOrders = new Order("123456", "QWERASDF", "SUCCESS", "2019-08-21T11:05:03");
        List<Order> orders = Arrays.asList(expectedOrders);

        orderListener.process(orders);
        Mockito.verify(orderService).publish(orders);

    }

    @Test
    public void shouldPublishOrders() {
        OrderService orderService = Mockito.mock(OrderService.class);
        OrderListener orderListener = new OrderListener(orderService);
        Order expectedOrders1 = new Order("123456", "QWERASDF", "SUCCESS", "2019-08-21T11:05:03");
        Order expectedOrders2 = new Order("999999", "QWERASDF", "SUCCESS", "2019-08-21T11:05:03");
        List<Order> orders = Arrays.asList(expectedOrders1, expectedOrders2);

        orderListener.process(orders);
        Mockito.verify(orderService).publish(orders);

    }

    @Test(expected = OrderNotProcessedException.class)
    public void shouldThrowExceptionWhenPublishFailed() {
        OrderService orderService = Mockito.mock(OrderService.class);
        OrderListener orderListener = new OrderListener(orderService);
        Order expectedOrders = new Order("123456", "QWERASDF", "SUCCESS", "2019-08-21T11:05:03");
        List<Order> orders = Arrays.asList(expectedOrders);

        Mockito.doThrow(new RuntimeException()).when(orderService).publish(orders);
        orderListener.process(orders);

    }

}